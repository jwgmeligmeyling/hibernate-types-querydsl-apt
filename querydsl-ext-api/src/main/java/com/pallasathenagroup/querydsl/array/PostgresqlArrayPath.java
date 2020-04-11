package com.pallasathenagroup.querydsl.array;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathImpl;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.EntityPathBase;

import javax.persistence.Column;
import java.lang.reflect.AnnotatedElement;

public class PostgresqlArrayPath<A, T> extends PostgresqlArrayExpression<A, T> implements Path<A> {

    private final PathImpl<A> pathMixin;

    protected PostgresqlArrayPath(PathImpl<A> mixin) {
        super(mixin, getColumnDefinitionFromMetadata(mixin.getMetadata()));
        this.pathMixin = mixin;
    }

    public PostgresqlArrayPath(Class type, Path<?> parent, String property) {
        this(type, PathMetadataFactory.forProperty(parent, property));
    }

    public PostgresqlArrayPath(PathMetadata metadata) {
        this(getTypeFromMetadata(metadata), metadata);
    }

    public PostgresqlArrayPath(Class type, PathMetadata metadata) {
        super(ExpressionUtils.path(type, metadata), getColumnDefinitionFromMetadata(metadata));
        this.pathMixin = (PathImpl<A>) mixin;
    }

    private static Class getTypeFromMetadata(PathMetadata metadata) {
        try {
            return ((EntityPathBase) metadata.getParent()).getType().getDeclaredField(metadata.getName()).getType();
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    private static String getColumnDefinitionFromMetadata(PathMetadata metadata) {
        try {
            String columnDefinition = ((EntityPathBase) metadata.getParent()).getType().getDeclaredField(metadata.getName())
                    .getAnnotation(Column.class).columnDefinition();
            return columnDefinition.substring(0, columnDefinition.indexOf("["));
        } catch (NoSuchFieldException e) {
            return null;
        }
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
