package com.pallasathenagroup.querydsl.period;

import com.pallasathenagroup.querydsl.TypedParameterValueSimpleExpression;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Visitor;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.vladmihalcea.hibernate.type.interval.PostgreSQLIntervalType;
import com.vladmihalcea.hibernate.type.interval.PostgreSQLPeriodType;
import org.hibernate.jpa.TypedParameterValue;

import javax.annotation.Nullable;
import java.time.Period;

public class PeriodExpression extends TypedParameterValueSimpleExpression<Period> {

    public PeriodExpression(Expression<Period> mixin) {
        super(mixin);
    }

    public PeriodOperation max() {
        return new PeriodOperation(Expressions.operation(Period.class, PeriodOps.MAX, this));
    }

    public PeriodOperation sum() {
        return new PeriodOperation(Expressions.operation(Period.class, PeriodOps.SUM, this));
    }

    public PeriodOperation min() {
        return new PeriodOperation(Expressions.operation(Period.class, PeriodOps.MIN, this));
    }

    public PeriodOperation avg() {
        return new PeriodOperation(Expressions.operation(Period.class, PeriodOps.AVG, this));
    }

    public PeriodOperation add(Expression<Period> Period) {
        return new PeriodOperation(Expressions.operation(Period.class, PeriodOps.ADD, mixin, Period));
    }

    public PeriodOperation subtract(Expression<Period> Period) {
        return new PeriodOperation(Expressions.operation(Period.class, PeriodOps.SUBTRACT, mixin, Period));
    }

    protected Expression<Period> constant(Period Period) {
        return (Expression) Expressions.constant(new TypedParameterValue(PostgreSQLPeriodType.INSTANCE, Period));
    }

    public PeriodOperation add(Period Period) {
        return new PeriodOperation(Expressions.operation(Period.class, PeriodOps.ADD, mixin, constant(Period)));
    }

    public PeriodOperation subtract(Period Period) {
        return new PeriodOperation(Expressions.operation(Period.class, PeriodOps.SUBTRACT, mixin,  constant(Period)));
    }

    public PeriodOperation multiply(NumberExpression<?> numberExpression) {
        return new PeriodOperation(Expressions.operation(Period.class, PeriodOps.MULTIPLY, mixin,  numberExpression));
    }

    public PeriodOperation divide(NumberExpression<?> numberExpression) {
        return new PeriodOperation(Expressions.operation(Period.class, PeriodOps.DIVIDE, mixin,  numberExpression));
    }

    public PeriodOperation multiply(Number number) {
        return new PeriodOperation(Expressions.operation(Period.class, PeriodOps.MULTIPLY, mixin, Expressions.constant(number)));
    }

    public PeriodOperation divide(Number number) {
        return new PeriodOperation(Expressions.operation(Period.class, PeriodOps.DIVIDE, mixin, Expressions.constant(number)));
    }

    /**
     * Create a month expression
     *
     * @return month
     */
    public NumberExpression<Integer> month() {
        return Expressions.numberOperation(Integer.class, Ops.DateTimeOps.MONTH, mixin);
    }

    /**
     * Create a days expression
     *
     * @return month
     */
    public NumberExpression<Integer> day() {
        return Expressions.numberOperation(Integer.class, Ops.DateTimeOps.DAY_OF_MONTH, mixin);
    }

    /**
     * Create a year expression
     *
     * @return year
     */
    public NumberExpression<Integer> year() {
        return Expressions.numberOperation(Integer.class, Ops.DateTimeOps.YEAR, mixin);
    }

    @Nullable
    @Override
    public <R, C> R accept(Visitor<R, C> v, @Nullable C context) {
        return mixin.accept(v, context);
    }

}
