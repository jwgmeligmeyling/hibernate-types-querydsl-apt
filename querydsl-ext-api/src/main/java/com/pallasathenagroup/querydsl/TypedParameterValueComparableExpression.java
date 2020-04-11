package com.pallasathenagroup.querydsl;

import com.querydsl.core.types.CollectionExpression;
import com.querydsl.core.types.Constant;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseForEqBuilder;
import com.querydsl.core.types.dsl.Coalesce;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimpleExpression;

import javax.annotation.Nullable;

public abstract class TypedParameterValueComparableExpression<T extends Comparable> extends TypedParameterValueSimpleExpression<T> {

    public TypedParameterValueComparableExpression(Expression<T> mixin) {
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
    
    /**
     * Create an OrderSpecifier for ascending order of this expression
     *
     * @return ascending order by this
     */
    public final OrderSpecifier<T> asc() {
        return new OrderSpecifier<T>(Order.ASC, mixin);
    }

    /**
     * Create a {@code coalesce(this, exprs...)} expression
     *
     * @param exprs additional arguments
     * @return coalesce
     */
    @SuppressWarnings("unchecked")
    public final Coalesce<T> coalesce(Expression<?>...exprs) {
        Coalesce<T> coalesce = new Coalesce<T>(getType(), mixin);
        for (Expression expr : exprs) {
            coalesce.add(expr);
        }
        return coalesce;
    }

    /**
     * Create a {@code coalesce(this, args...)} expression
     *
     * @param args additional arguments
     * @return coalesce
     */
    public final Coalesce<T> coalesce(T... args) {
        Coalesce<T> coalesce = new Coalesce<T>(getType(), mixin);
        for (T arg : args) {
            coalesce.add(constant(arg));
        }
        return coalesce;
    }

    /**
     * Create an OrderSpecifier for descending order of this expression
     *
     * @return descending order by this
     */
    public final OrderSpecifier<T> desc() {
        return new OrderSpecifier<T>(Order.DESC, mixin);
    }

    @Override
    public ComparableExpression<T> as(Path<T> alias) {
        return Expressions.comparableOperation(getType(), Ops.ALIAS, mixin, alias);
    }

    @Override
    public ComparableExpression<T> as(String alias) {
        return as(ExpressionUtils.path(getType(), alias));
    }

    /**
     * Create a {@code this between from and to} expression
     *
     * <p>Is equivalent to {@code from <= this <= to}</p>
     *
     * @param from inclusive start of range
     * @param to inclusive end of range
     * @return this between from and to
     */
    public final BooleanExpression between(@Nullable T from, @Nullable T to) {
        if (from == null) {
            if (to != null) {
                return Expressions.booleanOperation(Ops.LOE, mixin, constant(to));
            } else {
                throw new IllegalArgumentException("Either from or to needs to be non-null");
            }
        } else if (to == null) {
            return Expressions.booleanOperation(Ops.GOE, mixin, constant(from));
        } else {
            return Expressions.booleanOperation(Ops.BETWEEN, mixin, constant(from), constant(to));
        }
    }

    /**
     * Create a {@code this between from and to} expression
     *
     * <p>Is equivalent to {@code from <= this <= to}</p>
     *
     * @param from inclusive start of range
     * @param to inclusive end of range
     * @return this between from and to
     */
    public final BooleanExpression between(@Nullable Expression<T> from, @Nullable Expression<T> to) {
        if (from == null) {
            if (to != null) {
                return Expressions.booleanOperation(Ops.LOE, mixin, to);
            } else {
                throw new IllegalArgumentException("Either from or to needs to be non-null");
            }
        } else if (to == null) {
            return Expressions.booleanOperation(Ops.GOE, mixin, from);
        } else {
            return Expressions.booleanOperation(Ops.BETWEEN, mixin, from, to);
        }

    }

    /**
     * Create a {@code this not between from and to} expression
     *
     * <p>Is equivalent to {@code this < from || this > to}</p>
     *
     * @param from inclusive start of range
     * @param to inclusive end of range
     * @return this not between from and to
     */
    public final BooleanExpression notBetween(T from, T to) {
        return between(from, to).not();
    }

    /**
     * Create a {@code this not between from and to} expression
     *
     * <p>Is equivalent to {@code this < from || this > to}</p>
     *
     * @param from inclusive start of range
     * @param to inclusive end of range
     * @return this not between from and to
     */
    public final BooleanExpression notBetween(Expression<T> from, Expression<T> to) {
        return between(from, to).not();
    }

    /**
     * Create a {@code this > right} expression
     *
     * @param right rhs of the comparison
     * @return this &gt; right
     * @see java.lang.Comparable#compareTo(Object)
     */
    public BooleanExpression gt(T right) {
        return gt(constant(right));
    }

    /**
     * Create a {@code this > right} expression
     *
     * @param right rhs of the comparison
     * @return this &gt; right
     * @see java.lang.Comparable#compareTo(Object)
     */
    public BooleanExpression gt(Expression<T> right) {
        return Expressions.booleanOperation(Ops.GT, mixin, right);
    }

    /**
     * Create a {@code this > all right} expression
     *
     * @param right rhs of the comparison
     * @return this &gt; all right
     */
    public BooleanExpression gtAll(CollectionExpression<?, ? super T> right) {
        return gt(ExpressionUtils.all(right));
    }

    /**
     * Create a {@code this > any right} expression
     *
     * @param right rhs of the comparison
     * @return this &gt; any right
     */
    public BooleanExpression gtAny(CollectionExpression<?, ? super T> right) {
        return gt(ExpressionUtils.any(right));
    }

    /**
     * Create a {@code this > all right} expression
     *
     * @param right rhs of the comparison
     * @return this &gt; all right
     */
    public BooleanExpression gtAll(SubQueryExpression<? extends T> right) {
        return gt(ExpressionUtils.all(right));
    }

    /**
     * Create a {@code this > any right} expression
     *
     * @param right rhs of the comparison
     * @return this &gt; any right
     */
    public BooleanExpression gtAny(SubQueryExpression<? extends T> right) {
        return gt(ExpressionUtils.any(right));
    }

    /**
     * Create a {@code this >= right} expression
     *
     * @param right rhs of the comparison
     * @return this &gt;= right
     * @see java.lang.Comparable#compareTo(Object)
     */
    public BooleanExpression goe(T right) {
        return goe(constant(right));
    }

    /**
     * Create a {@code this >= right} expression
     *
     * @param right rhs of the comparison
     * @return this &gt;= right
     * @see java.lang.Comparable#compareTo(Object)
     */
    public BooleanExpression goe(Expression<T> right) {
        return Expressions.booleanOperation(Ops.GOE, mixin, right);
    }

    /**
     * Create a {@code this >= all right} expression
     *
     * @param right rhs of the comparison
     * @return this &gt;= all right
     */
    public BooleanExpression goeAll(CollectionExpression<?, ? super T> right) {
        return goe(ExpressionUtils.all(right));
    }

    /**
     * Create a {@code this >= any right} expression
     *
     * @param right rhs of the comparison
     * @return this &gt;= any right
     */
    public BooleanExpression goeAny(CollectionExpression<?, ? super T> right) {
        return goe(ExpressionUtils.any(right));
    }

    /**
     * Create a {@code this >= all right} expression
     *
     * @param right rhs of the comparison
     * @return this &gt;= all right
     */
    public BooleanExpression goeAll(SubQueryExpression<? extends T> right) {
        return goe(ExpressionUtils.all(right));
    }

    /**
     * Create a {@code this >= any right} expression
     *
     * @param right rhs of the comparison
     * @return this &gt;= any right
     */
    public BooleanExpression goeAny(SubQueryExpression<? extends T> right) {
        return goe(ExpressionUtils.any(right));
    }

    /**
     * Create a {@code this < right} expression
     *
     * @param right rhs of the comparison
     * @return this &lt; right
     * @see java.lang.Comparable#compareTo(Object)
     */
    public final BooleanExpression lt(T right) {
        return lt(constant(right));
    }

    /**
     * Create a {@code this < right} expression
     *
     * @param right rhs of the comparison
     * @return this &lt; right
     * @see java.lang.Comparable#compareTo(Object)
     */
    public final BooleanExpression lt(Expression<T> right) {
        return Expressions.booleanOperation(Ops.LT, mixin, right);
    }

    /**
     * Create a {@code this < all right} expression
     *
     * @param right rhs of the comparison
     * @return this &lt; all right
     */
    public BooleanExpression ltAll(CollectionExpression<?, ? super T> right) {
        return lt(ExpressionUtils.all(right));
    }

    /**
     * Create a {@code this < any right} expression
     *
     * @param right rhs of the comparison
     * @return this &lt; any right
     */
    public BooleanExpression ltAny(CollectionExpression<?, ? super T> right) {
        return lt(ExpressionUtils.any(right));
    }

    /**
     * Create a {@code this < all right} expression
     *
     * @param right rhs of the comparison
     * @return this &lt; all right
     */
    public BooleanExpression ltAll(SubQueryExpression<? extends T> right) {
        return lt(ExpressionUtils.all(right));
    }

    /**
     * Create a {@code this < any right} expression
     *
     * @param right rhs of the comparison
     * @return this &lt; any right
     */
    public BooleanExpression ltAny(SubQueryExpression<? extends T> right) {
        return lt(ExpressionUtils.any(right));
    }

    /**
     * Create a {@code this <= right} expression
     *
     * @param right rhs of the comparison
     * @return this &lt;= right
     * @see java.lang.Comparable#compareTo(Object)
     */
    public final BooleanExpression loe(T right) {
        return Expressions.booleanOperation(Ops.LOE, mixin, constant(right));
    }

    /**
     * Create a {@code this <= right} expression
     *
     * @param right rhs of the comparison
     * @return this &lt;= right
     * @see java.lang.Comparable#compareTo(Object)
     */
    public final BooleanExpression loe(Expression<T> right) {
        return Expressions.booleanOperation(Ops.LOE, mixin, right);
    }

    /**
     * Create a {@code this <= all right} expression
     *
     * @param right rhs of the comparison
     * @return this &lt;= all right
     */
    public BooleanExpression loeAll(CollectionExpression<?, ? super T> right) {
        return loe(ExpressionUtils.all(right));
    }

    /**
     * Create a {@code this <= any right} expression
     *
     * @param right rhs of the comparison
     * @return this &lt;= any right
     */
    public BooleanExpression loeAny(CollectionExpression<?, ? super T> right) {
        return loe(ExpressionUtils.any(right));
    }

    /**
     * Create a {@code this <= all right} expression
     *
     * @param right rhs of the comparison
     * @return this &lt;= all right
     */
    public BooleanExpression loeAll(SubQueryExpression<? extends T> right) {
        return loe(ExpressionUtils.all(right));
    }

    /**
     * Create a {@code this <= any right} expression
     *
     * @param right rhs of the comparison
     * @return this &lt;= any right
     */
    public BooleanExpression loeAny(SubQueryExpression<? extends T> right) {
        return loe(ExpressionUtils.any(right));
    }

}
