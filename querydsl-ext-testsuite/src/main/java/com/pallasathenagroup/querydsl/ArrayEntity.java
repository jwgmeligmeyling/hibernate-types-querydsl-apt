package com.pallasathenagroup.querydsl;

import com.vladmihalcea.hibernate.type.array.DateArrayType;
import com.vladmihalcea.hibernate.type.array.DoubleArrayType;
import com.vladmihalcea.hibernate.type.array.EnumArrayType;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.LongArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.array.TimestampArrayType;
import com.vladmihalcea.hibernate.type.array.UUIDArrayType;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
@TypeDefs({
        @TypeDef(name = "uuid-array", typeClass = UUIDArrayType.class, defaultForType = UUID[].class),
        @TypeDef(name = "string-array", typeClass = StringArrayType.class, defaultForType = String[].class),
        @TypeDef(name = "int-array", typeClass = IntArrayType.class, defaultForType = int[].class),
        @TypeDef(name = "long-array", typeClass = LongArrayType.class, defaultForType = long[].class),
        @TypeDef(name = "double-array", typeClass = DoubleArrayType.class, defaultForType = double[].class),
        @TypeDef(name = "date-array", typeClass = DateArrayType.class, defaultForType = Date[].class),
        @TypeDef(name = "timestamp-array", typeClass = TimestampArrayType.class, defaultForType = Timestamp[].class),
        @TypeDef(name = "sensor-state-array", typeClass = EnumArrayType.class, parameters = {
                @Parameter(name = EnumArrayType.SQL_ARRAY_TYPE, value = "sensor_state")}
        , defaultForType = ArrayEntity.SensorState[].class),
        @TypeDef(name = "sensor-state", typeClass = PostgreSQLEnumType.class, defaultForType = ArrayEntity.SensorState.class)

})
public class ArrayEntity {

    @Id
    Long id;

    @Type(type = "uuid-array")
    @Column(name = "sensor_ids", columnDefinition = "uuid[]")
    UUID[] sensorIds;

    @Type(type = "string-array")
    @Column(name = "sensor_names", columnDefinition = "text[]")
    String[] sensorNames;

    @Type(type = "int-array")
    @Column(name = "sensor_values", columnDefinition = "integer[]")
    int[] sensorValues;

    @Type(type = "long-array")
    @Column(name = "sensor_long_values", columnDefinition = "bigint[]")
    long[] sensorLongValues;

    @Type(type = "double-array")
    @Column(name = "sensor_double_values", columnDefinition = "float8[]")
    double[] sensorDoubleValues;

    @Type(type = "date-array")
    @Column(name = "date_values", columnDefinition = "date[]")
    Date[] dateValues;

    @Type(type = "timestamp-array")
    @Column(name = "timestamp_values", columnDefinition = "timestamp[]")
    Date[] timestampValues;

    @Type(type = "sensor-state-array")
    @Column(name = "sensor_states", columnDefinition = "sensor_state[]")
    private SensorState[] sensorStates;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID[] getSensorIds() {
        return sensorIds;
    }

    public void setSensorIds(UUID[] sensorIds) {
        this.sensorIds = sensorIds;
    }

    public String[] getSensorNames() {
        return sensorNames;
    }

    public void setSensorNames(String[] sensorNames) {
        this.sensorNames = sensorNames;
    }

    public int[] getSensorValues() {
        return sensorValues;
    }

    public void setSensorValues(int[] sensorValues) {
        this.sensorValues = sensorValues;
    }

    public long[] getSensorLongValues() {
        return sensorLongValues;
    }

    public void setSensorLongValues(long[] sensorLongValues) {
        this.sensorLongValues = sensorLongValues;
    }

    public double[] getSensorDoubleValues() {
        return sensorDoubleValues;
    }

    public void setSensorDoubleValues(double[] sensorDoubleValues) {
        this.sensorDoubleValues = sensorDoubleValues;
    }

    public Date[] getDateValues() {
        return dateValues;
    }

    public void setDateValues(Date[] dateValues) {
        this.dateValues = dateValues;
    }

    public Date[] getTimestampValues() {
        return timestampValues;
    }

    public void setTimestampValues(Date[] timestampValues) {
        this.timestampValues = timestampValues;
    }

    public SensorState[] getSensorStates() {
        return sensorStates;
    }

    public void setSensorStates(SensorState[] sensorStates) {
        this.sensorStates = sensorStates;
    }

    public enum SensorState {
        ONLINE, OFFLINE, UNKNOWN;
    }

}
