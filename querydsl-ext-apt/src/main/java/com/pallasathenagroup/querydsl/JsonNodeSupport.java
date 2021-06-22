package com.pallasathenagroup.querydsl;

import com.fasterxml.jackson.databind.JsonNode;
import com.pallasathenagroup.querydsl.json.JsonPath;
import com.querydsl.codegen.AbstractModule;
import com.querydsl.codegen.CodegenModule;
import com.querydsl.codegen.Extension;
import com.querydsl.codegen.TypeMappings;
import com.querydsl.codegen.utils.model.SimpleType;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class JsonNodeSupport implements Extension {

    private static void registerTypes(TypeMappings typeMappings) {
        SimpleType jsonNode = new SimpleType(JsonNode.class.getName());
        SimpleType jsonPath = new SimpleType(JsonPath.class.getName());

        typeMappings.register(jsonNode, new SimpleType(jsonPath, Collections.singletonList(jsonNode)));
    }

    private static void addImports(AbstractModule module, String packageName) {
        @SuppressWarnings("unchecked")
        Set<String> imports = module.get(Set.class, CodegenModule.IMPORTS);
        if (imports.isEmpty()) {
            imports = Collections.singleton(packageName);
        } else {
            Set<String> old = imports;
            imports = new HashSet<>();
            imports.addAll(old);
            imports.add(packageName);
        }
        module.bind(CodegenModule.IMPORTS, imports);
    }

    public void addSupport(AbstractModule module) {
        registerTypes(module.get(TypeMappings.class));
        addImports(module,"com.pallasathenagroup.querydsl");
    }


}
