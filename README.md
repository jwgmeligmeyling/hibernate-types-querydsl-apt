Hibernate-Types extensions for QueryDSL APT
======

Extensions for QueryDSL APT to map entity properties mapped with [Hibernate-Types](https://github.com/vladmihalcea/hibernate-types).
Hibernate-Types makes fills in the gap between the Java Persistence API and modern Java ecosystems.
It adds various custom types for mapping arrays and Java 8 Years and Months to Hibernate.
It also adds various custom types for database specific data types, such as JSON, HStore and Range fields.
However, after finding Hibernate-Types, it can be difficult to use these types in Querydsl queries.
This extension for Querydsl makes sure that the query types are generated with the proper expression and path types,
and that commonly used functions are made easily accessible.

# What is currently supported?

* PostgreSQL Ranges through Guava's `Range` class
* Various arrays mapped as their native database equivalent
* Various `json`/`jsonb` fields
* `hstore` fields
* Java `Period` and `Duration` objects for PostgreSQL `intervals`.

Future considerations are:

* Release to Maven central

# Example use

For example the mapping:

```java
@Entity
@TypeDef(name = "range", typeClass = PostgreSQLRangeType.class, defaultForType = Range.class)
public class RangeEntity {

    @Id
    Long id;

    @Type(type = "range")
    Range<LocalDateTime> localDateTimeRange;

}
```

Allows for the following path creation:

```java
QRangeEntity.rangeEntity.localDateTimeRange.intersects(Range.closedOpen(LocalDateTime.MIN, LocalDateTime.MAX))
```

For more examples, look at the following test cases:

* [`ArrayEntityPathTest`](querydsl-ext-testsuite/src/test/java/com/pallasathenagroup/querydsl/ArrayEntityPathTest.java) - for dealing with arrays;
* [`RangeEntityPathTest`](querydsl-ext-testsuite/src/test/java/com/pallasathenagroup/querydsl/RangeEntityPathTest.java) - for dealing with ranges;
* [`DurationEntityPathTest`](querydsl-ext-testsuite/src/test/java/com/pallasathenagroup/querydsl/DurationEntityPathTest.java) and [`PeriodEntityPathTest`](querydsl-ext-testsuite/src/test/java/com/pallasathenagroup/querydsl/PeriodEntityPathTest.java) - for dealing with intervals;
* [`JsonNodePathTest`](querydsl-ext-testsuite/src/test/java/com/pallasathenagroup/querydsl/JsonNodePathTest.java) - for dealing with JSON values;
* [`MonetaryAmountPathTest`](querydsl-ext-testsuite/src/test/java/com/pallasathenagroup/querydsl/MonetaryAmountPathTest.java) - for dealing with monetary amounts;
* [`HstorePathTest`](querydsl-ext-testsuite/src/test/java/com/pallasathenagroup/querydsl/HstorePathTest.java) - for dealing with HSTORE fields.

# Maven configuration
Note: this configuration is for Querydsl 5.0 and above. For earlier versions of Querydsl, use `hibernate-types-querydsl-apt:1.1.0` instead.

```xml
<dependencies>
    <dependency>
        <groupId>com.querydsl</groupId>
        <artifactId>querydsl-jpa</artifactId>
        <version>${querydsl.version}</version>
    </dependency>
    <dependency>
        <groupId>com.querydsl</groupId>
        <artifactId>querydsl-apt</artifactId>
        <version>${querydsl.version}</version>
        <classifier>jpa</classifier>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>${project.groupId}</groupId>
        <artifactId>querydsl-ext-api</artifactId>
        <version>${project.version}</version>
    </dependency>
    <dependency>
        <groupId>${project.groupId}</groupId>
        <artifactId>querydsl-ext-impl</artifactId>
        <version>${project.version}</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>${project.groupId}</groupId>
        <artifactId>querydsl-ext-apt</artifactId>
        <version>${project.version}</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```
