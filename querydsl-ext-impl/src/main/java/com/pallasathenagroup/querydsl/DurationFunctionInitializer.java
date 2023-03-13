package com.pallasathenagroup.querydsl;

import com.pallasathenagroup.querydsl.range.RangeOps;
import com.vladmihalcea.hibernate.type.interval.PostgreSQLIntervalType;
import com.vladmihalcea.hibernate.type.range.guava.PostgreSQLGuavaRangeType;
import org.hibernate.QueryException;
import org.hibernate.TypeHelper;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.spi.MetadataBuilderInitializer;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.ReturnableType;
import org.hibernate.query.spi.QueryEngine;
import org.hibernate.query.sqm.function.SelfRenderingSqmFunction;
import org.hibernate.query.sqm.function.SqmFunctionDescriptor;
import org.hibernate.query.sqm.produce.function.ArgumentsValidator;
import org.hibernate.query.sqm.tree.SqmTypedNode;
import org.hibernate.type.BooleanType;
import org.hibernate.type.Type;
import org.hibernate.type.spi.TypeConfiguration;

import java.util.List;

public class DurationFunctionInitializer implements MetadataBuilderInitializer {

    @Override
    public void contribute(MetadataBuilder metadataBuilder, StandardServiceRegistry standardServiceRegistry) {
        metadataBuilder.applySqlFunction("", new SqmFunctionDescriptor() {
            @Override
            public <T> SelfRenderingSqmFunction<T> generateSqmExpression(List<? extends SqmTypedNode<?>> arguments, ReturnableType<T> impliedResultType, QueryEngine queryEngine, TypeConfiguration typeConfiguration) {
                return null;
            }

            @Override
            public ArgumentsValidator getArgumentsValidator() {
                return null;
            }
        });
        metadataBuilder.applySqlFunction("DURATION_ADD", new Operator(PostgreSQLIntervalType.INSTANCE, "+"));
        metadataBuilder.applySqlFunction("DURATION_SUBTRACT", new Operator(PostgreSQLIntervalType.INSTANCE, "-"));
        metadataBuilder.applySqlFunction("DURATION_DIVIDE", new Operator(PostgreSQLIntervalType.INSTANCE, "/"));
        metadataBuilder.applySqlFunction("DURATION_MULTIPLY", new Operator(PostgreSQLIntervalType.INSTANCE, "*"));
        metadataBuilder.applySqlFunction("DURATION_BETWEEN", new SQLFunctionTemplate(PostgreSQLIntervalType.INSTANCE, "?1::timestamp - ?2::timestamp"));
        metadataBuilder.applySqlFunction("DURATION_AVG", new SQLFunctionTemplate(PostgreSQLIntervalType.INSTANCE, "AVG(?1)"));
        metadataBuilder.applySqlFunction("DURATION_MAX", new SQLFunctionTemplate(PostgreSQLIntervalType.INSTANCE, "MAX(?1)"));
        metadataBuilder.applySqlFunction("DURATION_MIN", new SQLFunctionTemplate(PostgreSQLIntervalType.INSTANCE, "MIN(?1)"));
        metadataBuilder.applySqlFunction("DURATION_SUM", new SQLFunctionTemplate(PostgreSQLIntervalType.INSTANCE, "SUM(?1)"));

    }

}
