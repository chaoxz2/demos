package com.goldfish666.virus.service.impl;

import com.goldfish666.virus.enums.ValidateResultEnum;
import com.goldfish666.virus.po.ProvinceURL;
import com.goldfish666.virus.repository.ProvinceURLRepository;
import com.goldfish666.virus.service.DistrictService;
import com.goldfish666.virus.service.ProvinceURLService;
import com.goldfish666.virus.utils.Constant;
import com.goldfish666.virus.utils.MyHttpClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author : lee
 * @version : 1.0.0
 * @date : 2020/2/7 15:11
 */
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ProvinceURLServiceImpl implements ProvinceURLService {

    private final DistrictService districtService;

    private final ProvinceURLRepository provinceURLRepository;

    @Override
    public List<ProvinceURL> saveAll(List<ProvinceURL> provinceURLList) {
        return provinceURLRepository.saveAll(provinceURLList);
    }

    @Override
    public void initial() {
        // 1. 保存省份名称及对应首页网址
//        List<ProvinceURL> origin = saveDistrictNameAndRooUrl();
        List<ProvinceURL> provinceURLList = provinceURLRepository.getAllByAccess(ValidateResultEnum.success).orElse(Collections.emptyList());
        // 2. 保存“公告公示”基础页面地址
        provinceURLList = setAnnouncementPageUrl(validateRootUrl(provinceURLList));
    }

    private List<ProvinceURL> validateRootUrl(List<ProvinceURL> provinceURLList) {
        return provinceURLList.stream().filter(rootUrl -> StringUtils.hasText(MyHttpClient.sendGet(rootUrl.getRoot()))).collect(Collectors.toList());
    }

    private List<ProvinceURL> setAnnouncementPageUrl(List<ProvinceURL> origin) {
        return origin.stream().peek(provinceURL -> {
            System.out.println("------开始查找： " + provinceURL.getName() + "------");
            if (StringUtils.hasText(provinceURL.getRoot())) {
                System.out.println("首页Url： " + provinceURL.getRoot());
                List<String> urlList = this.getAllRelativeUrl(provinceURL.getRoot());
                provinceURL.setAnnouncementPage(getAnnouncementPageUrl(provinceURL, urlList));
                System.out.println("------查找完成： " + provinceURL.getName() + "------");
            } else {
                System.out.println("------查找失败： " + provinceURL.getName() + "------");
            }
            System.out.println();
        }).collect(Collectors.toList());
    }

    private String getAnnouncementPageUrl(ProvinceURL provinceURL, List<String> urlList) {

        String result = "";
        if (ObjectUtils.isEmpty(urlList)) {
            return result;
        }
        // 1. 从多个url里选出代表某个详情页地址的url（挑选+拼接）
        String theDetailPageFullUrl = selectOneInfoPage(provinceURL, urlList);
        // 2. 进入到这个详情页，获取发布列表的url(从详情页获取列表页的url)
        return getAnnouncementPageUrl(provinceURL, theDetailPageFullUrl);
    }


    private String getAnnouncementPageUrl(ProvinceURL provinceURL, String theDetailPageFullUrl) {

        return null;
    }

    private String selectOneInfoPage(ProvinceURL provinceURL, List<String> urlList) {
        String result = "";
        String theUrl = selectOneInfoPageUrl(provinceURL, urlList);
        result = StringUtils.hasText(theUrl) ? theUrl.startsWith(provinceURL.getRoot()) ? theUrl : theUrl.startsWith("http:") ? theUrl : provinceURL.getRoot() + theUrl : "";
        System.out.println("使用的详情页url： " + result);
        return result;
    }

    private String selectOneInfoPageUrl(ProvinceURL provinceURL, List<String> urlList) {
        System.out.println("共有url： " + urlList.size() + "条");
        String start = getStartOfUrl(urlList);
        List<String> maybeUrl = urlList.parallelStream().filter(url -> url.startsWith(start)).collect(Collectors.toList());
        System.out.println("可能url： " + maybeUrl.size() + "条");
        List<String> sureUrlList = validateInfoPageUrl(provinceURL, maybeUrl);
        if (ObjectUtils.isEmpty(sureUrlList)) {
            return selectOneInfoPageUrl(provinceURL, urlList.parallelStream().filter(x -> !maybeUrl.contains(x)).collect(Collectors.toList()));
        } else {
            System.out.println("确定url： ");
            System.out.println(sureUrlList);
            System.out.println();
            return sureUrlList.get(0);
        }
    }

    private List<String> validateInfoPageUrl(ProvinceURL provinceURL, List<String> maybeList) {
        List<String> result = new ArrayList<>();
        for (String s : maybeList) {
            String link = s.startsWith("http:") ? s : provinceURL.getRoot() + s;
            String origin = MyHttpClient.getString(link, ".*累计.*医学观察");
            if (StringUtils.hasText(origin)) {
                result.add(s);
                break;
            }
        }
        return result;
    }

    private String getStartOfUrl(List<String> urlList) {
        Collections.sort(urlList);
        String start = getStartOfUrl("", urlList);
        return start.substring(0, start.lastIndexOf("/") + 1);
    }


    private String getStartOfUrl(String start, List<String> urlList) {
        urlList = urlList.parallelStream().filter(StringUtils::hasText).collect(Collectors.toList());
        if (urlList.size() == 1) {
            return start;
        }
        Map<String, List<String>> map = urlList.parallelStream().collect(Collectors.groupingBy(url -> url.charAt(0) + ""));
        if (map.keySet().size() == urlList.size()) {
            return start;
        }
        String key = "";
        List<String> list = new ArrayList<>();
        for (String x : map.keySet()) {
            if (map.get(x).size() > list.size()) {
                key = x;
                list = map.get(x);
            }
        }
        list = list.parallelStream().filter(url -> url.length() > 1).collect(Collectors.toList());
        if (list.size() > 1) {
            return getStartOfUrl(start + key, list.parallelStream().map(url -> url.substring(1)).collect(Collectors.toList()));
        } else {
            return start + key;
        }
    }

    private List<String> getAllRelativeUrl(String rootUrl) {
        List<String> result = new ArrayList<>();
        String origin = MyHttpClient.getString(rootUrl, ".*疫情.*情况");
        if (!StringUtils.hasText(origin)) {
            return result;
        }
        Pattern pattern = Pattern.compile("href=\"[^\"]*\"");
        Matcher matcher = pattern.matcher(origin);
        String group = "";
        while (matcher.find()) {
            group = matcher.group();
            result.addAll(Arrays.stream(group.split("href=\"")).filter(x -> x.length() > 12 && !x.endsWith(".css\"") && !x.contains("javascript") && !x.contains("ico\"")).collect(Collectors.toList()));
        }
        return result.parallelStream().map(url -> url.replace("\"", "")).filter(StringUtils::hasText).distinct().collect(Collectors.toList());
    }


    private List<ProvinceURL> saveDistrictNameAndRooUrl() {
        // 1. 获取全部省级位置列表
        List<String> provinceNameList = districtService.getProvinceNameList();

        // 2. 对全部省份，使用百度查询官网地址
        List<ProvinceURL> provinceURLList = new ArrayList<>();
        for (String name : provinceNameList) {
            ProvinceURL provinceURL = ProvinceURL.builder().name(name).build();
            String url = Constant.baiduSearch.replace("查询关键字", name + "卫生健康委员会");
            List<String> urlList = MyHttpClient.getRootUrl(url);
            if (!ObjectUtils.isEmpty(urlList)) {
                provinceURL.setRoot(urlList.get(0).startsWith("http") ? urlList.get(0) : "http://" + urlList.get(0));
            }
            provinceURLList.add(provinceURL);
        }
        for (ProvinceURL rootUrl : provinceURLList) {
            System.out.println(rootUrl.getName() + " : " + rootUrl.getRoot());
        }
        // 3. 验证获取的主页是否正确(521\412问题)
        provinceURLList = provinceURLList.parallelStream().map(MyHttpClient::validateRootUrl).collect(Collectors.toList());
        return this.saveAll(provinceURLList);
    }
}
