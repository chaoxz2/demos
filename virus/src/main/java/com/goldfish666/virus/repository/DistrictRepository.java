package com.goldfish666.virus.repository;

import com.goldfish666.virus.po.District;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author : lee
 * @version : 1.0.0
 * @date : 2020/2/7 13:28
 */
public interface DistrictRepository extends BaseRepository<District, String> {

    @Query(value = "select name from district where level = :level", nativeQuery = true)
    List<String> getAllNameByLevel(@Param(value = "level") String level);

}
