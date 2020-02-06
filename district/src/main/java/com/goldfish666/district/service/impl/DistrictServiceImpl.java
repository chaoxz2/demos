package com.goldfish666.district.service.impl;

import com.alibaba.fastjson.JSON;
import com.goldfish666.district.dto.Result;
import com.goldfish666.district.enums.Level;
import com.goldfish666.district.po.District;
import com.goldfish666.district.repository.DistrictRepository;
import com.goldfish666.district.service.DistrictService;
import com.goldfish666.district.utils.Constant;
import com.goldfish666.district.utils.HttpClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : lee
 * @version : 1.0.0
 * @date : 2020/2/6 9:35
 */
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository districtRepository;

    @Override
    public Result getAddress(String name) {
        String url = Constant.rootURL + "?" + "key=" + Constant.key + "&keywords=" + name + "&subdistrict=3&extensions=base";
        String resultString = HttpClient.sendGet(url);
        return JSON.parseObject(resultString, Result.class);
    }

    @Override
    public District generateDistrict(District district) {
        District savedDistrict = districtRepository.save(district);
        return saveChildren(savedDistrict);
    }

    private District saveChildren(District parent) {
        if (Level.district.ordinal() > parent.getLevel().ordinal() && !ObjectUtils.isEmpty(parent.getChildren())) {
            List<District> children = new ArrayList<>();
            for (District x : parent.getChildren()) {
                x.setParentId(parent.getId());
                District child = districtRepository.save(x);
                saveChildren(child);
                children.add(child);
            }
            parent.setChildren(children);
        }
        return parent;
//        System.out.println(parent.getAdCode());
//        if (parent.getLevel().ordinal() < Level.district.ordinal() && !ObjectUtils.isEmpty(parent.getChildren())) {
//            List<District> children = new ArrayList<>();
//            for (District x : parent.getChildren()) {
//                x.setParent(parent);
//                x.setParentId(parent.getAdCode());
//                District child = districtRepository.save(x);
//                if (!Level.district.equals(child.getLevel())) {
//                    child = saveChildren(child);
//                }
//                children.add(child);
//
//            }
//            parent.setChildren(children);
//        }
//        return parent;
    }

}
