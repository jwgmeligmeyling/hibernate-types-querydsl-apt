package com.pallasathenagroup.querydsl;

import com.google.common.collect.Range;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.pallasathenagroup.querydsl.QRangeEntity.rangeEntity;
import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;

public class RangeEntityPathTest extends BaseCoreFunctionalTestCase {

    @Override
    protected Class<?>[] getAnnotatedClasses() {
        return new Class<?>[] { RangeEntity.class };
    }

    @Before
    public void setUp() {
        doInJPA(this::sessionFactory, entityManager -> {
            RangeEntity rangeEntity = new RangeEntity();
            rangeEntity.localDateTimeRange = Range.closedOpen(LocalDate.of(2020, 1, 1).atStartOfDay(), LocalDate.of(2020, 1, 2).atStartOfDay());
            entityManager.persist(rangeEntity);
        });
    }

    @Test
    public void rangeEntityPathTest() {
        doInJPA(this::sessionFactory, entityManager -> {
            List<RangeEntity> fetch = new JPAQuery<RangeEntity>(entityManager, ExtendedHQLTemplates.DEFAULT).from(rangeEntity).select(rangeEntity)
                .where(rangeEntity.localDateTimeRange.overlaps(Range.closedOpen(LocalDate.of(2019, 1, 1).atStartOfDay(), LocalDate.of(2021, 1, 1).atStartOfDay())))
                .fetch();

            Assert.assertFalse(fetch.isEmpty());
        });
    }

    @Test
    public void selectLower() {
        doInJPA(this::sessionFactory, entityManager -> {
            List<LocalDateTime> fetch = new JPAQuery<RangeEntity>(entityManager, ExtendedHQLTemplates.DEFAULT)
                    .from(rangeEntity).select(rangeEntity.localDateTimeRange.lower())
                    .fetch();

            Assert.assertFalse(fetch.isEmpty());
        });
    }

    @Test
    public void rangeEntityPathContainedByTest() {
        doInJPA(this::sessionFactory, entityManager -> {
            List<RangeEntity> fetch = new JPAQuery<RangeEntity>(entityManager, ExtendedHQLTemplates.DEFAULT).from(rangeEntity).select(rangeEntity)
                .where(rangeEntity.localDateTimeRange.isContainedBy(Range.closedOpen(LocalDate.of(2019, 1, 1).atStartOfDay(), LocalDate.of(2021, 1, 1).atStartOfDay())))
                .fetch();

            Assert.assertFalse(fetch.isEmpty());
        });
    }

}
