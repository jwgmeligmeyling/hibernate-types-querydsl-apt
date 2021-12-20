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
import org.hibernate.type.BooleanType;
import org.hibernate.type.Type;

public class DurationFunctionInitializer implements MetadataBuilderInitializer {

    @Override
    public void contribute(MetadataBuilder metadataBuilder, StandardServiceRegistry standardServiceRegistry) {
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
