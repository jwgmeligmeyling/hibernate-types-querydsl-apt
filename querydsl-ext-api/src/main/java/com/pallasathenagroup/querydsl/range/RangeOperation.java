package com.pallasathenagroup.querydsl.range;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Operation;
import com.querydsl.core.types.OperationImpl;
import com.querydsl.core.types.Operator;
import com.querydsl.core.types.Visitor;

import java.util.List;

public class RangeOperation<X extends Comparable<?>> extends RangeExpression<X> implements Operation<Range<X>>  {

    private final OperationImpl<Range<X>> opMixin;

    protected RangeOperation(OperationImpl<Range<X>> mixin) {
        super(mixin);
        this.opMixin = mixin;
    }

    protected RangeOperation(Class<Range<X>> type, Operator op, Expression<?>... args) {
        this(type, op, ImmutableList.copyOf(args));
    }

    protected RangeOperation(Class<Range<X>> type, Operator op, ImmutableList<Expression<?>> args) {
        super(ExpressionUtils.operation(type, op, args));
        this.opMixin = (OperationImpl<Range<X>>) mixin;
    }

    @Override
    public final <R,C> R accept(Visitor<R,C> v, C context) {
        return v.visit(opMixin, context);
    }

    @Override
    public Expression<?> getArg(int index) {
        return opMixin.getArg(index);
    }

    @Override
    public List<Expression<?>> getArgs() {
        return opMixin.getArgs();
    }

    @Override
    public Operator getOperator() {
        return opMixin.getOperator();
    }

}
