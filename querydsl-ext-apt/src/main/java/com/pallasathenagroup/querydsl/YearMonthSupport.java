package com.pallasathenagroup.querydsl;

import com.querydsl.codegen.utils.model.SimpleType;
import com.pallasathenagroup.querydsl.yearmonth.YearMonthPath;
import com.querydsl.codegen.Extension;
import com.querydsl.codegen.AbstractModule;
import com.querydsl.codegen.TypeMappings;

import java.time.YearMonth;

public class YearMonthSupport implements Extension {

    private static void registerTypes(TypeMappings typeMappings) {
        SimpleType rawType = new SimpleType(YearMonth.class.getName());
        SimpleType rawPath = new SimpleType(YearMonthPath.class.getName());
        typeMappings.register(new SimpleType(rawType), new SimpleType(rawPath));
    }

    public void addSupport(AbstractModule module) {
        registerTypes(module.get(TypeMappings.class));
    }


}
