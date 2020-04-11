package com.pallasathenagroup.querydsl;

import com.querydsl.core.types.Constant;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseForEqBuilder;
import com.querydsl.core.types.dsl.SimpleExpression;

public abstract class TypedParameterValueSimpleExpression<T> extends SimpleExpression<T> {

    public TypedParameterValueSimpleExpression(Expression<T> mixin) {
        super(mixin);
    }

    protected abstract Expression<T> constant(T value);

    @Override
    public BooleanExpression eq(T right) {
        return super.eq(constant(right));
    }

    @Override
    public BooleanExpression in(T... right) {
        Expression<T>[] rhs = new Constant[right.length];
        for (int i = 0; i < right.length; i++) {
            rhs[i] = constant(right[i]);
        }
        return super.in(rhs);
    }

    @Override
    public BooleanExpression ne(T right) {
        return super.ne(constant(right));
    }

    @Override
    public BooleanExpression notIn(T... right) {
        Expression<T>[] rhs = new Constant[right.length];
        for (int i = 0; i < right.length; i++) {
            rhs[i] = constant(right[i]);
        }
        return super.notIn(rhs);
    }

    @Override
    public SimpleExpression<T> nullif(T other) {
        return super.nullif(constant(other));
    }

    @Override
    public CaseForEqBuilder<T> when(T other) {
        return super.when(constant(other));
    }

}
