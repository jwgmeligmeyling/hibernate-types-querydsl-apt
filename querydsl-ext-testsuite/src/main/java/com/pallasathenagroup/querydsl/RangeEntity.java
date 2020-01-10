package com.pallasathenagroup.querydsl;

import com.google.common.collect.Range;
import com.vladmihalcea.hibernate.type.range.PostgreSQLRangeType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@TypeDef(name = "range", typeClass = PostgreSQLRangeType.class, defaultForType = Range.class)
public class RangeEntity {

    @Id
    Long id;

    @Type(type = "range")
    Range<LocalDateTime> localDateTimeRange;

}
