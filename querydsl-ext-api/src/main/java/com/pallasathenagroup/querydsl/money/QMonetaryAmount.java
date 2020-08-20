package com.pallasathenagroup.querydsl.money;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.FactoryExpression;
import com.querydsl.core.types.Visitor;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.util.Arrays;
import java.util.List;

public class QMonetaryAmount implements FactoryExpression<MonetaryAmount> {

    private final NumberExpression<?> amount;
    private final Expression<? extends CurrencyUnit> currencyUnit;

    public QMonetaryAmount(Expression<? extends Number> amount, Expression<? extends CurrencyUnit> currencyUnit) {
        this.amount = Expressions.asNumber((Expression) amount);
        this.currencyUnit = currencyUnit;
    }

    public QMonetaryAmount(MonetaryAmountPath monetaryAmount) {
        this(monetaryAmount.amount, monetaryAmount.currencyUnit);
    }

    public QMonetaryAmount sum() {
        return new QMonetaryAmount(amount.sum(), currencyUnit);
    }

    public QMonetaryAmount avg() {
        return new QMonetaryAmount(amount.avg(), currencyUnit);
    }

    public QMonetaryAmount min() {
        return new QMonetaryAmount(amount.min(), currencyUnit);
    }

    public QMonetaryAmount max() {
        return new QMonetaryAmount(amount.max(), currencyUnit);
    }

    @Override
    public List<Expression<?>> getArgs() {
        return Arrays.asList(amount, currencyUnit);
    }

    @Override
    public MonetaryAmount newInstance(Object... args) {
        Number amount = (Number) args[0];
        CurrencyUnit currencyUnit = (CurrencyUnit) args[1];
        return Monetary.getDefaultAmountFactory().setCurrency(currencyUnit).setNumber(amount).create();
    }

    @Override
    public <R, C> R accept(Visitor<R, C> v, C context) {
        return v.visit(this, context);
    }

    @Override
    public Class<? extends MonetaryAmount> getType() {
        return MonetaryAmount.class;
    }

}
