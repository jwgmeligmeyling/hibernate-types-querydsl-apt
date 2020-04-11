package com.pallasathenagroup.querydsl;

import com.pallasathenagroup.querydsl.array.ArrayOps;
import com.pallasathenagroup.querydsl.range.RangeOps;
import com.querydsl.jpa.HQLTemplates;

public class ExtendedHQLTemplates extends HQLTemplates {

    public static final ExtendedHQLTemplates DEFAULT = new ExtendedHQLTemplates();

    public ExtendedHQLTemplates() {
        add(RangeOps.OVERLAPS, "(OVERLAPS({0}, {1})) IS TRUE");
        add(RangeOps.CONTAINS, "(CONTAINS({0}, {1})) IS TRUE");
        add(RangeOps.IS_CONTAINED_BY, "(IS_CONTAINED_BY({0}, {1})) IS TRUE");
        add(RangeOps.STRICTLY_LEFT_OF, "(STRICTLY_LEFT_OF({0}, {1})) IS TRUE");
        add(RangeOps.STRICTLY_RIGHT_OF, "(STRICTLY_RIGHT_OF({0}, {1})) IS TRUE");
        add(RangeOps.ADJACENT_TO, "(ADJACENT_TO({0}, {1})) IS TRUE");
        add(RangeOps.UNION, "UNION({0}, {1})");
        add(RangeOps.INTERSECTION, "INTERSECTION({0}, {1})");
        add(RangeOps.DIFFERENCE, "DIFFERENCE({0}, {1})");
        add(RangeOps.LOWER_BOUND, "LOWER_BOUND({0})");
        add(RangeOps.UPPER_BOUND, "UPPER_BOUND({0})");

        add(ArrayOps.OVERLAPS, "(OVERLAPS({0}, {1})) IS TRUE");
        add(ArrayOps.CONTAINS, "(CONTAINS({0}, {1})) IS TRUE");
        add(ArrayOps.IS_CONTAINED_BY, "(IS_CONTAINED_BY({0}, {1})) IS TRUE");
        add(ArrayOps.CONCAT, "ARRAY_CONCAT({0}, {1})");
        add(ArrayOps.APPEND, "ARRAY_APPEND({0}, {1})");
        add(ArrayOps.PREPEND, "ARRAY_PREPEND({0}, {1})");
        add(ArrayOps.NDIMS, "ARRAY_NDIMS({0})");
        add(ArrayOps.DIMS, "ARRAY_DIMS({0})");
        add(ArrayOps.FILL, "ARRAY_FILL({0}, {1})");
        add(ArrayOps.LENGTH, "ARRAY_LENGTH({0})");
        add(ArrayOps.TOSTRING, "ARRAY_TOSTRING({0})");
        add(ArrayOps.UNNEST, "ARRAY_UNNEST({0})");
        add(ArrayOps.ELEMENT_AT, "ARRAY_ELEMENT_AT({0}, {1})");
    }

}
