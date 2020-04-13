package com.pallasathenagroup.querydsl;

import com.google.common.collect.Range;
import com.pallasathenagroup.querydsl.array.PostgresqlArrayExpression;
import com.pallasathenagroup.querydsl.duration.DurationExpression;
import com.pallasathenagroup.querydsl.duration.DurationOperation;
import com.pallasathenagroup.querydsl.duration.DurationOps;
import com.pallasathenagroup.querydsl.period.PeriodExpression;
import com.pallasathenagroup.querydsl.period.PeriodOperation;
import com.pallasathenagroup.querydsl.period.PeriodOps;
import com.pallasathenagroup.querydsl.range.RangeExpression;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.DateExpression;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.vladmihalcea.hibernate.type.interval.PostgreSQLIntervalType;
import com.vladmihalcea.hibernate.type.interval.PostgreSQLPeriodType;
import org.hibernate.jpa.TypedParameterValue;

import java.time.Duration;
import java.time.Period;

public final class HibernateTypesExpressions {

    public static <T> PostgresqlArrayExpression<T[], T> createArrayExpression(T... elements) {
        return new PostgresqlArrayExpression<T[], T>((Expression) Expressions.constant(PostgresqlArrayExpression.getTypedParameterValue(elements, null)), null);
    }

    public static <T extends Enum<? extends T>> PostgresqlArrayExpression<T[], T> createArrayExpression(String enumType, T... elements) {
        return new PostgresqlArrayExpression<T[], T>((Expression) Expressions.constant(PostgresqlArrayExpression.getTypedParameterValue(elements, enumType)), enumType);
    }

    public static <T extends Comparable<?>> RangeExpression<T> createRangeExpression(Range<T> range) {
        return new RangeExpression<T>((Expression) Expressions.constant(new TypedParameterValue(ExtendedPostgreSQLGuavaRangeType.INSTANCE, range)));
    }

    public static DurationExpression duration(Duration duration) {
        return new DurationExpression((Expression) Expressions.constant(new TypedParameterValue(PostgreSQLIntervalType.INSTANCE, duration)));
    }

    public static DurationOperation durationBetween(DateTimeExpression<?> a, DateTimeExpression<?> b) {
        return new DurationOperation(Expressions.operation(Duration.class, DurationOps.BETWEEN, a, b));
    }

    public static <T extends Comparable> DurationOperation durationBetween(DateTimeExpression<T> a, T b) {
        return new DurationOperation(Expressions.operation(Duration.class, DurationOps.BETWEEN, a, Expressions.constant(b)));
    }

    public static <T extends Comparable> DurationOperation durationBetween(T a, DateTimeExpression<T> b) {
        return new DurationOperation(Expressions.operation(Duration.class, DurationOps.BETWEEN, Expressions.constant(a), b));
    }

    public static PeriodExpression period(Period period) {
        return new PeriodExpression((Expression) Expressions.constant(new TypedParameterValue(PostgreSQLPeriodType.INSTANCE, period)));
    }

    public static PeriodOperation periodBetween(DateExpression<?> a, DateExpression<?> b) {
        return new PeriodOperation(Expressions.operation(Period.class, PeriodOps.BETWEEN, a, b));
    }

    public static <T extends Comparable> PeriodOperation periodBetween(DateExpression<T> a, T b) {
        return new PeriodOperation(Expressions.operation(Period.class, PeriodOps.BETWEEN, a, Expressions.constant(b)));
    }

    public static <T extends Comparable> PeriodOperation periodBetween(T a, DateExpression<T> b) {
        return new PeriodOperation(Expressions.operation(Period.class, PeriodOps.BETWEEN, Expressions.constant(a), b));
    }
}
