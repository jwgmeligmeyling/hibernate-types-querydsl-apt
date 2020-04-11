package com.pallasathenagroup.querydsl.array;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Operation;
import com.querydsl.core.types.Operator;

import java.util.List;

public class PostgresqlArrayOperation<A, T> extends PostgresqlArrayExpression<A, T> implements Operation<A> {

    public PostgresqlArrayOperation(Operation<A> operation, String columnDefinition) {
        super(operation, columnDefinition);
    }

    @Override
    public Expression<?> getArg(int index) {
        return ((Operation<T[]>) mixin).getArg(index);
    }

    @Override
    public List<Expression<?>> getArgs() {
        return ((Operation<T[]>) mixin).getArgs();
    }

    @Override
    public Operator getOperator() {
        return ((Operation<T[]>) mixin).getOperator();
    }

}
