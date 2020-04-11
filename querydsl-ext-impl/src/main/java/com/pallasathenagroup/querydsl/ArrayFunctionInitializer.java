package com.pallasathenagroup.querydsl;

import com.pallasathenagroup.querydsl.array.ArrayOps;
import com.vladmihalcea.hibernate.type.array.internal.AbstractArrayType;
import com.vladmihalcea.hibernate.type.array.internal.AbstractArrayTypeDescriptor;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import org.hibernate.QueryException;
import org.hibernate.TypeHelper;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.spi.MetadataBuilderInitializer;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.type.BasicType;
import org.hibernate.type.BooleanType;
import org.hibernate.type.CustomType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;

import java.lang.reflect.Field;
import java.util.function.Function;

public class ArrayFunctionInitializer implements MetadataBuilderInitializer {

    public static Field arrayObjectClassField;

    {
        try {
            arrayObjectClassField = AbstractArrayTypeDescriptor.class.getDeclaredField("arrayObjectClass");
            arrayObjectClassField.setAccessible(true);
        } catch (Exception e) {}
    }

    @Override
    public void contribute(MetadataBuilder metadataBuilder, StandardServiceRegistry standardServiceRegistry) {
        metadataBuilder.applySqlFunction(ArrayOps.OVERLAPS.name(), new SQLFunctionTemplate(BooleanType.INSTANCE, "?1 && ?2"));
        metadataBuilder.applySqlFunction(ArrayOps.CONTAINS.name(), new SQLFunctionTemplate(BooleanType.INSTANCE, "?1 @> ?2"));
        metadataBuilder.applySqlFunction(ArrayOps.IS_CONTAINED_BY.name(), new SQLFunctionTemplate(BooleanType.INSTANCE, "?1 <@ ?2"));
        metadataBuilder.applySqlFunction("ARRAY_DIMS", new SQLFunctionTemplate(StringType.INSTANCE, "ARRAY_DIMS(?1)"));
        metadataBuilder.applySqlFunction("ARRAY_MDIMS", new SQLFunctionTemplate(IntegerType.INSTANCE, "ARRAY_MDIMS(?1)"));
        metadataBuilder.applySqlFunction("ARRAY_LENGTH", new SQLFunctionTemplate(IntegerType.INSTANCE, "ARRAY_LENGTH(?1, 1)"));

        metadataBuilder.applySqlFunction("ARRAY_CONCAT", new SQLFunctionTemplate(null, "?1 || ?2") {
            @Override
            public Type getReturnType(Type argumentType, Mapping mapping) throws QueryException {
                return argumentType;
            }
        });

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


                Class getJavaType = getArrayObjectClass(javaTypeDescriptor);
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
                Class getJavaType = javaTypeDescriptor.getJavaType();
                Class componentType = getJavaType.getComponentType();
                Type basic = typeHelper.basic(componentType);

                if (basic == null) {
                    basic = new CustomType(new PostgreSQLEnumType(componentType));
                }

                return basic;
            }
        });

    }

    private static Class getArrayObjectClass(AbstractArrayTypeDescriptor javaTypeDescriptor) {
        try {
            return (Class) arrayObjectClassField.get(javaTypeDescriptor);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

}
