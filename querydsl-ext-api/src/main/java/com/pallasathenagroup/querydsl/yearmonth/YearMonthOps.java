package com.pallasathenagroup.querydsl.yearmonth;

import com.querydsl.core.types.Operator;

import java.time.YearMonth;

public enum YearMonthOps implements Operator {
    CAST_YEARMONTH(YearMonth.class);

    private final Class<?> type;

    private YearMonthOps(Class<?> type) {
        this.type = type;
    }

    @Override
    public Class<?> getType() {
        return type;
    }
}
