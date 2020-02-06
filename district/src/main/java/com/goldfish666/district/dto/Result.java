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
 * @date : 2020/2/6 9:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Serializable {

    private String status;

    private String info;

    private String infocode;

    private String count;

    private Suggestion suggestion;

    private List<DistrictDTO> districts;
}
