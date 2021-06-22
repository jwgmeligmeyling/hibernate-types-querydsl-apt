package com.pallasathenagroup.querydsl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.pallasathenagroup.querydsl.json.JsonExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.pallasathenagroup.querydsl.QJsonNodeEntity.jsonNodeEntity;
import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;
import static org.junit.Assert.assertEquals;

public class JsonNodePathTest extends BaseCoreFunctionalTestCase {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private JsonNodeEntity entity;

    @Override
    protected Class<?>[] getAnnotatedClasses() {
        return new Class<?>[] { JsonNodeEntity.class };
    }

    @Before
    public void setUp() {
        doInJPA(this::sessionFactory, entityManager -> {
            entity = new JsonNodeEntity();
            entity.jsonNode = objectMapper.valueToTree(ImmutableMap.of("a", 123));
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
            String result = new JPAQuery<RangeEntity>(entityManager, ExtendedHQLTemplates.DEFAULT)
                    .from(jsonNodeEntity)
                    .select(
                            jsonNodeEntity.jsonNode.get("a").asText()
                    )
                    .fetchOne();

            assertEquals("123", result);
        });
    }

    @Test
    public void getFieldAsNode() {
        doInJPA(this::sessionFactory, entityManager -> {
            JsonNode result = new JPAQuery<RangeEntity>(entityManager, ExtendedHQLTemplates.DEFAULT)
                    .from(jsonNodeEntity)
                    .select(
                            jsonNodeEntity.jsonNode.get("a")
                    )
                    .fetchOne();

            assertEquals(objectMapper.valueToTree(123), result);
        });
    }

    @Test
    public void getKeys() {
        doInJPA(this::sessionFactory, entityManager -> {
            List<String> result = new JPAQuery<RangeEntity>(entityManager, ExtendedHQLTemplates.DEFAULT)
                    .from(jsonNodeEntity)
                    .select(
                            jsonNodeEntity.jsonNode.keys()
                    )
                    .fetch();

            assertEquals(ImmutableList.of("a"), result);
        });
    }


    @Test
    public void buildJsonObject() {
        doInJPA(this::sessionFactory, entityManager -> {
            ObjectNode result = new JPAQuery<RangeEntity>(entityManager, ExtendedHQLTemplates.DEFAULT)
                    .from(jsonNodeEntity)
                    .select(
                            JsonExpressions.buildJsonObject(jsonNodeEntity.id.as("id"), jsonNodeEntity.id.as("id2"))
                    )
                    .fetchOne();

            try {
                assertEquals(ImmutableMap.of("id", entity.id.intValue(), "id2", entity.id.intValue()), objectMapper.treeToValue(result, Map.class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void buildJsonObject2() {
        doInJPA(this::sessionFactory, entityManager -> {
            ObjectNode result = new JPAQuery<RangeEntity>(entityManager, ExtendedHQLTemplates.DEFAULT)
                    .from(jsonNodeEntity)
                    .select(
                            JsonExpressions.buildJsonObject(ImmutableMap.of("id", jsonNodeEntity.id, "id2", jsonNodeEntity.id))
                    )
                    .fetchOne();

            try {
                assertEquals(ImmutableMap.of("id", entity.id.intValue(), "id2", entity.id.intValue()), objectMapper.treeToValue(result, Map.class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
