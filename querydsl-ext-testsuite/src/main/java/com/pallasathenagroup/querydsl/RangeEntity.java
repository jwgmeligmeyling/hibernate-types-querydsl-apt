package com.pallasathenagroup.querydsl;

import com.google.common.collect.Range;
import com.vladmihalcea.hibernate.type.range.guava.PostgreSQLGuavaRangeType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@TypeDef(name = "range", typeClass = ExtendedPostgreSQLGuavaRangeType.class, defaultForType = Range.class)
public class RangeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Type(type = "range")
    @Column(columnDefinition = "tsrange")
    Range<LocalDateTime> localDateTimeRange;

}
