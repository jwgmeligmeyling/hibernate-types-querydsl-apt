Hibernate-Types extensions for QueryDSL APT
======

Extensions for QueryDSL APT to map entity properties mapped with [Hibernate-Types](https://github.com/vladmihalcea/hibernate-types).

# What is it?

* Traverse Guava `Range` expressions and perform functions on them
* Traverse expressions on Java Arrays mapped to a PostgreSQL or MySQL array and perform functions on them

The implementation is an unreleased proof-of-concept.

Future considerations are:

* Release to Maven central
* Support for JSON(B) types
* Support for `Period` and `Duration` types that are mapped to a PostgeSQL `interval`


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

