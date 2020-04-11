package com.pallasathenagroup.querydsl;

import com.vladmihalcea.hibernate.type.interval.PostgreSQLPeriodType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Period;

@Entity
@TypeDef(name = "period", typeClass = PostgreSQLPeriodType.class, defaultForType = Period.class)
public class PeriodEntity {

    @Id
    Long id;

    @Type(type = "period")
    @Column(columnDefinition = "interval")
    Period period;
}
