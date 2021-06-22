package com.pallasathenagroup.querydsl.json;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.querydsl.core.types.Constant;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.FactoryExpression;
import com.querydsl.core.types.Operation;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.ParamExpression;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.TemplateExpression;
import com.querydsl.core.types.Visitor;
import com.querydsl.core.types.dsl.Expressions;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class JsonExpressions {

    public static <T> JsonExpression<T> asJsonExpression(Expression<T> expression) {
        return new JsonExpression<>(expression);
    }

    public static JsonExpression<ObjectNode> buildJsonObject(Expression<?>... expressions) {
        Expression<?>[] arguments = new Expression<?>[expressions.length * 2];

        for (int i = 0; i < expressions.length; i++) {
            Expression<?> expression  = expressions[i];
            if (expression instanceof Operation) {
                Operation<?> operation = (Operation<?>) expression;
                if (operation.getOperator().equals(Ops.ALIAS)) {
                    arguments[i*2] = operation.getArg(1).accept(AliasLiteralVisitor.INSTANCE, null);
                    arguments[i*2+1] = operation.getArg(0);
                    continue;
                }
            }
            throw new IllegalArgumentException("All expressions need to be aliased");
        }

        return new JsonOperation<>(Expressions.operation(ObjectNode.class, JsonOps.JSON_BUILD_OBJECT, Expressions.list(arguments)));
    }


    public static JsonExpression<ObjectNode> buildJsonObject(Map<String, Expression<?>> expressions) {
        Expression<?>[] arguments = new Expression<?>[expressions.size() * 2];
        int i = 0;

        for (Map.Entry<String, Expression<?>> entry : expressions.entrySet()) {
            arguments[i++] = Expressions.constant(entry.getKey());
            arguments[i++] = entry.getValue();
        }

        return new JsonOperation<>(Expressions.operation(ObjectNode.class, JsonOps.JSON_BUILD_OBJECT, Expressions.list(arguments)));
    }

    public static JsonExpression<ArrayNode> buildJsonArray(Expression<?> expressions) {
        return new JsonOperation<>(Expressions.operation(ArrayNode.class, JsonOps.JSON_BUILD_ARRAY, Expressions.list(expressions)));
    }

    private static class AliasLiteralVisitor implements Visitor<Expression<?>, Void> {

        public static final AliasLiteralVisitor INSTANCE = new AliasLiteralVisitor();

        @Override
        public Expression<?> visit(Constant<?> constant, @Nullable Void unused) {
            return constant;
        }

        @Override
        public Expression<?> visit(FactoryExpression<?> factoryExpression, @Nullable Void unused) {
            return factoryExpression;
        }

        @Override
        public Expression<?> visit(Operation<?> operation, @Nullable Void unused) {
            return operation;
        }

        @Override
        public Expression<?> visit(ParamExpression<?> paramExpression, @Nullable Void unused) {
            return paramExpression;
        }

        @Override
        public Expression<?> visit(Path<?> path, @Nullable Void unused) {
            return Expressions.constant(path.getMetadata().getName());
        }

        @Override
        public Expression<?> visit(SubQueryExpression<?> subQueryExpression, @Nullable Void unused) {
            return subQueryExpression;
        }

        @Override
        public Expression<?> visit(TemplateExpression<?> templateExpression, @Nullable Void unused) {
            return templateExpression;
        }
    }
}
