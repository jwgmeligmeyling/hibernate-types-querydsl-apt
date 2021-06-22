package com.pallasathenagroup.querydsl.hstore;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.MapExpression;
import com.querydsl.core.types.Visitor;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class HstoreExpression extends SimpleExpression<Map<String, String>> implements MapExpression<String, String> {

    public HstoreExpression(Expression<Map<String, String>> mixin) {
        super(mixin);
    }

    public final BooleanExpression contains(String key, String value) {
        return this.get(key).eq(value);
    }

    public final BooleanExpression contains(Expression<String> key, Expression<String> value) {
        return this.get(key).eq(value);
    }

    public final BooleanExpression containsKey(Expression<String> key) {
        return Expressions.booleanOperation(HstoreOps.CONTAINS_KEY, this.mixin, key);
    }

    public final BooleanExpression containsKey(String key) {
        return Expressions.booleanOperation(HstoreOps.CONTAINS_KEY, this.mixin, ConstantImpl.create(key));
    }

    public final BooleanExpression isEmpty() {
        return Expressions.booleanOperation(HstoreOps.MAP_IS_EMPTY, this.mixin);
    }

    public final BooleanExpression isNotEmpty() {
        return this.isEmpty().not();
    }

    public final NumberExpression<Integer> size() {
        return Expressions.numberOperation(Integer.class, HstoreOps.MAP_SIZE, this.mixin);
    }

    public StringExpression get(Expression<String> expression) {
        return Expressions.stringOperation(HstoreOps.GET, this.mixin, expression);
    }

    public StringExpression get(String s) {
        return get(Expressions.constant(s));
    }

    @Override
    public Class<?> getParameter(int i) {
        return String.class;
    }

    @Override
    public <R, C> @Nullable R accept(Visitor<R, C> visitor, @Nullable C c) {
        return mixin.accept(visitor, c);
    }

}
