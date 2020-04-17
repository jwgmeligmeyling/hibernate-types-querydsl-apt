package com.pallasathenagroup.querydsl.array;

import com.querydsl.core.types.Operator;

public enum ArrayOps implements Operator {
    OVERLAPS(Boolean.class),
    CONTAINS(Boolean.class),
    CONTAINS_ELEMENT(Boolean.class),
    IS_CONTAINED_BY(Boolean.class),
    CONCAT(Object.class),
    APPEND(Object.class),
    PREPEND(Object.class),
    NDIMS(Integer.class),
    DIMS(String.class),
    FILL(Object.class),
    LENGTH(Integer.class),
    TOSTRING(String.class),
    UNNEST(Object.class),
    ELEMENT_AT(Object.class),
    ARRAY_AGG(Object.class);

    private final Class<?> type;

    private ArrayOps(Class<?> type) {
        this.type = type;
    }

    @Override
    public Class<?> getType() {
        return type;
    }
}
