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

# Maven configuration

```xml
<dependencies>
    <dependency>
        <groupId>com.querydsl</groupId>
        <artifactId>querydsl-jpa</artifactId>
        <version>${querydsl.version}</version>
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
</dependencies>
<build>
    <plugins>
        <plugin>
            <groupId>com.mysema.maven</groupId>
            <artifactId>apt-maven-plugin</artifactId>
            <version>1.1.3</version>
            <executions>
                <execution>
                    <goals>
                        <goal>process</goal>
                    </goals>
                    <configuration>
                        <outputDirectory>target/generated-sources/java</outputDirectory>
                        <processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>
                    </configuration>
                </execution>
            </executions>
            <dependencies>
                <dependency>
                    <groupId>com.querydsl</groupId>
                    <artifactId>querydsl-apt</artifactId>
                    <version>${querydsl.version}</version>
                </dependency>
                <dependency>
                    <groupId>${project.groupId}</groupId>
                    <artifactId>querydsl-ext-apt</artifactId>
                    <version>${project.version}</version>
                </dependency>
            </dependencies>
        </plugin>
    </plugins>
</build>
```