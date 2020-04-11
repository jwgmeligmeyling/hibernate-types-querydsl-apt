package com.pallasathenagroup.querydsl.duration;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Operation;
import com.querydsl.core.types.Operator;

import java.time.Duration;
import java.util.List;

public class DurationOperation extends DurationExpression implements Operation<Duration> {

    private final Operation<Duration> operation;

    public DurationOperation(Operation<Duration> operation) {
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
