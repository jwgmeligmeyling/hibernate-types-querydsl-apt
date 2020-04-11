package com.pallasathenagroup.querydsl;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import com.mysema.codegen.model.SimpleType;
import com.pallasathenagroup.querydsl.range.RangePath;
import com.querydsl.apt.Extension;
import com.querydsl.codegen.AbstractModule;
import com.querydsl.codegen.CodegenModule;
import com.querydsl.codegen.TypeMappings;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

public class RangeSupport implements Extension {

    private static void registerTypes(TypeMappings typeMappings) {
        SimpleType rawRange = new SimpleType(Range.class.getName());
        SimpleType rawPath = new SimpleType(RangePath.class.getName());
        SimpleType localDateType = new SimpleType(LocalDateTime.class.getName());
        typeMappings.register(new SimpleType(rawRange, Collections.singletonList(localDateType)), new SimpleType(rawPath, Collections.singletonList(localDateType)));
    }

    private static void addImports(AbstractModule module, String packageName) {
        @SuppressWarnings("unchecked")
        Set<String> imports = module.get(Set.class, CodegenModule.IMPORTS);
        if (imports.isEmpty()) {
            imports = ImmutableSet.of(packageName);
        } else {
            Set<String> old = imports;
            imports = Sets.newHashSet();
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
