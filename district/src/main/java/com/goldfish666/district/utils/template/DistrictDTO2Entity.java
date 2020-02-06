package com.goldfish666.district.utils.template;

import com.goldfish666.district.dto.DistrictDTO;
import com.goldfish666.district.enums.Level;
import com.goldfish666.district.po.District;
import com.goldfish666.district.utils.convert.String2LevelEnumConvert;
import com.tuyang.beanutils.annotation.BeanCopySource;
import com.tuyang.beanutils.annotation.CopyCollection;
import com.tuyang.beanutils.annotation.CopyProperty;

import java.util.List;

/**
 * @author : lee
 * @version : 1.0.0
 * @date : 2020/2/6 11:05
 */
@BeanCopySource(source = DistrictDTO.class)
public class DistrictDTO2Entity {

    @CopyProperty(property = "adcode")
    private String adCode;

    @CopyProperty(property = "citycode")
    private String cityCode;

    @CopyProperty(property = "name")
    private String name;

    @CopyProperty(property = "center")
    private String center;

    @CopyProperty(convertor = String2LevelEnumConvert.class)
    private Level level;

    @CopyCollection(property = "districts", targetClass = District.class, optionClass = DistrictDTO2Entity.class)
    private List<District> children;

    @CopyProperty(ignored = true)
    private District parent;
}
