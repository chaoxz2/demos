package com.goldfish666.virus.po;

import com.tuyang.beanutils.annotation.CopyProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author : lee
 * @version : 1.0.0
 * @date : 2020/2/5 10:59
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class District implements Serializable {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    @Column(columnDefinition = "varchar(50) NOT NULL COMMENT '实体ID'")
    private String id;

    /**
     * 名字
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '名字'")
    private String name;

    /**
     * 上级ID
     */
    @Column(name = "parent_id", columnDefinition = "varchar(50) NULL DEFAULT NULL COMMENT '上级位置'")
    private String parentId;

    /**
     * 所属父级
     */
    @ManyToOne
    @JoinColumn(name = "parent_id", columnDefinition = "VARCHAR(255) COMMENT '上级ID'", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private District parent;

    /**
     * 包含下级
     */
    @OrderBy(value = "name ASC")
    @OneToMany(mappedBy = "parent", cascade = {CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<District> children;

    /**
     * 路径
     */
    @CopyProperty(ignored = true)
    @Column(name = "path", columnDefinition = "VARCHAR(300) COMMENT 'path'")
    private String path;
}
