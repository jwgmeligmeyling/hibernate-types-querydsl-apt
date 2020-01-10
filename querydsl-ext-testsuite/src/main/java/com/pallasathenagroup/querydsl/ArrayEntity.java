package com.pallasathenagroup.querydsl;

import com.vladmihalcea.hibernate.type.array.EnumArrayType;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@TypeDef(
        name = "pgsql_array",
        typeClass = EnumArrayType.class,
        defaultForType = ArrayEntity.Function[].class
)public class ArrayEntity {

    @Id
    Long id;

    @Type(type = "pgsql_array")
    Function[] numbers;

    enum Function {}

}
