package com.pallasathenagroup.querydsl;

import com.pallasathenagroup.querydsl.array.ArrayOps;
import com.pallasathenagroup.querydsl.duration.DurationOps;
import com.pallasathenagroup.querydsl.period.PeriodOps;
import com.pallasathenagroup.querydsl.range.RangeOps;
import com.querydsl.jpa.HQLTemplates;

public class ExtendedHQLTemplates extends HQLTemplates {

    public static final ExtendedHQLTemplates DEFAULT = new ExtendedHQLTemplates();

    public ExtendedHQLTemplates() {
        registerTemplates(this);
    }

    public static void registerTemplates(ExtendedHQLTemplates templates) {
        templates.add(RangeOps.OVERLAPS, "RANGE_OVERLAPS({0}, {1}) = TRUE");
        templates.add(RangeOps.CONTAINS, "RANGE_CONTAINS({0}, {1}) = TRUE");
        templates.add(RangeOps.IS_CONTAINED_BY, "RANGE_IS_CONTAINED_BY({0}, {1}) = TRUE");
        templates.add(RangeOps.STRICTLY_LEFT_OF, "RANGE_STRICTLY_LEFT_OF({0}, {1}) = TRUE");
        templates.add(RangeOps.STRICTLY_RIGHT_OF, "RANGE_STRICTLY_RIGHT_OF({0}, {1}) = TRUE");
        templates.add(RangeOps.ADJACENT_TO, "RANGE_ADJACENT_TO({0}, {1}) = TRUE");
        templates.add(RangeOps.UNION, "RANGE_UNION({0}, {1})");
        templates.add(RangeOps.INTERSECTION, "RANGE_INTERSECTION({0}, {1})");
        templates.add(RangeOps.DIFFERENCE, "RANGE_DIFFERENCE({0}, {1})");
        templates.add(RangeOps.LOWER_BOUND, "RANGE_LOWER_BOUND({0})");
        templates.add(RangeOps.UPPER_BOUND, "RANGE_UPPER_BOUND({0})");

        templates.add(ArrayOps.OVERLAPS, "ARRAY_OVERLAPS({0}, {1}) = TRUE");
        templates.add(ArrayOps.CONTAINS, "ARRAY_CONTAINS({0}, {1}) = TRUE");
        templates.add(ArrayOps.IS_CONTAINED_BY, "ARRAY_IS_CONTAINED_BY({0}, {1}) = TRUE");
        templates.add(ArrayOps.CONCAT, "ARRAY_CONCAT({0}, {1})");
        templates.add(ArrayOps.APPEND, "ARRAY_APPEND({0}, {1})");
        templates.add(ArrayOps.PREPEND, "ARRAY_PREPEND({0}, {1})");
        templates.add(ArrayOps.NDIMS, "ARRAY_NDIMS({0})");
        templates.add(ArrayOps.DIMS, "ARRAY_DIMS({0})");
        templates.add(ArrayOps.FILL, "ARRAY_FILL({0}, {1})");
        templates.add(ArrayOps.LENGTH, "ARRAY_LENGTH({0})");
        templates.add(ArrayOps.TOSTRING, "ARRAY_TOSTRING({0})");
        templates.add(ArrayOps.UNNEST, "ARRAY_UNNEST({0})");
        templates.add(ArrayOps.ELEMENT_AT, "ARRAY_ELEMENT_AT({0}, {1})");

        templates.add(DurationOps.BETWEEN, "DURATION_BETWEEN({0}, {1})");
        templates.add(DurationOps.ADD, "DURATION_extendedHQLTemplates.add({0}, {1})");
        templates.add(DurationOps.SUBTRACT, "DURATION_SUBTRACT({0}, {1})");
        templates.add(DurationOps.MULTIPLY, "DURATION_MULTIPLY({0}, {1})");
        templates.add(DurationOps.DIVIDE, "DURATION_DIVIDE({0}, {1})");
        templates.add(DurationOps.AVG, "DURATION_AVG({0})");
        templates.add(DurationOps.MAX, "DURATION_MAX({0})");
        templates.add(DurationOps.MIN, "DURATION_MIN({0})");
        templates.add(DurationOps.SUM, "DURATION_SUM({0})");

        templates.add(PeriodOps.BETWEEN, "PERIOD_BETWEEN({0}, {1})");
        templates.add(PeriodOps.ADD, "PERIOD_extendedHQLTemplates.add({0}, {1})");
        templates.add(PeriodOps.SUBTRACT, "PERIOD_SUBTRACT({0}, {1})");
        templates.add(PeriodOps.MULTIPLY, "PERIOD_MULTIPLY({0}, {1})");
        templates.add(PeriodOps.DIVIDE, "PERIOD_DIVIDE({0}, {1})");
        templates.add(PeriodOps.AVG, "PERIOD_AVG({0})");
        templates.add(PeriodOps.MAX, "PERIOD_MAX({0})");
        templates.add(PeriodOps.MIN, "PERIOD_MIN({0})");
        templates.add(PeriodOps.SUM, "PERIOD_SUM({0})");
    }

}
