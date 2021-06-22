package com.pallasathenagroup.querydsl.hstore;

import com.querydsl.core.types.Operator;

public enum HstoreOps implements Operator {
    CONTAINS_KEY(Boolean.class),
    MAP_IS_EMPTY(Boolean.class),
    MAP_SIZE(Integer.class),
    GET(Object.class),
    KEYS(String.class),
    VALUES(String.class),
    CONCAT(Object.class);

    private final Class<?> type;

    private HstoreOps(Class<?> type) {
        this.type = type;
    }

    @Override
    public Class<?> getType() {
        return type;
    }
}
