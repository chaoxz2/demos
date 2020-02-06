package com.goldfish666.virus.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author : lee
 * @version : 1.0.0
 * @date : 2020/2/5 10:52
 */
@Entity
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
     * 创建时间
     */
    @Column(name = "create_time", updatable = false, columnDefinition = "datetime NOT NULL COMMENT '创建时间'")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createTime;

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

}
