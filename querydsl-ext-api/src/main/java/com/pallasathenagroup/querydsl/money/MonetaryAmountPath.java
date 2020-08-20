package com.pallasathenagroup.querydsl.money;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.ComparablePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.SimpleExpression;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import java.lang.reflect.AnnotatedElement;
import java.math.BigDecimal;
import java.util.Currency;

public class MonetaryAmountPath extends MonetaryAmountExpression implements Path<MonetaryAmount> {

    public final NumberPath<BigDecimal> amount;
    public final ComparablePath<CurrencyUnit> currencyUnit;

    public MonetaryAmountPath(Path<MonetaryAmount> mixin) {
        super(mixin);
        this.amount = Expressions.numberPath(BigDecimal.class, this, "amount");
        this.currencyUnit = Expressions.comparablePath(CurrencyUnit.class, this, "currencyUnit");
    }

    public MonetaryAmountPath(PathMetadata pathMetadata) {
        this(ExpressionUtils.path(MonetaryAmount.class, pathMetadata));
    }

    @Override
    public PathMetadata getMetadata() {
        return ((Path<MonetaryAmount>) mixin).getMetadata();
    }

    @Override
    public Path<?> getRoot() {
        return ((Path<MonetaryAmount>) mixin).getRoot();
    }

    @Override
    public AnnotatedElement getAnnotatedElement() {
        return ((Path<MonetaryAmount>) mixin).getAnnotatedElement();
    }

}
