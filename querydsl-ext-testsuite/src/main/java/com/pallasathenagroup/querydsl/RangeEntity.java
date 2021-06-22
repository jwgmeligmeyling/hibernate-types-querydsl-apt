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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@TypeDef(name = "range", typeClass = PostgreSQLGuavaRangeType.class, defaultForType = Range.class)
public class RangeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Type(type = "range")
    @Column(columnDefinition = "tsrange")
    Range<LocalDateTime> localDateTimeRange;

    @Column(name = "r_int", columnDefinition = "int4Range")
    private Range<Integer> rangeInt;

    @Column(name = "r_int_empty", columnDefinition = "int4Range")
    private Range<Integer> rangeIntEmpty;

    @Column(name = "r_int_infinity", columnDefinition = "int4Range")
    private Range<Integer> rangeIntInfinity;

    @Column(name = "r_long", columnDefinition = "int8range")
    private Range<Long> rangeLong;

    @Column(name = "r_numeric", columnDefinition = "numrange")
    private Range<BigDecimal> rangeBigDecimal;

    @Column(name = "r_ts", columnDefinition = "tsrange")
    private Range<LocalDateTime> rangeLocalDateTime;

    @Column(name = "r_ts_tz", columnDefinition = "tstzrange")
    private Range<ZonedDateTime> rangeZonedDateTime;

    @Column(name = "r_ts_tz_infinity", columnDefinition = "tstzrange")
    private Range<ZonedDateTime> rangeZonedDateTimeInfinity;

    @Column(name = "r_date", columnDefinition = "daterange")
    private Range<LocalDate> rangeLocalDate;

}
