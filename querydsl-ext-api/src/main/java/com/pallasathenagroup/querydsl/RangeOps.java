package com.pallasathenagroup.querydsl;

import com.querydsl.core.types.Operator;

public enum RangeOps implements Operator {
    INTERSECTS(Boolean.class);

    private final Class<?> type;

    private RangeOps(Class<?> type) {
        this.type = type;
    }

    @Override
    public Class<?> getType() {
        return type;
    }
}
