package com.goldfish666.virus.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author : lee
 * @version : 1.0.0
 * @date : 2020/2/5 10:52
 */
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VirusData implements Serializable {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    @Column(columnDefinition = "varchar(50) NOT NULL COMMENT '实体ID'")
    private String id;

    /**
     * 日期
     */
    @Column(name = "date", updatable = false, columnDefinition = "datetime NOT NULL COMMENT '日期'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    /**
     * 地区
     */
    @ManyToOne
    @JoinColumn(name = "district_id", nullable = true, columnDefinition = "varchar(50) comment '地区'")
    private District district;

    /**
     * 数量
     */
    private int count;
}
