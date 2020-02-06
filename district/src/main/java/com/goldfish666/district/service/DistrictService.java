package com.goldfish666.district.service;

import com.goldfish666.district.dto.Result;
import com.goldfish666.district.po.District;

/**
 * @author : lee
 * @version : 1.0.0
 * @date : 2020/2/6 9:30
 */
public interface DistrictService {

    Result getAddress(String name);

    District generateDistrict(District district);
}
