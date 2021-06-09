package com.pallasathenagroup.querydsl;

import com.google.common.collect.Range;
import com.querydsl.codegen.utils.model.SimpleType;
import com.pallasathenagroup.querydsl.range.RangePath;
import com.querydsl.codegen.Extension;
import com.querydsl.codegen.AbstractModule;
import com.querydsl.codegen.CodegenModule;
import com.querydsl.codegen.TypeMappings;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class RangeSupport implements Extension {

    private static void registerTypes(TypeMappings typeMappings) {
        SimpleType rawRange = new SimpleType(Range.class.getName());
        SimpleType rawPath = new SimpleType(RangePath.class.getName());

        SimpleType localDateTimeType = new SimpleType(LocalDateTime.class.getName());
        typeMappings.register(new SimpleType(rawRange, Collections.singletonList(localDateTimeType)), new SimpleType(rawPath, Collections.singletonList(localDateTimeType)));

        SimpleType localDateType = new SimpleType(LocalDate.class.getName());
        typeMappings.register(new SimpleType(rawRange, Collections.singletonList(localDateType)), new SimpleType(rawPath, Collections.singletonList(localDateType)));

        SimpleType zonedDateTime = new SimpleType(ZonedDateTime.class.getName());
        typeMappings.register(new SimpleType(rawRange, Collections.singletonList(zonedDateTime)), new SimpleType(rawPath, Collections.singletonList(zonedDateTime)));

        SimpleType bigDecimalType = new SimpleType(BigDecimal.class.getName());
        typeMappings.register(new SimpleType(rawRange, Collections.singletonList(bigDecimalType)), new SimpleType(rawPath, Collections.singletonList(bigDecimalType)));

        SimpleType integerType = new SimpleType(Integer.class.getName());
        typeMappings.register(new SimpleType(rawRange, Collections.singletonList(integerType)), new SimpleType(rawPath, Collections.singletonList(integerType)));

        SimpleType longType = new SimpleType(Long.class.getName());
        typeMappings.register(new SimpleType(rawRange, Collections.singletonList(longType)), new SimpleType(rawPath, Collections.singletonList(longType)));
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
