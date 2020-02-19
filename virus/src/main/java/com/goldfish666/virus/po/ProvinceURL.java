package com.goldfish666.virus.po;

import com.goldfish666.virus.enums.ValidateResultEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author : lee
 * @version : 1.0.0
 * @date : 2020/2/7 15:07
 */
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ProvinceURL implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDHexGenerator")
    @Column(name = "id", columnDefinition = "varchar(50) COMMENT '实体ID'")
    protected String id;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '行政区名称'")
    private String name;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '根链接'")
    private String root;

    @Enumerated(EnumType.STRING)
    @Column(name = "accsss", columnDefinition = "VARCHAR(20) COMMENT '根地址是否可用'")
    private ValidateResultEnum access;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '栏目名称'")
    private String keyWord;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '公告列表路径'")
    private String announcementPage;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '公告详情路径'")
    private String announcementHomePage;


}
