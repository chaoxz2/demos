package com.goldfish666.virus.utils;

import com.goldfish666.virus.enums.ValidateResultEnum;
import com.goldfish666.virus.po.ProvinceURL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : lee
 * @version : 1.0.0
 * @date : 2020/2/6 10:02
 */
@Slf4j
@Component
public class MyHttpClient {

    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
//            System.out.println(connection.getHeaderFields());
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
//                log.info(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.info("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static ProvinceURL validateRootUrl(ProvinceURL provinceURL) {
        if (!StringUtils.hasText(provinceURL.getRoot())) {
            provinceURL.setAccess(ValidateResultEnum.empty);
        } else {
            try {
                String rootUrl = provinceURL.getRoot().startsWith("http") ? provinceURL.getRoot() : Constant.httpProtocol + provinceURL.getRoot();
                String origin = sendGet(rootUrl);
                Pattern pattern = Pattern.compile(provinceURL.getName());
                Matcher matcher = pattern.matcher(origin);
                if (matcher.find()) {
                    provinceURL.setAccess(ValidateResultEnum.success);
                } else {
                    provinceURL.setAccess(ValidateResultEnum.notMatch);
                }
            } catch (Exception e) {
                provinceURL.setAccess(ValidateResultEnum.fail);
            }
        }
        return provinceURL;
    }

    public static List<String> getRootUrl(String url) {
        List<String> result = new ArrayList<>();
        String origin = getString(url, "style=\"text-decoration:none;\">.{1,70}\\.gov\\.cn/");
        if (!ObjectUtils.isEmpty(origin)) {
            String originRootUrl = origin.split(">")[1];
            result.add(originRootUrl);
        }
        return result;
    }

    public static String getString(String url, String regex) {
        String origin = sendGet(url);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(origin);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return null;
        }
    }
}
