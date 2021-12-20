package com.pallasathenagroup.querydsl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.spi.MetadataBuilderInitializer;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.BooleanType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

public class JsonFunctionInitializer implements MetadataBuilderInitializer {

    private static final JsonBinaryType JSON_NODE_TYPE = new JsonBinaryType(JsonNode.class);
    private static final JsonBinaryType ARRAY_NODE_TYPE = new JsonBinaryType(ArrayNode.class);
    private static final JsonBinaryType OBJECT_NODE_TYPE = new JsonBinaryType(ObjectNode.class);

    @Override
    public void contribute(MetadataBuilder metadataBuilder, StandardServiceRegistry standardServiceRegistry) {
        metadataBuilder.applySqlFunction("JSON_CONTAINS_KEY", new Operator(BooleanType.INSTANCE, " ?? ")); // Note: First ? is a JDBC escape here.
        metadataBuilder.applySqlFunction("JSON_GET", new Operator(JSON_NODE_TYPE, "->"));
        metadataBuilder.applySqlFunction("JSON_GET_TEXT", new Operator(StringType.INSTANCE, "->>"));
        metadataBuilder.applySqlFunction("JSON_CONCAT", new Operator(JSON_NODE_TYPE, "||"));

        metadataBuilder.applySqlFunction("json_array_length", new StandardSQLFunction("json_array_length", IntegerType.INSTANCE));
        metadataBuilder.applySqlFunction("jsonb_array_length", new StandardSQLFunction("jsonb_array_length", IntegerType.INSTANCE));
        metadataBuilder.applySqlFunction("json_object_keys", new StandardSQLFunction("json_object_keys", StringType.INSTANCE));
        metadataBuilder.applySqlFunction("jsonb_object_keys", new StandardSQLFunction("jsonb_object_keys", StringType.INSTANCE));
        metadataBuilder.applySqlFunction("json_array_elements_text", new StandardSQLFunction("json_array_elements_text", StringType.INSTANCE));
        metadataBuilder.applySqlFunction("jsonb_array_elements_text", new StandardSQLFunction("jsonb_array_elements_text", StringType.INSTANCE));
        metadataBuilder.applySqlFunction("json_build_object", new StandardSQLFunction("json_build_object", OBJECT_NODE_TYPE));
        metadataBuilder.applySqlFunction("jsonb_build_object", new StandardSQLFunction("jsonb_build_object", OBJECT_NODE_TYPE));
        metadataBuilder.applySqlFunction("jsonb_build_array", new StandardSQLFunction("jsonb_build_array", ARRAY_NODE_TYPE));
        metadataBuilder.applySqlFunction("jsonb_build_array", new StandardSQLFunction("jsonb_build_array", ARRAY_NODE_TYPE));
    }

}
