package com.pallasathenagroup.querydsl;

import com.google.common.collect.Range;
import com.vladmihalcea.hibernate.type.range.guava.PostgreSQLGuavaRangeType;
import org.hibernate.annotations.common.reflection.java.JavaXMember;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.DynamicParameterizedType;
import org.postgresql.util.PGobject;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Properties;

public class ExtendedPostgreSQLGuavaRangeType extends PostgreSQLGuavaRangeType implements DynamicParameterizedType {

    public static final Range<Integer> EMPTY_INT_RANGE = Range.closedOpen(Integer.MIN_VALUE, Integer.MIN_VALUE);
    public static final Range<Long> EMPTY_LONG_RANGE = Range.closedOpen(Long.MIN_VALUE, Long.MIN_VALUE);
    public static final Range<BigDecimal> EMPTY_BIGDECIMAL_RANGE = Range.closedOpen(BigDecimal.ZERO, BigDecimal.ZERO);
    public static final Range<LocalDateTime> EMPTY_LOCALDATETIME_RANGE = Range.closedOpen(LocalDateTime.MIN, LocalDateTime.MIN);
    public static final Range<LocalDate> EMPTY_DATE_RANGE = Range.closedOpen(LocalDate.MIN, LocalDate.MIN);

    private Class<?> elementType;

    @Override
    public void setParameterValues(Properties parameters) {
        JavaXMember javaXMember = (JavaXMember) parameters.get(DynamicParameterizedType.XPROPERTY);
        ParameterizedType javaType = (ParameterizedType) javaXMember.getJavaType();
        elementType = (Class<?>) javaType.getActualTypeArguments()[0];
    }

    public Class<?> getElementType() {
        return elementType;
    }

    public void setElementType(Class<?> elementType) {
        this.elementType = elementType;
    }

    @Override
    protected Range get(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws SQLException {
        PGobject pgObject = (PGobject) rs.getObject(names[0]);

        if (pgObject == null) {
            return null;
        }

        String type = pgObject.getType();
        String value = pgObject.getValue();

        // Handle normalised empty ranges, i.e. empty and is canonical form (,)
        if ("empty".equals(value)) {
            switch (type) {
                case "int4range":
                    return EMPTY_INT_RANGE;
                case "int8range":
                    return EMPTY_LONG_RANGE;
                case "numrange":
                    return EMPTY_BIGDECIMAL_RANGE;
                case "tsrange":
                    return EMPTY_LOCALDATETIME_RANGE;
                case "daterange":
                    return EMPTY_DATE_RANGE;
                default:
                    throw new IllegalStateException("The range type [" + type + "] is not supported!");
            }
        } else if ("(,)".equals(value)) {
            return Range.all();
        }

        return super.get(rs, names, session, owner);
    }

    @Override
    protected void set(PreparedStatement st, Range range, int index, SharedSessionContractImplementor session) throws SQLException {
        if (range == null) {
            st.setNull(index, Types.OTHER);
        } else {
            PGobject object = new PGobject();
            object.setType(determineRangeType(range));
            object.setValue(range.isEmpty() ? "empty" : asString(range));
            st.setObject(index, object);
        }
    }

    private String determineRangeType(Range<?> range) {
        Class<?> clazz = getElementType();

        if (clazz == null) {
            Object anyEndpoint = range.hasLowerBound() ? range.lowerEndpoint() : range.upperEndpoint();
            clazz = anyEndpoint.getClass();
        }

        if (clazz.equals(Integer.class)) {
            return "int4range";
        } else if (clazz.equals(Long.class)) {
            return "int8range";
        } else if (clazz.equals(BigDecimal.class)) {
            return "numrange";
        } else if (clazz.equals(LocalDateTime.class)) {
            return "tsrange";
        } else if (clazz.equals(ZonedDateTime.class)) {
            return "tstzrange";
        } else if (clazz.equals(LocalDate.class)) {
            return "daterange";
        }

        throw new IllegalStateException("The class [" + clazz.getName() + "] is not supported!");
    }

}
