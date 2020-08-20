package com.pallasathenagroup.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.group.GroupBy;
import com.querydsl.jpa.impl.JPAQuery;
import org.hamcrest.CoreMatchers;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.util.Map;

import static com.pallasathenagroup.querydsl.QMonetaryAmountEntity.monetaryAmountEntity;
import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;

public class MonetaryAmountPathTest extends BaseCoreFunctionalTestCase {

    @Override
    protected Class<?>[] getAnnotatedClasses() {
        return new Class<?>[] { MonetaryAmountEntity.class };
    }

    @Override
    protected boolean isCleanupTestDataRequired() {
        return true;
    }

    @Before
    public void setUp() {
        doInJPA(this::sessionFactory, entityManager -> {
            CurrencyUnit eur = Monetary.getCurrency("EUR");
            MonetaryAmount monetaryAmount = Monetary.getDefaultAmountFactory().setCurrency(eur).setNumber(200).create();

            MonetaryAmountEntity moneyEntity = new MonetaryAmountEntity();
            moneyEntity.setId(1L);
            moneyEntity.setMonetaryAmount(monetaryAmount);
            moneyEntity.setSummableValue(new BigDecimal("5.00"));
            entityManager.persist(moneyEntity);
        });
    }

    @Test
    public void testArrayPaths() {
        doInJPA(this::sessionFactory, entityManager -> {
            Map<CurrencyUnit, BigDecimal> result = new JPAQuery<PeriodEntity>(entityManager, ExtendedHQLTemplates.DEFAULT)
                    .from(monetaryAmountEntity)
                    .groupBy(monetaryAmountEntity.monetaryAmount.currencyUnit)
                    .transform(GroupBy.groupBy(monetaryAmountEntity.monetaryAmount.currencyUnit).as(monetaryAmountEntity.monetaryAmount.amount.sum()));

            Assert.assertThat(result, CoreMatchers.is(CoreMatchers.notNullValue()));
        });
    }


    @Test
    public void testMoneyProjection() {

        doInJPA(this::sessionFactory, entityManager -> {
            QueryResults<MonetaryAmount> result = new JPAQuery<PeriodEntity>(entityManager, ExtendedHQLTemplates.DEFAULT)
                    .from(monetaryAmountEntity)
                    .groupBy(monetaryAmountEntity.monetaryAmount.currencyUnit)
                    .select(MoneyProjections.money(monetaryAmountEntity.monetaryAmount.amount.sum(), monetaryAmountEntity.monetaryAmount.currencyUnit))
                    .fetchResults();

            Assert.assertThat(result, CoreMatchers.is(CoreMatchers.notNullValue()));
        });
    }


    @Test
    public void testMoneyComponentExpressions() {

        doInJPA(this::sessionFactory, entityManager -> {
            MonetaryAmount result = new JPAQuery<PeriodEntity>(entityManager, ExtendedHQLTemplates.DEFAULT)
                    .from(monetaryAmountEntity)
                    .where(monetaryAmountEntity.monetaryAmount.amount.gt(new BigDecimal("100")))
                    .where(monetaryAmountEntity.monetaryAmount.currencyUnit.eq(Monetary.getCurrency("EUR")))
                    .select(monetaryAmountEntity.monetaryAmount)
                    .fetchOne();

            Assert.assertThat(result, CoreMatchers.is(CoreMatchers.notNullValue()));
        });
    }

    @Test
    public void testMoneyParameter() {
        doInJPA(this::sessionFactory, entityManager -> {
            CurrencyUnit eur = Monetary.getCurrency("EUR");
            MonetaryAmount monetaryAmount = Monetary.getDefaultAmountFactory().setCurrency(eur).setNumber(200).create();

            MonetaryAmount result = new JPAQuery<PeriodEntity>(entityManager, ExtendedHQLTemplates.DEFAULT)
                    .from(monetaryAmountEntity)
                    .where(monetaryAmountEntity.monetaryAmount.eq(monetaryAmount))
                    .select(monetaryAmountEntity.monetaryAmount)
                    .fetchOne();

            Assert.assertEquals(monetaryAmount, result);
        });
    }


}
