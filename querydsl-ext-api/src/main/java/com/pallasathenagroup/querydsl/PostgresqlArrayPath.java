package com.pallasathenagroup.querydsl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.ArrayPath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.SimplePath;

public class PostgresqlArrayPath<A, E> extends ArrayPath<A, E> {

    public PostgresqlArrayPath(Class<? super A> type, String variable) {
        super(type, variable);
    }

    public PostgresqlArrayPath(Class<? super A> type, Path<?> parent, String property) {
        super(type, parent, property);
    }

    public PostgresqlArrayPath(Class<? super A> type, PathMetadata metadata) {
        super(type, metadata);
    }

    public PostgresqlArrayPath(PathMetadata metadata) {
        super(getTypeFromMetadata(metadata), metadata);
    }

    private static Class getTypeFromMetadata(PathMetadata metadata) {
        try {
            return ((EntityPathBase) metadata.getParent()).getType().getDeclaredField(metadata.getName()).getType();
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    @Override
    public SimplePath<E> get(Expression<Integer> index) {
        return super.get(index);
    }

    @Override
    public SimplePath<E> get(int index) {
        return super.get(index);
    }

    @Override
    public NumberExpression<Integer> size() {
        return super.size();
    }
}
