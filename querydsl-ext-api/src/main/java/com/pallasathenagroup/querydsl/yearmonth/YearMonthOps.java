package com.pallasathenagroup.querydsl.yearmonth;

import com.querydsl.core.types.Operator;

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;

public enum YearMonthOps implements Operator {
    CAST_YEARMONTH(YearMonth.class),
    CAST_YEAR(Year.class),
    CAST_MONTH(Month.class);

    private final Class<?> type;

    private YearMonthOps(Class<?> type) {
        this.type = type;
    }

    @Override
    public Class<?> getType() {
        return type;
    }
}
