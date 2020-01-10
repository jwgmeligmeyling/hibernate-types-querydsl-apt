package com.pallasathenagroup.querydsl;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class RangeEntityPathTest {

    @Test
    public void rangeEntityPathTest() {
        QRangeEntity.rangeEntity.localDateTimeRange.intersects(Range.closedOpen(LocalDateTime.MIN, LocalDateTime.MAX));
    }

}
