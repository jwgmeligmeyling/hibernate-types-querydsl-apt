package com.pallasathenagroup.querydsl;

import com.google.common.collect.ImmutableMap;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static com.pallasathenagroup.querydsl.QHstoreEntity.hstoreEntity;
import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;
import static org.junit.Assert.assertEquals;

@Ignore("No Hstore extension enabled on CI")
public class HstorePathTest extends BaseCoreFunctionalTestCase {

    @Override
    protected Class<?>[] getAnnotatedClasses() {
        return new Class<?>[] { HstoreEntity.class };
    }

    @Before
    public void setUp() {
        doInJPA(this::sessionFactory, entityManager -> {
            HstoreEntity entity = new HstoreEntity();
            entity.hstore = ImmutableMap.of("a", "b");
            entityManager.persist(entity);
        });
    }

    @Override
    protected boolean isCleanupTestDataRequired() {
        return true;
    }

    @Test
    public void getFieldAsText() {
        doInJPA(this::sessionFactory, entityManager -> {
            String result = new JPAQuery<HstoreEntity>(entityManager, ExtendedHQLTemplates.DEFAULT)
                    .from(hstoreEntity)
                    .select(
                            HibernateTypesExpressions.asHstore(hstoreEntity.hstore).get("a")
                    )
                    .fetchOne();

            assertEquals("b", result);
        });
    }

}
