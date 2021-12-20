package com.pallasathenagroup.querydsl;

import com.pallasathenagroup.querydsl.range.RangeOps;
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

public class RangeFunctionInitializer implements MetadataBuilderInitializer {

    @Override
    public void contribute(MetadataBuilder metadataBuilder, StandardServiceRegistry standardServiceRegistry) {
        metadataBuilder.applySqlFunction("RANGE_OVERLAPS", new Operator(BooleanType.INSTANCE, "&&"));
        metadataBuilder.applySqlFunction("RANGE_CONTAINS", new Operator(BooleanType.INSTANCE, "@>"));
        metadataBuilder.applySqlFunction("RANGE_IS_CONTAINED_BY", new Operator(BooleanType.INSTANCE, "<@"));
        metadataBuilder.applySqlFunction("RANGE_STRICTLY_LEFT_OF", new Operator(BooleanType.INSTANCE, "<<"));
        metadataBuilder.applySqlFunction("RANGE_STRICTLY_RIGHT_OF", new Operator(BooleanType.INSTANCE, ">>"));
        metadataBuilder.applySqlFunction("RANGE_ADJACENT_TO", new Operator(BooleanType.INSTANCE, "-|-"));
        metadataBuilder.applySqlFunction("RANGE_UNION", new Operator(PostgreSQLGuavaRangeType.INSTANCE, "+"));
        metadataBuilder.applySqlFunction("RANGE_INTERSECTION", new Operator(PostgreSQLGuavaRangeType.INSTANCE, "*"));
        metadataBuilder.applySqlFunction("RANGE_DIFFERENCE", new Operator(PostgreSQLGuavaRangeType.INSTANCE, "-"));

        metadataBuilder.applySqlFunction("RANGE_LOWER_BOUND", new SQLFunctionTemplate(null, "LOWER(?1)") {
            @Override
            public Type getReturnType(Type argumentType, Mapping mapping) throws QueryException {
                if (argumentType == null) {
                    return null;
                }

                TypeHelper typeHelper = ((SessionFactoryImpl) mapping).getTypeHelper();
                Class<?> elementType = ((PostgreSQLGuavaRangeType) argumentType).getElementType();
                return typeHelper.basic(elementType);
            }
        });

        metadataBuilder.applySqlFunction("RANGE_UPPER_BOUND", new SQLFunctionTemplate(null, "UPPER(?1)") {
            @Override
            public Type getReturnType(Type argumentType, Mapping mapping) throws QueryException {
                if (argumentType == null) {
                    return null;
                }

                TypeHelper typeHelper = ((SessionFactoryImpl) mapping).getTypeHelper();
                Class<?> elementType = ((PostgreSQLGuavaRangeType) argumentType).getElementType();
                return typeHelper.basic(elementType);
            }
        });
    }

}
