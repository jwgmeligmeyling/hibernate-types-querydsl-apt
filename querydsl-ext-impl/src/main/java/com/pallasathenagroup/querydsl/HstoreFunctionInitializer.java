package com.pallasathenagroup.querydsl;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLHStoreType;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.spi.MetadataBuilderInitializer;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.BooleanType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

public class HstoreFunctionInitializer implements MetadataBuilderInitializer {

    @Override
    public void contribute(MetadataBuilder metadataBuilder, StandardServiceRegistry standardServiceRegistry) {
        metadataBuilder.applySqlFunction("HSTORE_CONTAINS_KEY", new SQLFunctionTemplate(PostgreSQLHStoreType.INSTANCE, "akeys(?1) @> ARRAY[?2]"));
        metadataBuilder.applySqlFunction("HSTORE_MAP_SIZE", new SQLFunctionTemplate(IntegerType.INSTANCE, "array_length(akeys(?1))"));
        metadataBuilder.applySqlFunction("HSTORE_GET", new SQLFunctionTemplate(StringType.INSTANCE, "?1->?2"));
        metadataBuilder.applySqlFunction("HSTORE_GET_MULTIPLE", new SQLFunctionTemplate(StringArrayType.INSTANCE, "?1->?2"));
        metadataBuilder.applySqlFunction("HSTORE_MAP_IS_EMPTY", new SQLFunctionTemplate(BooleanType.INSTANCE, "array_length(akeys(?1)) = 0"));
        metadataBuilder.applySqlFunction("HSTORE_CONCAT", new SQLFunctionTemplate(PostgreSQLHStoreType.INSTANCE, "?1 || ?2"));

        metadataBuilder.applySqlFunction("skeys", new StandardSQLFunction("skeys", StringType.INSTANCE));
        metadataBuilder.applySqlFunction("akeys", new StandardSQLFunction("akeys", StringArrayType.INSTANCE));
        metadataBuilder.applySqlFunction("svals", new StandardSQLFunction("svals", StringType.INSTANCE));
        metadataBuilder.applySqlFunction("avals", new StandardSQLFunction("svals", StringArrayType.INSTANCE));
        metadataBuilder.applySqlFunction("hstore_to_array", new StandardSQLFunction("hstore_to_array", StringArrayType.INSTANCE));
    }

}
