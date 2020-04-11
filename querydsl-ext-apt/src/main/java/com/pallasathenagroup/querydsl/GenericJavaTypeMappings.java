package com.pallasathenagroup.querydsl;

import com.mysema.codegen.model.Type;
import com.querydsl.codegen.EntityType;
import com.querydsl.codegen.JavaTypeMappings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class GenericJavaTypeMappings extends JavaTypeMappings {

    private final Map<TypeKey, Type> queryTypes = new HashMap();

    public static class TypeKey {
        private final String fullName;
        private final List<TypeKey> parameters;

        public TypeKey(Type type) {
            this.fullName = type.getFullName();
            this.parameters = type.getParameters() != null ?
                type.getParameters().stream().map(TypeKey::new).collect(Collectors.toList()) : null;
        }

        public TypeKey(String fullName, List<TypeKey> parameters) {
            this.fullName = fullName;
            this.parameters = parameters;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TypeKey typeKey = (TypeKey) o;
            return Objects.equals(fullName, typeKey.fullName) &&
                    Objects.equals(parameters, typeKey.parameters);
        }

        @Override
        public int hashCode() {
            return Objects.hash(fullName, parameters);
        }

    }

    public Type getExprType(Type type, EntityType model, boolean raw, boolean rawParameters, boolean extend) {
        TypeKey key = new TypeKey(type);
        if (queryTypes.containsKey(key)) {
            return queryTypes.get(key);
        }
        return super.getExprType(type, model, raw, rawParameters, extend);
    }

    public Type getPathType(Type type, EntityType model, boolean raw, boolean rawParameters, boolean extend) {
        TypeKey key = new TypeKey(type);
        if (queryTypes.containsKey(key)) {
            return queryTypes.get(key);
        }
        return super.getPathType(type, model, raw, rawParameters, extend);
    }

    public void register(Type type, Type queryType) {
        this.queryTypes.put(new TypeKey(type), queryType);
        super.register(type, queryType);
    }

}
