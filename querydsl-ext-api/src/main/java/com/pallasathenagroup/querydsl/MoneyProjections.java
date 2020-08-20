package com.pallasathenagroup.querydsl;

import com.pallasathenagroup.querydsl.money.QMonetaryAmount;
import com.querydsl.core.types.Expression;

import javax.money.CurrencyUnit;

public class MoneyProjections {

    public static QMonetaryAmount money(Expression<? extends Number> amount, Expression<? extends CurrencyUnit> currencyUnit) {
        return new QMonetaryAmount(amount, currencyUnit);
    }

}
