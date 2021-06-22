package com.pallasathenagroup.querydsl;

import com.vladmihalcea.hibernate.type.basic.Iso8601MonthType;
import com.vladmihalcea.hibernate.type.basic.YearMonthTimestampType;
import com.vladmihalcea.hibernate.type.basic.YearType;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.spi.MetadataBuilderInitializer;
import org.hibernate.dialect.function.SQLFunctionTemplate;

public class YearMonthFunctionInitializer implements MetadataBuilderInitializer {

    @Override
    public void contribute(MetadataBuilder metadataBuilder, StandardServiceRegistry standardServiceRegistry) {
        metadataBuilder.applySqlFunction("CAST_YEARMONTH", new SQLFunctionTemplate(YearMonthTimestampType.INSTANCE, "?1"));
        metadataBuilder.applySqlFunction("CAST_MONTH", new SQLFunctionTemplate(Iso8601MonthType.INSTANCE, "?1"));
        metadataBuilder.applySqlFunction("CAST_YEAR", new SQLFunctionTemplate(YearType.INSTANCE, "?1"));
    }

}
