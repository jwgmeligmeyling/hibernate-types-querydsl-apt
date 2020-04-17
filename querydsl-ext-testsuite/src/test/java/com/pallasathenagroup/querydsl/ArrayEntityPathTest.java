package com.pallasathenagroup.querydsl;

import com.pallasathenagroup.querydsl.ArrayEntity.SensorState;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.pallasathenagroup.querydsl.HibernateTypesExpressions.createArrayExpression;
import static com.pallasathenagroup.querydsl.QArrayEntity.arrayEntity;
import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ArrayEntityPathTest extends BaseCoreFunctionalTestCase {

    @Override
    protected Class<?>[] getAnnotatedClasses() {
        return new Class<?>[] { ArrayEntity.class };
    }

    @Override
    protected boolean isCleanupTestDataRequired() {
        return true;
    }

    @Before
    public void setUp() {
        doInJPA(this::sessionFactory, entityManager -> {
            Date date1 = Date.from(LocalDate.of(1991, 12, 31).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            Date date2 = Date.from(LocalDate.of(1990, 1, 1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

            ArrayEntity arrayEntity = new ArrayEntity();
            arrayEntity.setId(1L);
            arrayEntity.setSensorIds(new UUID[]{UUID.fromString("c65a3bcb-8b36-46d4-bddb-ae96ad016eb1"), UUID.fromString("72e95717-5294-4c15-aa64-a3631cf9a800")});
            arrayEntity.setSensorNames(new String[]{"Temperature", "Pressure"});
            arrayEntity.setSensorValues(new int[]{12, 756});
            arrayEntity.setSensorLongValues(new long[]{42L, 9223372036854775800L});
            arrayEntity.setSensorDoubleValues(new double[]{0.123, 456.789});
            arrayEntity.setSensorStates(new SensorState[]{SensorState.ONLINE, SensorState.OFFLINE, SensorState.ONLINE, SensorState.UNKNOWN});
            arrayEntity.setDateValues(new Date[]{date1, date2});
            arrayEntity.setTimestampValues(new Date[]{date1, date2});
            entityManager.persist(arrayEntity);
        });
    }

    @Test
    public void testArrayPaths() {
        doInJPA(this::sessionFactory, entityManager -> {
            List<Tuple> fetch = new JPAQuery<>(entityManager, ExtendedHQLTemplates.DEFAULT)
                    .from(arrayEntity).select(
                            arrayEntity.sensorValues.get(0),
                            arrayEntity.sensorValues.append(5),
                            arrayEntity.sensorValues.prepend(5),
                            arrayEntity.sensorValues.concat(arrayEntity.sensorValues),
                            arrayEntity.sensorValues.contains(12, 756),
                            arrayEntity.sensorValues.contains(123),
                            arrayEntity.sensorValues.isContainedBy(12, 13),
                            arrayEntity.sensorValues.isContainedBy(12, 13, 756),
                            arrayEntity.sensorValues.overlaps(12, 13),
                            arrayEntity.sensorValues.size(),
                            arrayEntity.sensorStates.concat(SensorState.ONLINE, SensorState.UNKNOWN).contains(SensorState.ONLINE),
                            arrayEntity.sensorStates.get(0)
                            )
                    .where(createArrayExpression(1, 2).contains(createArrayExpression(1,2 )))
                    .fetch();

            Tuple tuple = fetch.get(0);
            assertEquals(12, tuple.get(0, Object.class));
            assertArrayEquals(new int[] {12, 756, 5}, tuple.get(1, int[].class));
            assertArrayEquals(new int[] {5, 12, 756}, tuple.get(2, int[].class));
            assertArrayEquals(new int[] {12, 756, 12, 756}, tuple.get(3, int[].class));
            assertEquals(true, tuple.get(4, Object.class));
            assertEquals(false, tuple.get(5, Object.class));
            assertEquals(false, tuple.get(6, Object.class));
            assertEquals(true, tuple.get(7, Object.class));
            assertEquals(true, tuple.get(8, Object.class));
            assertEquals(2, tuple.get(9, Object.class));
            assertEquals(true, tuple.get(10, Object.class));
            assertEquals(SensorState.ONLINE, tuple.get(11, Object.class));
        });
    }


    @Test
    public void testArrayAgg() {
        doInJPA(this::sessionFactory, entityManager -> {
            new JPAQuery<>(entityManager, ExtendedHQLTemplates.DEFAULT)
                    .from(arrayEntity)
                    .select(HibernateTypesExpressions.arrayAgg(arrayEntity.sensorStates.get(0)))
                    .fetch();
        });
    }
}
