package com.pallasathenagroup.querydsl.duration;

import com.querydsl.core.types.Operator;

import java.time.Duration;

public enum DurationOps implements Operator {
    ADD(Duration.class),
    SUBTRACT(Duration.class),
    MULTIPLY(Duration.class),
    DIVIDE(Duration.class),
    AVG(Duration.class),
    MAX(Duration.class),
    MIN(Duration.class),
    SUM(Duration.class),
    BETWEEN(Duration.class);

    private final Class<?> type;

    private DurationOps(Class<?> type) {
        this.type = type;
    }

    @Override
    public Class<?> getType() {
        return type;
    }
}
