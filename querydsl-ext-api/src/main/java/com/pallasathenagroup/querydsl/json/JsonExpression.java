package com.pallasathenagroup.querydsl.json;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Visitor;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringExpression;

public class JsonExpression<T> extends SimpleExpression<T> {

    public JsonExpression(Expression<T> mixin) {
        super(mixin);
    }

    @Override
    public <R, C> @org.jetbrains.annotations.Nullable R accept(Visitor<R, C> v, @org.jetbrains.annotations.Nullable C context) {
        return mixin.accept(v, context);
    }

    public BooleanExpression containsKey(Expression<String> key) {
        return Expressions.booleanOperation(JsonOps.CONTAINS_KEY, mixin, key);
    }

    public BooleanExpression containsKey(String key) {
        return containsKey(Expressions.constant(key));
    }

    public JsonOperation<T> get(Expression<?> key) {
        return new JsonOperation<T>(Expressions.operation(getType(), JsonOps.GET, mixin, key)) {
            @Override
            public StringExpression asText() {
                return Expressions.stringOperation(JsonOps.GET_TEXT, JsonExpression.this.mixin, key);
            }
        };
    }

    public JsonExpression<T> get(Integer index) {
        return get(Expressions.constant(index));
    }

    public JsonExpression<T> get(String key) {
        return get(Expressions.constant(key));
    }

    public StringExpression asText() {
        throw new UnsupportedOperationException();
    }

    public JsonOperation<T> concat(JsonExpression<?> other) {
        return new JsonOperation<>(Expressions.operation(getType(), JsonOps.CONCAT, mixin, other));
    }

    public final NumberExpression<Integer> size() {
        return Expressions.numberOperation(Integer.class, JsonOps.MAP_SIZE, mixin);
    }

    public final StringExpression keys() {
        return Expressions.stringOperation(JsonOps.KEYS, mixin);
    }

    public final StringExpression elements() {
        return Expressions.stringOperation(JsonOps.ELEMENTS, mixin);
    }

}
