package com.pallasathenagroup.querydsl.duration;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;

import java.lang.reflect.AnnotatedElement;
import java.time.Duration;

public class DurationPath extends DurationExpression implements Path<Duration> {

    public DurationPath(Path<Duration> mixin) {
        super(mixin);
    }

    public DurationPath(PathMetadata pathMetadata) {
        this(ExpressionUtils.path(Duration.class, pathMetadata));
    }

    @Override
    public PathMetadata getMetadata() {
        return ((Path<Duration>) mixin).getMetadata();
    }

    @Override
    public Path<?> getRoot() {
        return ((Path<Duration>) mixin).getRoot();
    }

    @Override
    public AnnotatedElement getAnnotatedElement() {
        return ((Path<Duration>) mixin).getAnnotatedElement();
    }

}
