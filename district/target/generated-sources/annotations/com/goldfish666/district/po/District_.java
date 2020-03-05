package com.goldfish666.district.po;

import com.goldfish666.district.enums.Level;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(District.class)
public abstract class District_ {

	public static volatile SingularAttribute<District, District> parent;
	public static volatile SingularAttribute<District, Level> level;
	public static volatile ListAttribute<District, District> children;
	public static volatile SingularAttribute<District, String> cityCode;
	public static volatile SingularAttribute<District, String> adCode;
	public static volatile SingularAttribute<District, String> center;
	public static volatile SingularAttribute<District, String> name;
	public static volatile SingularAttribute<District, String> id;
	public static volatile SingularAttribute<District, String> parentId;

	public static final String PARENT = "parent";
	public static final String LEVEL = "level";
	public static final String CHILDREN = "children";
	public static final String CITY_CODE = "cityCode";
	public static final String AD_CODE = "adCode";
	public static final String CENTER = "center";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String PARENT_ID = "parentId";

}

