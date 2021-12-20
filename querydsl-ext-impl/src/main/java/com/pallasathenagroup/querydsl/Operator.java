package com.pallasathenagroup.querydsl;

import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.Type;

import java.util.List;

public class Operator implements SQLFunction {

    private final Type returnType;
    private final String operator;

    public Operator(Type returnType, String operator) {
        this.returnType = returnType;
        this.operator = operator;
    }

    @Override
    public boolean hasArguments() {
        return true;
    }

    @Override
    public boolean hasParenthesesIfNoArguments() {
        return false;
    }

    @Override
    public Type getReturnType(Type firstArgumentType, Mapping mapping) throws QueryException {
        return returnType == null ? firstArgumentType : returnType;
    }

    @Override
    public String render(Type firstArgumentType, List arguments, SessionFactoryImplementor factory) throws QueryException {
        return String.join(operator, arguments);
    }

}
