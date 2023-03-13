package com.pallasathenagroup.querydsl;

import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.mapping.BasicValuedMapping;
import org.hibernate.query.ReturnableType;
import org.hibernate.query.sqm.function.AbstractSqmSelfRenderingFunctionDescriptor;
import org.hibernate.query.sqm.function.FunctionKind;
import org.hibernate.query.sqm.produce.function.ArgumentsValidator;
import org.hibernate.query.sqm.produce.function.FunctionArgumentTypeResolver;
import org.hibernate.query.sqm.produce.function.FunctionReturnTypeResolver;
import org.hibernate.query.sqm.tree.SqmTypedNode;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.sql.ast.tree.predicate.Predicate;
import org.hibernate.sql.ast.tree.select.SortSpecification;
import org.hibernate.type.BasicTypeReference;
import org.hibernate.type.Type;
import org.hibernate.type.spi.TypeConfiguration;

import java.util.List;
import java.util.function.Supplier;

public class Operator extends AbstractSqmSelfRenderingFunctionDescriptor {


    public Operator(BasicTypeReference<?> type, String operator) {
        super(
                name,
                FunctionKind.NORMAL,
                null,
                new FunctionReturnTypeResolver() {
                    @Override
                    public ReturnableType<?> resolveFunctionReturnType(
                            ReturnableType<?> impliedType,
                            List<? extends SqmTypedNode<?>> arguments,
                            TypeConfiguration typeConfiguration) {
                        return type == null ? null : typeConfiguration.getBasicTypeRegistry().resolve( type );
                    }

                    @Override
                    public BasicValuedMapping resolveFunctionReturnType(Supplier<BasicValuedMapping> impliedTypeAccess, List<? extends SqlAstNode> arguments) {
                        return type == null || impliedTypeAccess == null ? null : impliedTypeAccess.get();
                    }
                },
                null
        );
    }

    @Override
    public void render(SqlAppender sqlAppender, List<? extends SqlAstNode> sqlAstArguments, SqlAstTranslator<?> walker) {
        render(sqlAppender, sqlAstArguments, null, walker);
    }

    @Override
    public void render(SqlAppender sqlAppender, List<? extends SqlAstNode> sqlAstArguments, Predicate filter, SqlAstTranslator<?> walker) {
        render(sqlAppender, sqlAstArguments, filter, null, walker);
    }

    @Override
    public void render(SqlAppender sqlAppender, List<? extends SqlAstNode> sqlAstArguments, Predicate filter, List<SortSpecification> withinGroup, SqlAstTranslator<?> walker) {
        render(sqlAppender, sqlAstArguments, filter, null, null, walker);
    }

    @Override
    public void render(SqlAppender sqlAppender, List<? extends SqlAstNode> sqlAstArguments, Predicate filter, Boolean respectNulls, Boolean fromFirst, SqlAstTranslator<?> walker) {
        super.render(sqlAppender, sqlAstArguments, filter, respectNulls, fromFirst, walker);
    }

}
