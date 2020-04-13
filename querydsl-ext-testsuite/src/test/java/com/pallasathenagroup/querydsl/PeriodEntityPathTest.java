package com.pallasathenagroup.querydsl;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import static com.pallasathenagroup.querydsl.QPeriodEntity.periodEntity;
import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;

public class PeriodEntityPathTest extends BaseCoreFunctionalTestCase {

    @Override
    protected Class<?>[] getAnnotatedClasses() {
        return new Class<?>[] { PeriodEntity.class };
    }

    @Before
    public void setUp() {
        doInJPA(this::sessionFactory, entityManager -> {
            PeriodEntity periodEntity = new PeriodEntity();
            periodEntity.id = 1L;
            periodEntity.period = Period.ofDays(1);
            entityManager.persist(periodEntity);
        });
    }

    @Test
    public void periodTest() {
        doInJPA(this::sessionFactory, entityManager -> {
            LocalDate now = LocalDate.now();
            List<PeriodEntity> fetch = new JPAQuery<PeriodEntity>(entityManager, ExtendedHQLTemplates.DEFAULT)
                .from(periodEntity).select(periodEntity)
                .where(periodEntity.period.ne(Period.ofDays(2)))
                .where(periodEntity.period.multiply(6).divide(2.0).isNotNull())
                .where(HibernateTypesExpressions.period(Period.ofWeeks(3)).eq(Period.ofWeeks(3)))
                .where(HibernateTypesExpressions.periodBetween(Expressions.asDate(now), now.minusDays(7L)).eq(Period.ofWeeks(1)))
                .fetch();

            Assert.assertFalse(fetch.isEmpty());
        });
    }

}
