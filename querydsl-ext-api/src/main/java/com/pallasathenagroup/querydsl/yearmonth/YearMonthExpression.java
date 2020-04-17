package com.pallasathenagroup.querydsl.yearmonth;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Visitor;
import com.querydsl.core.types.dsl.DateExpression;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.Expressions;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.YearMonth;

public class YearMonthExpression extends DateExpression<YearMonth> {

    public YearMonthExpression(Expression<YearMonth> mixin) {
        super(mixin);
    }

    @Nullable
    @Override
    public <R, C> R accept(Visitor<R, C> v, @Nullable C context) {
        return mixin.accept(v, context);
    }

    public DateTimeExpression<LocalDateTime> atStartOfMonth() {
        return Expressions.dateTimeTemplate(LocalDateTime.class, "{0}", mixin);
    }

}
