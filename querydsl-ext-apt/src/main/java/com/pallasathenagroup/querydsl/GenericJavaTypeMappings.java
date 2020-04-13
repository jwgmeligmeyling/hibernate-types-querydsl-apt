package com.pallasathenagroup.querydsl;

import com.mysema.codegen.model.Type;
import com.querydsl.codegen.EntityType;
import com.querydsl.codegen.JavaTypeMappings;

import java.util.HashMap;
import java.util.Map;

public class GenericJavaTypeMappings extends JavaTypeMappings {

    private final Map<Type, Type> queryTypes = new HashMap();

    public Type getExprType(Type type, EntityType model, boolean raw, boolean rawParameters, boolean extend) {
        if (queryTypes.containsKey(type)) {
            return queryTypes.get(type);
        }
        return super.getExprType(type, model, raw, rawParameters, extend);
    }

    public Type getPathType(Type type, EntityType model, boolean raw, boolean rawParameters, boolean extend) {
        if (queryTypes.containsKey(type)) {
            return queryTypes.get(type);
        }
        return super.getPathType(type, model, raw, rawParameters, extend);
    }

    public void register(Type type, Type queryType) {
        this.queryTypes.put(type, queryType);
        super.register(type, queryType);
    }

}
