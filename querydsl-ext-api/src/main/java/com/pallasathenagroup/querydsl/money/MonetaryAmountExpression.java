package com.pallasathenagroup.querydsl.money;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Visitor;
import com.querydsl.core.types.dsl.SimpleExpression;

import javax.money.MonetaryAmount;

public class MonetaryAmountExpression extends SimpleExpression<MonetaryAmount> {

    public MonetaryAmountExpression(Expression<MonetaryAmount> mixin) {
        super(mixin);
    }

    @Override
    public <R, C> R accept(Visitor<R, C> v, C context) {
        return mixin.accept(v, context);
    }

}
