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
        metadataBuilder.applySqlFunction(RangeOps.OVERLAPS.name(), new SQLFunctionTemplate(BooleanType.INSTANCE, "?1 && ?2"));
        metadataBuilder.applySqlFunction(RangeOps.CONTAINS.name(), new SQLFunctionTemplate(BooleanType.INSTANCE, "?1 @> ?2"));
        metadataBuilder.applySqlFunction(RangeOps.IS_CONTAINED_BY.name(), new SQLFunctionTemplate(BooleanType.INSTANCE, "?1 <@ ?2"));
        metadataBuilder.applySqlFunction(RangeOps.STRICTLY_LEFT_OF.name(), new SQLFunctionTemplate(BooleanType.INSTANCE, "?1 << ?2"));
        metadataBuilder.applySqlFunction(RangeOps.STRICTLY_RIGHT_OF.name(), new SQLFunctionTemplate(BooleanType.INSTANCE, "?1 >> ?2"));
        metadataBuilder.applySqlFunction(RangeOps.ADJACENT_TO.name(), new SQLFunctionTemplate(BooleanType.INSTANCE, "?1 -|- ?2"));

        metadataBuilder.applySqlFunction(RangeOps.UNION.name(), new SQLFunctionTemplate(PostgreSQLGuavaRangeType.INSTANCE, "?1 + ?2") {
            @Override
            public Type getReturnType(Type argumentType, Mapping mapping) throws QueryException {
                return argumentType;
            }
        });

        metadataBuilder.applySqlFunction(RangeOps.INTERSECTION.name(), new SQLFunctionTemplate(PostgreSQLGuavaRangeType.INSTANCE, "?1 * ?2") {
            @Override
            public Type getReturnType(Type argumentType, Mapping mapping) throws QueryException {
                return argumentType;
            }
        });

        metadataBuilder.applySqlFunction(RangeOps.DIFFERENCE.name(), new SQLFunctionTemplate(PostgreSQLGuavaRangeType.INSTANCE, "?1 - ?2") {
            @Override
            public Type getReturnType(Type argumentType, Mapping mapping) throws QueryException {
                return argumentType;
            }
        });

        metadataBuilder.applySqlFunction(RangeOps.LOWER_BOUND.name(), new SQLFunctionTemplate(null, "LOWER(?1)") {
            @Override
            public Type getReturnType(Type argumentType, Mapping mapping) throws QueryException {
                if (argumentType == null) {
                    return null;
                }

                TypeHelper typeHelper = ((SessionFactoryImpl) mapping).getTypeHelper();
                Class<?> elementType = ((ExtendedPostgreSQLGuavaRangeType) argumentType).getElementType();
                return typeHelper.basic(elementType);
            }
        });

        metadataBuilder.applySqlFunction(RangeOps.UPPER_BOUND.name(), new SQLFunctionTemplate(null, "UPPER(?1)") {
            @Override
            public Type getReturnType(Type argumentType, Mapping mapping) throws QueryException {
                if (argumentType == null) {
                    return null;
                }

                TypeHelper typeHelper = ((SessionFactoryImpl) mapping).getTypeHelper();
                Class<?> elementType = ((ExtendedPostgreSQLGuavaRangeType) argumentType).getElementType();
                return typeHelper.basic(elementType);
            }
        });
    }

}
