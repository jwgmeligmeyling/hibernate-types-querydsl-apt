package com.pallasathenagroup.querydsl;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLHStoreType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Map;

@Entity
@TypeDef(name = "hstore", typeClass = PostgreSQLHStoreType.class)
public class HstoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Type(type = "hstore")
    @Column(columnDefinition = "hstore")
    Map<String, String> hstore;

}
