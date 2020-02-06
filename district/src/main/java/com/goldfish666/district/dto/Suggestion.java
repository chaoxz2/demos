package com.goldfish666.district.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : lee
 * @version : 1.0.0
 * @date : 2020/2/6 9:16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Suggestion implements Serializable {

    private String keywords;

    private String cities;
}
