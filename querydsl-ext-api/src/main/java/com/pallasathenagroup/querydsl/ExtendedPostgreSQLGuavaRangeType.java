package com.pallasathenagroup.querydsl;

import com.vladmihalcea.hibernate.type.range.guava.PostgreSQLGuavaRangeType;
import org.hibernate.annotations.common.reflection.java.JavaXMember;
import org.hibernate.usertype.DynamicParameterizedType;

import java.lang.reflect.ParameterizedType;
import java.util.Properties;

public class ExtendedPostgreSQLGuavaRangeType extends PostgreSQLGuavaRangeType implements DynamicParameterizedType {

    private Class<?> elementType;

    @Override
    public void setParameterValues(Properties parameters) {
        JavaXMember javaXMember = (JavaXMember) parameters.get(DynamicParameterizedType.XPROPERTY);
        ParameterizedType javaType = (ParameterizedType) javaXMember.getJavaType();
        elementType = (Class<?>) javaType.getActualTypeArguments()[0];
    }

    public Class<?> getElementType() {
        return elementType;
    }

    public void setElementType(Class<?> elementType) {
        this.elementType = elementType;
    }

}
