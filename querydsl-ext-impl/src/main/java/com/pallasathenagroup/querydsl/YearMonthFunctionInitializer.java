package com.pallasathenagroup.querydsl;

import com.vladmihalcea.hibernate.type.basic.YearMonthTimestampType;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.spi.MetadataBuilderInitializer;
import org.hibernate.dialect.function.SQLFunctionTemplate;

public class YearMonthFunctionInitializer implements MetadataBuilderInitializer {

    @Override
    public void contribute(MetadataBuilder metadataBuilder, StandardServiceRegistry standardServiceRegistry) {
        metadataBuilder.applySqlFunction("CAST_YEARMONTH", new SQLFunctionTemplate(YearMonthTimestampType.INSTANCE, "?1"));
    }

}
