package com.pallasathenagroup.querydsl.json;

import com.querydsl.core.types.Operator;

public enum JsonOps implements Operator {
    CONTAINS(Boolean.class),
    IS_CONTAINED_BY(Boolean.class),
    ELEMENT_AT(Object.class),
    GET(Object.class),
    GET_TEXT(String.class),
    CONTAINS_KEY(Boolean.class),
    MAP_SIZE(Object.class),
    KEYS(String.class),
    ELEMENTS(String.class),
    CONCAT(Object.class),
    DELETE_KEY(Object.class),
    DELETE_INDEX(String.class),
    TO_JSON(Object.class),
    ARRAY_TO_JSON(Object.class),
    ROW_TO_JSON(Object.class),
    JSON_BUILD_ARRAY(Object.class),
    JSON_BUILD_OBJECT(Object.class),
    LENGTH(Object.class);

    private final Class<?> type;

    private JsonOps(Class<?> type) {
        this.type = type;
    }

    @Override
    public Class<?> getType() {
        return type;
    }
}
