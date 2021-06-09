package com.pallasathenagroup.querydsl;

import com.querydsl.codegen.utils.model.SimpleType;
import com.pallasathenagroup.querydsl.period.PeriodPath;
import com.querydsl.codegen.Extension;
import com.querydsl.codegen.AbstractModule;
import com.querydsl.codegen.TypeMappings;

import java.time.Period;

public class PeriodSupport implements Extension {

    private static void registerTypes(TypeMappings typeMappings) {
        SimpleType rawType = new SimpleType(Period.class.getName());
        SimpleType rawPath = new SimpleType(PeriodPath.class.getName());
        typeMappings.register(new SimpleType(rawType), new SimpleType(rawPath));
    }

    public void addSupport(AbstractModule module) {
        registerTypes(module.get(TypeMappings.class));
    }


}
