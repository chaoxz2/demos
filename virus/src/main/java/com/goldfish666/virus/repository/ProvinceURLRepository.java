package com.goldfish666.virus.repository;

import com.goldfish666.virus.enums.ValidateResultEnum;
import com.goldfish666.virus.po.ProvinceURL;

import java.util.List;
import java.util.Optional;

/**
 * @author : lee
 * @version : 1.0.0
 * @date : 2020/2/7 15:10
 */
public interface ProvinceURLRepository extends BaseRepository<ProvinceURL, String> {

    Optional<List<ProvinceURL>> getAllByAccess(ValidateResultEnum access);
}
