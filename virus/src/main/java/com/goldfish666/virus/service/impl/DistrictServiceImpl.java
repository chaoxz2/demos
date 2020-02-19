package com.goldfish666.virus.service.impl;

import com.goldfish666.virus.enums.Level;
import com.goldfish666.virus.repository.DistrictRepository;
import com.goldfish666.virus.service.DistrictService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : lee
 * @version : 1.0.0
 * @date : 2020/2/7 13:31
 */
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository districtRepository;

    @Override
    public List<String> getProvinceNameList() {
        return districtRepository.getAllNameByLevel(Level.province.name());
    }
}
