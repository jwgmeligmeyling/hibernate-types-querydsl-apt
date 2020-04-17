package com.pallasathenagroup.querydsl.array;

import com.pallasathenagroup.querydsl.TypedParameterValueSimpleExpression;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Visitor;
import com.querydsl.core.types.dsl.ArrayExpression;
import com.querydsl.core.types.dsl.BooleanOperation;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberOperation;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringOperation;
import com.vladmihalcea.hibernate.type.array.DateArrayType;
import com.vladmihalcea.hibernate.type.array.DoubleArrayType;
import com.vladmihalcea.hibernate.type.array.EnumArrayType;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.LongArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.array.TimestampArrayType;
import com.vladmihalcea.hibernate.type.array.UUIDArrayType;
import org.hibernate.jpa.TypedParameterValue;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@SuppressWarnings("unchecked")
public class PostgresqlArrayExpression<A, T> extends TypedParameterValueSimpleExpression<A> implements ArrayExpression<A, T> {

    private final String columnDefinition;

    public PostgresqlArrayExpression(Expression<A> mixin, String columnDefinition) {
        super(mixin);
        this.columnDefinition = columnDefinition;
    }

    @Override
    public NumberExpression<Integer> size() {
        return Expressions.numberOperation(Integer.class, ArrayOps.LENGTH, this);
    }

    @Override
    public SimpleExpression<T> get(Expression<Integer> index) {
        return Expressions.simpleOperation((Class<? extends T>) mixin.getType().getComponentType(), ArrayOps.ELEMENT_AT, this, index);
    }

    @Override
    public SimpleExpression<T> get(int index) {
        return get(Expressions.constant(index));
    }

    public BooleanOperation overlaps(PostgresqlArrayExpression<A, T> expression) {
        return Expressions.predicate(ArrayOps.OVERLAPS, this, expression);
    }

    public BooleanOperation contains(PostgresqlArrayExpression<A, T> expression) {
        return Expressions.predicate(ArrayOps.CONTAINS, this, expression);
    }

    public BooleanOperation contains(Expression<T> expression) {
        return Expressions.predicate(ArrayOps.CONTAINS_ELEMENT, this, expression);
    }

    public BooleanOperation isContainedBy(PostgresqlArrayExpression<A, T> expression) {
        return Expressions.predicate(ArrayOps.IS_CONTAINED_BY, this, expression);
    }

    public BooleanOperation overlaps(T... other) {
        return Expressions.predicate(ArrayOps.OVERLAPS, this, new PostgresqlArrayExpression<>(Expressions.constant(getTypedParameterValue(other, columnDefinition)), columnDefinition));
    }

    public BooleanOperation contains(T... other) {
        return Expressions.predicate(ArrayOps.CONTAINS, this, new PostgresqlArrayExpression<>(Expressions.constant(getTypedParameterValue(other, columnDefinition)), columnDefinition));
    }

    public BooleanOperation isContainedBy(T... other) {
        return Expressions.predicate(ArrayOps.IS_CONTAINED_BY, this, new PostgresqlArrayExpression<>(Expressions.constant(getTypedParameterValue(other, columnDefinition)), columnDefinition));
    }

    public SimpleExpression<T> unnest() {
        return Expressions.simpleOperation((Class<? extends T>) mixin.getType().getComponentType(), ArrayOps.UNNEST, this);
    }

    public PostgresqlArrayOperation<A, T> append(Expression<T> element) {
        return new PostgresqlArrayOperation<>(Expressions.simpleOperation(getType(), ArrayOps.APPEND, this, element), columnDefinition);
    }

    public PostgresqlArrayOperation<A, T> prepend(Expression<T> element) {
        return new PostgresqlArrayOperation<>(Expressions.simpleOperation(getType(), ArrayOps.PREPEND, this, element), columnDefinition);
    }

    public PostgresqlArrayOperation<A, T> append(T element) {
        return new PostgresqlArrayOperation<>(Expressions.simpleOperation(getType(), ArrayOps.APPEND, this, Expressions.constant(element)), columnDefinition);
    }

    public PostgresqlArrayOperation<A, T> prepend(T element) {
        return new PostgresqlArrayOperation<>(Expressions.simpleOperation(getType(), ArrayOps.PREPEND, this, Expressions.constant(element)), columnDefinition);
    }

    public PostgresqlArrayOperation<A, T> concat(PostgresqlArrayExpression<A, T> other) {
        return new PostgresqlArrayOperation<>(Expressions.simpleOperation(getType(), ArrayOps.CONCAT, this, other), columnDefinition);
    }

    public PostgresqlArrayOperation<A, T> concat(T... other) {
        return new PostgresqlArrayOperation<>(Expressions.simpleOperation(getType(), ArrayOps.CONCAT, this, Expressions.constant(getTypedParameterValue(other, columnDefinition))), columnDefinition);
    }

    public NumberOperation<Integer> ndims() {
        return Expressions.numberOperation(Integer.class, ArrayOps.NDIMS, this);
    }

    public StringOperation dims() {
        return Expressions.stringOperation(ArrayOps.DIMS, this);
    }

    @Override
    protected Expression<A> constant(A right) {
        return (Expression) Expressions.constant(getTypedParameterValue((T[]) right, columnDefinition));
    }

    @Nullable
    @Override
    public <R, C> R accept(Visitor<R, C> v, @Nullable C context) {
        return mixin.accept(v, context);
    }

    public static <T> Object getTypedParameterValue(T[] other, String columnDefinition) {
        Class<?> componentType = other.getClass().getComponentType();
        if (componentType.equals(UUID.class)) {
            return new TypedParameterValue(UUIDArrayType.INSTANCE, other);
        } else if (componentType.equals(String.class)) {
            return new TypedParameterValue(StringArrayType.INSTANCE, other);
        } else if (componentType.equals(int.class) || componentType.equals(Integer.class)) {
            return new TypedParameterValue(IntArrayType.INSTANCE, other);
        } else if (componentType.equals(long.class) || componentType.equals(Long.class)) {
            return new TypedParameterValue(LongArrayType.INSTANCE, other);
        } else if (componentType.equals(double.class) || componentType.equals(Double.class)) {
            return new TypedParameterValue(DoubleArrayType.INSTANCE, other);
        } else if (Timestamp.class.isAssignableFrom(componentType)) {
            return new TypedParameterValue(TimestampArrayType.INSTANCE, other);
        } else if (Date.class.isAssignableFrom(componentType)) {
            return new TypedParameterValue(DateArrayType.INSTANCE, other);
        } else if (Enum.class.isAssignableFrom(componentType)) {
            return new TypedParameterValue(new EnumArrayType(componentType, columnDefinition), other);
        } else {
            throw new IllegalArgumentException("No type for " + componentType);
        }
    }

}
