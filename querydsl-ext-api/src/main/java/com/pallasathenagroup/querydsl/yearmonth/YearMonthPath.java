package com.pallasathenagroup.querydsl.yearmonth;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;

import java.lang.reflect.AnnotatedElement;
import java.time.YearMonth;

public class YearMonthPath extends YearMonthExpression implements Path<YearMonth> {

    public YearMonthPath(Path<YearMonth> mixin) {
        super(mixin);
    }

    public YearMonthPath(PathMetadata pathMetadata) {
        this(ExpressionUtils.path(YearMonth.class, pathMetadata));
    }

    @Override
    public PathMetadata getMetadata() {
        return ((Path<YearMonth>) mixin).getMetadata();
    }

    @Override
    public Path<?> getRoot() {
        return ((Path<YearMonth>) mixin).getRoot();
    }

    @Override
    public AnnotatedElement getAnnotatedElement() {
        return ((Path<YearMonth>) mixin).getAnnotatedElement();
    }

}
