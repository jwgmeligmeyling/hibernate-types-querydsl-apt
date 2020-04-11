package com.pallasathenagroup.querydsl;

import com.vladmihalcea.hibernate.type.interval.PostgreSQLIntervalType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Duration;

@Entity
@TypeDef(name = "duration", typeClass = PostgreSQLIntervalType.class, defaultForType = Duration.class)
public class DurationEntity {

    @Id
    Long id;

    @Type(type = "duration")
    @Column(columnDefinition = "interval")
    Duration duration;
}
