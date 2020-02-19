package com.goldfish666.virus.service;

import com.goldfish666.virus.po.ProvinceURL;

import java.util.List;

/**
 * @author : lee
 * @version : 1.0.0
 * @date : 2020/2/7 15:10
 */
public interface ProvinceURLService {

    List<ProvinceURL> saveAll(List<ProvinceURL> provinceURLList);

    void initial();
}
