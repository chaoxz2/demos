package com.goldfish666.district.utils.convert;

import com.goldfish666.district.enums.Level;
import com.tuyang.beanutils.BeanCopyConvertor;

/**
 * @author : lee
 * @version : 1.0.0
 * @date : 2020/2/6 11:12
 */
public class String2LevelEnumConvert implements BeanCopyConvertor<String, Level> {
    @Override
    public Level convertTo(String source) {
        Level result = null;
        for (Level x : Level.values()) {
            if (x.name().equals(source)) {
                result = x;
                break;
            }
        }
        return result;
    }
}
