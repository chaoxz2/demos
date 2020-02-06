package com.goldfish666.district.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author : lee
 * @version : 1.0.0
 * @date : 2020/2/6 9:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistrictDTO implements Serializable {

    private String citycode;

    private String adcode;

    private String name;

    private String center;

    private String level;

    private List<DistrictDTO> districts;
}
