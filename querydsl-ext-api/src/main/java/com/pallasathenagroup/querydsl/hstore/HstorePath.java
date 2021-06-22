package com.pallasathenagroup.querydsl.hstore;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;

import java.lang.reflect.AnnotatedElement;
import java.util.Map;

public class HstorePath extends HstoreExpression implements Path<Map<String, String>> {

    public HstorePath(Path<Map<String, String>> mixin) {
        super(mixin);
    }

    public HstorePath(PathMetadata pathMetadata) {
        this((Path) ExpressionUtils.path(Map.class, pathMetadata));
    }

    @Override
    public PathMetadata getMetadata() {
        return ((Path<Map<String, String>>) mixin).getMetadata();
    }

    @Override
    public Path<?> getRoot() {
        return ((Path<Map<String, String>>) mixin).getRoot();
    }

    @Override
    public AnnotatedElement getAnnotatedElement() {
        return ((Path<Map<String, String>>) mixin).getAnnotatedElement();
    }

}
