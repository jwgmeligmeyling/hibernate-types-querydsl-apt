package com.pallasathenagroup.querydsl.range;

import com.google.common.collect.Range;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathImpl;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.Visitor;

import javax.annotation.Nullable;
import java.lang.reflect.AnnotatedElement;

public class RangePath<X extends Comparable<?>> extends RangeExpression<X> implements Path<Range<X>> {

    private final PathImpl<Range<X>> pathMixin;

    protected RangePath(PathImpl<Range<X>> mixin) {
        super(mixin);
        this.pathMixin = mixin;
    }

    public RangePath(Path<?> parent, String property) {
        this(Range.class, parent, property);
    }

    public RangePath(Class type, Path<?> parent, String property) {
        this(type, PathMetadataFactory.forProperty(parent, property));
    }

    public RangePath(PathMetadata metadata) {
        this(Range.class, metadata);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public RangePath(Class type, PathMetadata metadata) {
        super(ExpressionUtils.path(type, metadata));
        this.pathMixin = (PathImpl<Range<X>>) mixin;
    }

    public RangePath(String var) {
        this(Range.class, PathMetadataFactory.forVariable(var));
    }


    @Nullable
    @Override
    public <R, C> R accept(Visitor<R, C> v, @Nullable C context) {
        return v.visit(pathMixin, context);
    }

    @Override
    public PathMetadata getMetadata() {
        return pathMixin.getMetadata();
    }

    @Override
    public Path<?> getRoot() {
        return pathMixin.getRoot();
    }

    @Override
    public AnnotatedElement getAnnotatedElement() {
        return pathMixin.getAnnotatedElement();
    }

}
