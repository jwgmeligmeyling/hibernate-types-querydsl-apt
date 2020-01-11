package com.pallasathenagroup.querydsl;

import com.querydsl.jpa.HQLTemplates;

public class ExtendedHQLTemplates extends HQLTemplates {

    public static final ExtendedHQLTemplates DEFAULT = new ExtendedHQLTemplates();

    public ExtendedHQLTemplates() {
        add(RangeOps.INTERSECTS, "(INTERSECTS({0}, {1})) IS TRUE");
    }

}
