package com.pallasathenagroup.querydsl;

import com.google.common.collect.Range;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.pallasathenagroup.querydsl.QDurationEntity.durationEntity;
import static com.pallasathenagroup.querydsl.QRangeEntity.rangeEntity;
import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;

public class DurationEntityPathTest extends BaseCoreFunctionalTestCase {

    @Override
    protected Class<?>[] getAnnotatedClasses() {
        return new Class<?>[] { DurationEntity.class };
    }

    @Before
    public void setUp() {
        doInJPA(this::sessionFactory, entityManager -> {
            DurationEntity durationEntity = new DurationEntity();
            durationEntity.id = 1L;
            durationEntity.duration = Duration.ofHours(1);
            entityManager.persist(durationEntity);
        });
    }

    @Test
    public void durationTest() {
        doInJPA(this::sessionFactory, entityManager -> {
            LocalDateTime now = LocalDateTime.now();
            List<DurationEntity> fetch = new JPAQuery<RangeEntity>(entityManager, ExtendedHQLTemplates.DEFAULT)
                .from(durationEntity).select(durationEntity)
                .where(durationEntity.duration.ne(Duration.ofHours(2)))
                .where(durationEntity.duration.multiply(6).divide(2.0).gt(Duration.ofHours(1)))
                .where(HibernateTypesExpressions.duration(Duration.ofMinutes(3)).eq(Duration.ofMinutes(3)))
                .where(HibernateTypesExpressions.durationBetween(Expressions.asDateTime(now), now.minusHours(1L)).eq(Duration.ofHours(1)))
                .orderBy(durationEntity.duration.asc())
                .fetch();

            Assert.assertFalse(fetch.isEmpty());
        });
    }

}
