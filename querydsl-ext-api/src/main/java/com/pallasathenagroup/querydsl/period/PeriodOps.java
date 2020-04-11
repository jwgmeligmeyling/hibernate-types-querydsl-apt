package com.pallasathenagroup.querydsl.period;

import com.querydsl.core.types.Operator;

import java.time.Period;

public enum PeriodOps implements Operator {
    ADD(Period.class),
    SUBTRACT(Period.class),
    MULTIPLY(Period.class),
    DIVIDE(Period.class),
    AVG(Period.class),
    MAX(Period.class),
    MIN(Period.class),
    SUM(Period.class),
    BETWEEN(Period.class);

    private final Class<?> type;

    private PeriodOps(Class<?> type) {
        this.type = type;
    }

    @Override
    public Class<?> getType() {
        return type;
    }
}
