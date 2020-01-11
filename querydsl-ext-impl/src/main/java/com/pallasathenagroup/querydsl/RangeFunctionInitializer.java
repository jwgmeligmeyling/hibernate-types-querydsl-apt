package com.pallasathenagroup.querydsl;

import com.vladmihalcea.hibernate.type.range.guava.PostgreSQLGuavaRangeType;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.spi.MetadataBuilderInitializer;
import org.hibernate.dialect.function.SQLFunctionTemplate;

public class RangeFunctionInitializer implements MetadataBuilderInitializer {

    @Override
    public void contribute(MetadataBuilder metadataBuilder, StandardServiceRegistry standardServiceRegistry) {
        metadataBuilder.applySqlFunction(RangeOps.INTERSECTS.name(), new SQLFunctionTemplate(PostgreSQLGuavaRangeType.INSTANCE, "?1 && ?2"));
    }

}
