package com.pallasathenagroup.querydsl;

import com.mysema.codegen.model.SimpleType;
import com.pallasathenagroup.querydsl.duration.DurationPath;
import com.querydsl.apt.Extension;
import com.querydsl.codegen.AbstractModule;
import com.querydsl.codegen.TypeMappings;

import java.time.Duration;

public class DurationSupport implements Extension {

    private static void registerTypes(TypeMappings typeMappings) {
        SimpleType rawType = new SimpleType(Duration.class.getName());
        SimpleType rawPath = new SimpleType(DurationPath.class.getName());
        typeMappings.register(new SimpleType(rawType), new SimpleType(rawPath));
    }

    public void addSupport(AbstractModule module) {
        registerTypes(module.get(TypeMappings.class));
    }


}
