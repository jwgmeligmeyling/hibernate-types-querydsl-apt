package com.pallasathenagroup.querydsl.period;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;

import java.lang.reflect.AnnotatedElement;
import java.time.Period;

public class PeriodPath extends PeriodExpression implements Path<Period> {

    public PeriodPath(Path<Period> mixin) {
        super(mixin);
    }

    public PeriodPath(PathMetadata pathMetadata) {
        this(ExpressionUtils.path(Period.class, pathMetadata));
    }

    @Override
    public PathMetadata getMetadata() {
        return ((Path<Period>) mixin).getMetadata();
    }

    @Override
    public Path<?> getRoot() {
        return ((Path<Period>) mixin).getRoot();
    }

    @Override
    public AnnotatedElement getAnnotatedElement() {
        return ((Path<Period>) mixin).getAnnotatedElement();
    }

}
