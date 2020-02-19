package com.goldfish666.virus.po;

import com.goldfish666.virus.enums.Level;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author : lee
 * @version : 1.0.0
 * @date : 2020/2/6 9:01
 */
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class District implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDHexGenerator")
    @Column(name = "id", columnDefinition = "varchar(50) COMMENT '实体ID'")
    protected String id;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '区域编码'")
    private String adCode;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '城市编码'")
    private String cityCode;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '行政区名称'")
    private String name;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '区域中心点'")
    private String center;

    @Enumerated(EnumType.STRING)
    @Column(name = "level", columnDefinition = "VARCHAR(20) COMMENT '行政区划级别'")
    private Level level;

    @OneToMany(mappedBy = "parent", cascade = {CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval = true)
    private List<District> children;

    @ManyToOne
    @JoinColumn(name = "parent_id", columnDefinition = "VARCHAR(255) COMMENT '上级ID'", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private District parent;

    @Column(name = "parent_id", columnDefinition = "varchar(50) NULL DEFAULT NULL COMMENT '上级位置'")
    private String parentId;

}
