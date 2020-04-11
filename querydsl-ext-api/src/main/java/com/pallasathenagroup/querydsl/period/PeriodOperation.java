package com.pallasathenagroup.querydsl.period;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Operation;
import com.querydsl.core.types.Operator;

import java.time.Period;
import java.util.List;

public class PeriodOperation extends PeriodExpression implements Operation<Period> {

    private final Operation<Period> operation;

    public PeriodOperation(Operation<Period> operation) {
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
