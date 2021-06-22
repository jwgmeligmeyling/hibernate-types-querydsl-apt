package com.pallasathenagroup.querydsl;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@TypeDef(name = "json", typeClass = JsonBinaryType.class, defaultForType = JsonNode.class)
public class JsonNodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    JsonNode jsonNode;

}
