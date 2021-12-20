package com.pallasathenagroup.querydsl;

import com.vladmihalcea.hibernate.type.array.DateArrayType;
import com.vladmihalcea.hibernate.type.array.DoubleArrayType;
import com.vladmihalcea.hibernate.type.array.EnumArrayType;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.LongArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.array.TimestampArrayType;
import com.vladmihalcea.hibernate.type.array.UUIDArrayType;
import com.vladmihalcea.hibernate.type.array.internal.AbstractArrayType;
import com.vladmihalcea.hibernate.type.array.internal.AbstractArrayTypeDescriptor;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import com.vladmihalcea.hibernate.type.util.ReflectionUtils;
import org.hibernate.QueryException;
import org.hibernate.TypeHelper;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.spi.MetadataBuilderInitializer;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.type.BooleanType;
import org.hibernate.type.CustomType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.usertype.UserType;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class ArrayFunctionInitializer implements MetadataBuilderInitializer {

    @Override
    public void contribute(MetadataBuilder metadataBuilder, StandardServiceRegistry standardServiceRegistry) {
        metadataBuilder.applySqlFunction("ARRAY_OVERLAPS", new Operator(BooleanType.INSTANCE, "&&"));
        metadataBuilder.applySqlFunction("ARRAY_CONTAINS", new Operator(BooleanType.INSTANCE, "@>"));
        metadataBuilder.applySqlFunction("ARRAY_CONTAINS_ELEMENT", new SQLFunctionTemplate(BooleanType.INSTANCE, "?1 @> ARRAY[?2]"));
        metadataBuilder.applySqlFunction("ARRAY_IS_CONTAINED_BY", new Operator(BooleanType.INSTANCE, "<@"));
        metadataBuilder.applySqlFunction("ARRAY_DIMS", new SQLFunctionTemplate(StringType.INSTANCE, "ARRAY_DIMS(?1)"));
        metadataBuilder.applySqlFunction("ARRAY_MDIMS", new SQLFunctionTemplate(IntegerType.INSTANCE, "ARRAY_MDIMS(?1)"));
        metadataBuilder.applySqlFunction("ARRAY_LENGTH", new SQLFunctionTemplate(IntegerType.INSTANCE, "ARRAY_LENGTH(?1, 1)"));
        metadataBuilder.applySqlFunction("ARRAY_CONCAT", new Operator(null, "||"));

        metadataBuilder.applySqlFunction("ARRAY_APPEND", new SQLFunctionTemplate(null, "ARRAY_APPEND(?1, ?2)") {
            @Override
            public Type getReturnType(Type argumentType, Mapping mapping) throws QueryException {
                return argumentType;
            }
        });


        metadataBuilder.applySqlFunction("ARRAY_PREPEND", new SQLFunctionTemplate(null, "ARRAY_PREPEND(?2, ?1)") {
            @Override
            public Type getReturnType(Type argumentType, Mapping mapping) throws QueryException {
                return argumentType;
            }
        });

        metadataBuilder.applySqlFunction("ARRAY_FILL", new SQLFunctionTemplate(null, "ARRAY_FILL(?1, ?2)") {
            @Override
            public Type getReturnType(Type argumentType, Mapping mapping) throws QueryException {
                return argumentType;
            }
        });

        metadataBuilder.applySqlFunction("ARRAY_ELEMENT_AT", new SQLFunctionTemplate(null, "?1[?2+1]") {
            @Override
            public Type getReturnType(Type argumentType, Mapping mapping) throws QueryException {
                if (argumentType == null) {
                    return null;
                }

                TypeHelper typeHelper = ((SessionFactoryImpl) mapping).getTypeHelper();
                AbstractArrayTypeDescriptor javaTypeDescriptor = (AbstractArrayTypeDescriptor) ((AbstractArrayType) argumentType).getJavaTypeDescriptor();
                Class<?> getJavaType = ReflectionUtils.getFieldValue(javaTypeDescriptor, "arrayObjectClass");
                Class componentType = getJavaType.getComponentType();
                Type basic = typeHelper.basic(componentType);

                if (basic == null) {
                    basic = new CustomType(new PostgreSQLEnumType(componentType));
                }

                return basic;
            }
        });

        metadataBuilder.applySqlFunction("ARRAY_UNNEST", new SQLFunctionTemplate(null, "UNNEST(?1)") {
            @Override
            public Type getReturnType(Type argumentType, Mapping mapping) throws QueryException {
                if (argumentType == null) {
                    return null;
                }

                TypeHelper typeHelper = ((SessionFactoryImpl) mapping).getTypeHelper();
                JavaTypeDescriptor javaTypeDescriptor = ((AbstractArrayType) argumentType).getJavaTypeDescriptor();
                Class getJavaType = ReflectionUtils.<Class<?>> getFieldValue(javaTypeDescriptor, "arrayObjectClass");
                Class componentType = getJavaType.getComponentType();
                Type basic = typeHelper.basic(componentType);

                if (basic == null) {
                    basic = new CustomType(new PostgreSQLEnumType(componentType));
                }

                return basic;
            }
        });

        metadataBuilder.applySqlFunction("ARRAY_AGG", new SQLFunctionTemplate(null, "ARRAY_AGG(?1)") {
            @Override
            public Type getReturnType(Type argumentType, Mapping mapping) throws QueryException {
                if (argumentType == null) {
                    return null;
                }

                Class<?> componentType = argumentType.getReturnedClass();

                if (argumentType instanceof CustomType) {
                    CustomType customType = (CustomType) argumentType;
                    UserType userType = customType.getUserType();
                    if (userType instanceof PostgreSQLEnumType) {
                        return new EnumArrayType(Array.newInstance(componentType, 0).getClass(), "");
                    }
                } else if (componentType.equals(UUID.class)) {
                    return UUIDArrayType.INSTANCE;
                } else if (componentType.equals(String.class)) {
                    return StringArrayType.INSTANCE;
                } else if (componentType.equals(int.class) || componentType.equals(Integer.class)) {
                    return IntArrayType.INSTANCE;
                } else if (componentType.equals(long.class) || componentType.equals(Long.class)) {
                    return LongArrayType.INSTANCE;
                } else if (componentType.equals(double.class) || componentType.equals(Double.class)) {
                    return DoubleArrayType.INSTANCE;
                } else if (Timestamp.class.isAssignableFrom(componentType)) {
                    return TimestampArrayType.INSTANCE;
                } else if (Date.class.isAssignableFrom(componentType)) {
                    return DateArrayType.INSTANCE;
                }

                throw new IllegalStateException("Array_agg is not supported for " + componentType + " mapped by " + argumentType);
            }
        });

    }

}
