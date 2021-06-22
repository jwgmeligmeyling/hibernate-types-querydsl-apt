package com.pallasathenagroup.querydsl.hstore;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Operation;
import com.querydsl.core.types.Operator;

import java.util.List;
import java.util.Map;

public class HstoreOperation extends HstoreExpression implements Operation<Map<String, String>> {

    private final Operation<Map<String, String>> operation;

    public HstoreOperation(Operation<Map<String, String>> operation) {
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
