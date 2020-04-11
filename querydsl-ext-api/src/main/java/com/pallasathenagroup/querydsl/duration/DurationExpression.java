package com.pallasathenagroup.querydsl.duration;

import com.pallasathenagroup.querydsl.TypedParameterValueComparableExpression;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Visitor;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.vladmihalcea.hibernate.type.interval.PostgreSQLIntervalType;
import org.hibernate.jpa.TypedParameterValue;

import javax.annotation.Nullable;
import java.time.Duration;

public class DurationExpression extends TypedParameterValueComparableExpression<Duration> {

    public DurationExpression(Expression<Duration> mixin) {
        super(mixin);
    }

    public DurationOperation max() {
        return new DurationOperation(Expressions.operation(Duration.class, DurationOps.MAX, this));
    }

    public DurationOperation sum() {
        return new DurationOperation(Expressions.operation(Duration.class, DurationOps.SUM, this));
    }

    public DurationOperation min() {
        return new DurationOperation(Expressions.operation(Duration.class, DurationOps.MIN, this));
    }

    public DurationOperation avg() {
        return new DurationOperation(Expressions.operation(Duration.class, DurationOps.AVG, this));
    }

    public DurationOperation add(Expression<Duration> duration) {
        return new DurationOperation(Expressions.operation(Duration.class, DurationOps.ADD, mixin, duration));
    }

    public DurationOperation subtract(Expression<Duration> duration) {
        return new DurationOperation(Expressions.operation(Duration.class, DurationOps.SUBTRACT, mixin, duration));
    }

    protected Expression<Duration> constant(Duration duration) {
        return (Expression) Expressions.constant(new TypedParameterValue(PostgreSQLIntervalType.INSTANCE, duration));
    }

    public DurationOperation add(Duration duration) {
        return new DurationOperation(Expressions.operation(Duration.class, DurationOps.ADD, mixin, constant(duration)));
    }

    public DurationOperation subtract(Duration duration) {
        return new DurationOperation(Expressions.operation(Duration.class, DurationOps.SUBTRACT, mixin,  constant(duration)));
    }

    public DurationOperation multiply(NumberExpression<?> numberExpression) {
        return new DurationOperation(Expressions.operation(Duration.class, DurationOps.MULTIPLY, mixin,  numberExpression));
    }

    public DurationOperation divide(NumberExpression<?> numberExpression) {
        return new DurationOperation(Expressions.operation(Duration.class, DurationOps.DIVIDE, mixin,  numberExpression));
    }

    public DurationOperation multiply(Number number) {
        return new DurationOperation(Expressions.operation(Duration.class, DurationOps.MULTIPLY, mixin, Expressions.constant(number)));
    }

    public DurationOperation divide(Number number) {
        return new DurationOperation(Expressions.operation(Duration.class, DurationOps.DIVIDE, mixin, Expressions.constant(number)));
    }

    /**
     * Create a hours expression (range 0-23)
     *
     * @return hour
     */
    public NumberExpression<Integer> hour() {
        return Expressions.numberOperation(Integer.class, Ops.DateTimeOps.HOUR, mixin);
    }


    /**
     * Create a milliseconds expression (range 0-999)
     * <p>Is always 0 in HQL and JDOQL modules</p>
     *
     * @return milli seconds
     */
    public NumberExpression<Integer> milliSecond() {
        return Expressions.numberOperation(Integer.class, Ops.DateTimeOps.MILLISECOND, mixin);
    }

    /**
     * Create a minutes expression (range 0-59)
     *
     * @return minute
     */
    public NumberExpression<Integer> minute() {
        return Expressions.numberOperation(Integer.class, Ops.DateTimeOps.MINUTE, mixin);
    }

    /**
     * Create a seconds expression (range 0-59)
     *
     * @return second
     */
    public NumberExpression<Integer> second() {
        return Expressions.numberOperation(Integer.class, Ops.DateTimeOps.SECOND, mixin);
    }

    @Nullable
    @Override
    public <R, C> R accept(Visitor<R, C> v, @Nullable C context) {
        return mixin.accept(v, context);
    }

}
