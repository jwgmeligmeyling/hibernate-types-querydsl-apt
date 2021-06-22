package com.pallasathenagroup.querydsl.json;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Operation;
import com.querydsl.core.types.Operator;

import java.util.List;

public class JsonOperation<T> extends JsonExpression<T> implements Operation<T> {

    private final Operation<T> operation;

    public JsonOperation(Operation<T> operation) {
        super(operation);
        this.operation = operation;
    }

    @Override
    public Expression<?> getArg(int index) {
        return operation.getArg(index);
    }

    @Override
    public List<Expression<?>> getArgs() {
        return operation.getArgs();
    }

    @Override
    public Operator getOperator() {
        return operation.getOperator();
    }
}
