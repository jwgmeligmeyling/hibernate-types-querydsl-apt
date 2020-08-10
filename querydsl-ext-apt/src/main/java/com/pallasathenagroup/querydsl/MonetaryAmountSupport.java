package com.pallasathenagroup.querydsl;

import com.mysema.codegen.model.SimpleType;
import com.pallasathenagroup.querydsl.money.MonetaryAmountPath;
import com.pallasathenagroup.querydsl.period.PeriodPath;
import com.querydsl.apt.Extension;
import com.querydsl.codegen.AbstractModule;
import com.querydsl.codegen.TypeMappings;

import javax.money.MonetaryAmount;
import java.time.Period;

public class MonetaryAmountSupport implements Extension {

    private static void registerTypes(TypeMappings typeMappings) {
        SimpleType rawType = new SimpleType(MonetaryAmount.class.getName());
        SimpleType rawPath = new SimpleType(MonetaryAmountPath.class.getName());
        typeMappings.register(new SimpleType(rawType), new SimpleType(rawPath));
    }

    public void addSupport(AbstractModule module) {
        registerTypes(module.get(TypeMappings.class));
    }


}
