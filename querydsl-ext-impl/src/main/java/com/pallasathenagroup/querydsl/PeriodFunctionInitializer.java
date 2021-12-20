package com.pallasathenagroup.querydsl;

import com.vladmihalcea.hibernate.type.interval.PostgreSQLPeriodType;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.spi.MetadataBuilderInitializer;
import org.hibernate.dialect.function.SQLFunctionTemplate;

public class PeriodFunctionInitializer implements MetadataBuilderInitializer {

    @Override
    public void contribute(MetadataBuilder metadataBuilder, StandardServiceRegistry standardServiceRegistry) {
        metadataBuilder.applySqlFunction("PERIOD_ADD", new Operator(PostgreSQLPeriodType.INSTANCE, "+"));
        metadataBuilder.applySqlFunction("PERIOD_SUBTRACT", new Operator(PostgreSQLPeriodType.INSTANCE, "-"));
        metadataBuilder.applySqlFunction("PERIOD_DIVIDE", new Operator(PostgreSQLPeriodType.INSTANCE, "/"));
        metadataBuilder.applySqlFunction("PERIOD_MULTIPLY", new Operator(PostgreSQLPeriodType.INSTANCE, "*"));
        metadataBuilder.applySqlFunction("PERIOD_BETWEEN", new SQLFunctionTemplate(PostgreSQLPeriodType.INSTANCE, "AGE(?1, ?2)"));
        metadataBuilder.applySqlFunction("PERIOD_AVG", new SQLFunctionTemplate(PostgreSQLPeriodType.INSTANCE, "AVG(?1)"));
        metadataBuilder.applySqlFunction("PERIOD_MAX", new SQLFunctionTemplate(PostgreSQLPeriodType.INSTANCE, "MAX(?1)"));
        metadataBuilder.applySqlFunction("PERIOD_MIN", new SQLFunctionTemplate(PostgreSQLPeriodType.INSTANCE, "MIN(?1)"));
        metadataBuilder.applySqlFunction("PERIOD_SUM", new SQLFunctionTemplate(PostgreSQLPeriodType.INSTANCE, "SUM(?1)"));

    }

}
