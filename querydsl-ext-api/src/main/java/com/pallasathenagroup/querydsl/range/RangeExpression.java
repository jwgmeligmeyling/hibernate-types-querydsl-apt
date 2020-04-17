package com.pallasathenagroup.querydsl.range;

import com.google.common.collect.Range;
import com.pallasathenagroup.querydsl.TypedParameterValueSimpleExpression;
import com.querydsl.core.types.Constant;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Visitor;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseForEqBuilder;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.vladmihalcea.hibernate.type.range.guava.PostgreSQLGuavaRangeType;
import org.hibernate.jpa.TypedParameterValue;

import javax.annotation.Nullable;

public class RangeExpression<X extends Comparable<?>> extends TypedParameterValueSimpleExpression<Range<X>> {

    public RangeExpression(Expression<Range<X>> mixin) {
        super(mixin);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected Constant<Range<X>> constant(Range<X> other) {
        return (Constant) ConstantImpl.create(new TypedParameterValue(PostgreSQLGuavaRangeType.INSTANCE, other));
    }

    public BooleanExpression overlaps(Range<X> other) {
        return overlaps(constant(other));
    }

    public BooleanExpression overlaps(Expression<Range<X>> other) {
        return Expressions.booleanOperation(RangeOps.OVERLAPS, mixin, other);
    }

    public BooleanExpression strictlyLeftOf(Range<X> other) {
        return strictlyLeftOf(constant(other));
    }

    public BooleanExpression strictlyLeftOf(Expression<Range<X>> other) {
        return Expressions.booleanOperation(RangeOps.STRICTLY_LEFT_OF, mixin, other);
    }

    public BooleanExpression strictlyRightOf(Range<X> other) {
        return strictlyRightOf(constant(other));
    }

    public BooleanExpression strictlyRightOf(Expression<Range<X>> other) {
        return Expressions.booleanOperation(RangeOps.STRICTLY_RIGHT_OF, mixin, other);
    }

    public BooleanExpression adjacentTo(Range<X> other) {
        return adjacentTo(constant(other));
    }

    public BooleanExpression adjacentTo(Expression<Range<X>> other) {
        return Expressions.booleanOperation(RangeOps.ADJACENT_TO, mixin, other);
    }

    public BooleanExpression contains(Range<X> other) {
        return contains(constant(other));
    }

    public BooleanExpression contains(Expression<Range<X>> other) {
        return Expressions.booleanOperation(RangeOps.CONTAINS, mixin, other);
    }

    public BooleanExpression contains(X other) {
        return containsElement(ConstantImpl.create(other));
    }

    public BooleanExpression containsElement(Expression<?> other) {
        return Expressions.booleanOperation(RangeOps.CONTAINS, mixin, other);
    }

    public BooleanExpression isContainedBy(Range<X> other) {
        return isContainedBy(constant(other));
    }

    public BooleanExpression isContainedBy(Expression<Range<X>> other) {
        return Expressions.booleanOperation(RangeOps.IS_CONTAINED_BY, mixin, other);
    }

    public BooleanExpression isContainedBy(X other) {
        return isContainedByElement(ConstantImpl.create(other));
    }

    public BooleanExpression isContainedByElement(Expression<X> other) {
        return Expressions.booleanOperation(RangeOps.IS_CONTAINED_BY, mixin, other);
    }

    public RangeExpression<X> union(Range<X> other) {
        return union(constant(other));
    }

    public RangeExpression<X> union(Expression<Range<X>> other) {
        return new RangeOperation(Range.class, RangeOps.UNION, mixin, other);
    }

    public RangeExpression<X> intersection(Range<X> other) {
        return intersection(constant(other));
    }

    public RangeExpression<X> intersection(Expression<Range<X>> other) {
        return new RangeOperation(Range.class, RangeOps.INTERSECTION, mixin, other);
    }

    public RangeExpression<X> difference(Range<X> other) {
        return difference(constant(other));
    }

    public RangeExpression<X> difference(Expression<Range<X>> other) {
        return new RangeOperation(Range.class, RangeOps.DIFFERENCE, mixin, other);
    }

    @SuppressWarnings("unchecked")
    public ComparableExpression<X> lower() {
        return (ComparableExpression) Expressions.comparableOperation(Comparable.class, RangeOps.LOWER_BOUND, mixin);
    }

    @SuppressWarnings("unchecked")
    public ComparableExpression<X> upper() {
        return (ComparableExpression) Expressions.comparableOperation(Comparable.class, RangeOps.UPPER_BOUND, mixin);
    }

    @Nullable
    @Override
    public <R, C> R accept(Visitor<R, C> v, @Nullable C context) {
        return mixin.accept(v, context);
    }
}
