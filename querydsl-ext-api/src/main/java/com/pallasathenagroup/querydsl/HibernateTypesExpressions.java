package com.pallasathenagroup.querydsl;

import com.google.common.collect.Range;
import com.pallasathenagroup.querydsl.array.ArrayOps;
import com.pallasathenagroup.querydsl.array.PostgresqlArrayExpression;
import com.pallasathenagroup.querydsl.array.PostgresqlArrayOperation;
import com.pallasathenagroup.querydsl.duration.DurationExpression;
import com.pallasathenagroup.querydsl.duration.DurationOperation;
import com.pallasathenagroup.querydsl.duration.DurationOps;
import com.pallasathenagroup.querydsl.hstore.HstoreExpression;
import com.pallasathenagroup.querydsl.period.PeriodExpression;
import com.pallasathenagroup.querydsl.period.PeriodOperation;
import com.pallasathenagroup.querydsl.period.PeriodOps;
import com.pallasathenagroup.querydsl.range.RangeExpression;
import com.pallasathenagroup.querydsl.yearmonth.YearMonthExpression;
import com.pallasathenagroup.querydsl.yearmonth.YearMonthOps;
import com.querydsl.core.types.Constant;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.DateExpression;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.vladmihalcea.hibernate.type.basic.Iso8601MonthType;
import com.vladmihalcea.hibernate.type.basic.YearMonthTimestampType;
import com.vladmihalcea.hibernate.type.basic.YearType;
import com.vladmihalcea.hibernate.type.interval.PostgreSQLIntervalType;
import com.vladmihalcea.hibernate.type.interval.PostgreSQLPeriodType;
import com.vladmihalcea.hibernate.type.range.guava.PostgreSQLGuavaRangeType;
import org.hibernate.jpa.TypedParameterValue;

import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.util.Map;

import static com.pallasathenagroup.querydsl.yearmonth.YearMonthOps.CAST_YEARMONTH;

public final class HibernateTypesExpressions {

    @SafeVarargs
    public static <T> PostgresqlArrayExpression<T[], T> createArrayExpression(T... elements) {
        return new PostgresqlArrayExpression<T[], T>((Expression) Expressions.constant(PostgresqlArrayExpression.getTypedParameterValue(elements, null)), null);
    }

    @SafeVarargs
    public static <T extends Enum<? extends T>> PostgresqlArrayExpression<T[], T> createArrayExpression(String enumType, T... elements) {
        return new PostgresqlArrayExpression<T[], T>((Expression) Expressions.constant(PostgresqlArrayExpression.getTypedParameterValue(elements, enumType)), enumType);
    }

    public static <T extends Comparable<?>> RangeExpression<T> createRangeExpression(Range<T> range) {
        return new RangeExpression<T>((Expression) Expressions.constant(new TypedParameterValue(PostgreSQLGuavaRangeType.INSTANCE, range)));
    }

    public static DurationExpression duration(Duration duration) {
        return new DurationExpression((Expression) Expressions.constant(new TypedParameterValue(PostgreSQLIntervalType.INSTANCE, duration)));
    }

    public static DurationOperation durationBetween(DateTimeExpression<?> a, DateTimeExpression<?> b) {
        return new DurationOperation(Expressions.operation(Duration.class, DurationOps.BETWEEN, a, b));
    }

    public static <T extends Comparable<? super T>> DurationOperation durationBetween(DateTimeExpression<T> a, T b) {
        return new DurationOperation(Expressions.operation(Duration.class, DurationOps.BETWEEN, a, Expressions.constant(b)));
    }

    public static <T extends Comparable<? super T>> DurationOperation durationBetween(T a, DateTimeExpression<T> b) {
        return new DurationOperation(Expressions.operation(Duration.class, DurationOps.BETWEEN, Expressions.constant(a), b));
    }

    public static DurationOperation durationBetween(RangeExpression<LocalDateTime> range) {
        return durationBetween(Expressions.asDateTime(range.upper()), Expressions.asDateTime(range.lower()));
    }

    public static PeriodExpression period(Period period) {
        return new PeriodExpression((Expression) Expressions.constant(new TypedParameterValue(PostgreSQLPeriodType.INSTANCE, period)));
    }

    public static PeriodOperation periodBetween(DateExpression<?> a, DateExpression<?> b) {
        return new PeriodOperation(Expressions.operation(Period.class, PeriodOps.BETWEEN, a, b));
    }

    public static <T extends Comparable<? super T>> PeriodOperation periodBetween(DateExpression<T> a, T b) {
        return new PeriodOperation(Expressions.operation(Period.class, PeriodOps.BETWEEN, a, Expressions.constant(b)));
    }

    public static <T extends Comparable<? super T>> PeriodOperation periodBetween(T a, DateExpression<T> b) {
        return new PeriodOperation(Expressions.operation(Period.class, PeriodOps.BETWEEN, Expressions.constant(a), b));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static YearMonthExpression yearMonth(YearMonth yearMonth) {
        return new YearMonthExpression((Constant) Expressions.constant(new TypedParameterValue(YearMonthTimestampType.INSTANCE, yearMonth)));
    }

    public static YearMonthExpression yearMonth(DateExpression<?> dateExpression) {
        return new YearMonthExpression(Expressions.operation(YearMonth.class, CAST_YEARMONTH, Expressions.operation(dateExpression.getType(), Ops.DateTimeOps.TRUNC_MONTH, dateExpression)));
    }

    public static YearMonthExpression yearMonth(DateTimeExpression<?> dateExpression) {
        return new YearMonthExpression(Expressions.operation(YearMonth.class, CAST_YEARMONTH, Expressions.operation(dateExpression.getType(), Ops.DateTimeOps.TRUNC_MONTH, dateExpression)));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static ComparableExpression<Month> month(Month month) {
        return Expressions.asComparable((Constant) Expressions.constant(new TypedParameterValue(Iso8601MonthType.INSTANCE, month)));
    }

    public static ComparableExpression<Month> month(NumberExpression<Integer> expression) {
        return Expressions.enumOperation(Month.class, YearMonthOps.CAST_MONTH, expression);
    }

    public static ComparableExpression<Month> month(DateExpression<?> expression) {
        return month(expression.month());
    }

    public static ComparableExpression<Month> month(DateTimeExpression<?> expression) {
        return month(expression.month());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static ComparableExpression<Year> year(Year year) {
        return Expressions.asComparable((Constant) Expressions.constant(new TypedParameterValue(YearType.INSTANCE, year)));
    }

    public static ComparableExpression<Year> year(NumberExpression<Integer> expression) {
        return Expressions.comparableOperation(Year.class, YearMonthOps.CAST_YEAR, expression);
    }

    public static ComparableExpression<Year> year(DateExpression<?> expression) {
        return year(expression.year());
    }

    public static ComparableExpression<Year> year(DateTimeExpression<?> expression) {
        return year(expression.year());
    }

    public static <T extends Comparable<? super T>> PostgresqlArrayOperation<T[], T> arrayAgg(Expression<T> expression) {
        return new PostgresqlArrayOperation(Expressions.operation(Array.newInstance(expression.getType(), 0).getClass(), ArrayOps.ARRAY_AGG, expression), null);
    }

    public static HstoreExpression asHstore(Expression<Map<String, String>> expression) {
        return new HstoreExpression(expression);
    }

}
