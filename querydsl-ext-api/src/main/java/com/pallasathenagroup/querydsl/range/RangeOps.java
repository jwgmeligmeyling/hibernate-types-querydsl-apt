package com.pallasathenagroup.querydsl.range;

import com.google.common.collect.Range;
import com.querydsl.core.types.Operator;

public enum RangeOps implements Operator {
    OVERLAPS(Boolean.class),
    CONTAINS(Boolean.class),
    IS_CONTAINED_BY(Boolean.class),
    STRICTLY_LEFT_OF(Boolean.class),
    STRICTLY_RIGHT_OF(Boolean.class),
    ADJACENT_TO(Boolean.class),
    UNION(Range.class),
    INTERSECTION(Range.class),
    DIFFERENCE(Range.class),
    LOWER_BOUND(Object.class),
    UPPER_BOUND(Object.class);

    private final Class<?> type;

    private RangeOps(Class<?> type) {
        this.type = type;
    }

    @Override
    public Class<?> getType() {
        return type;
    }
}
