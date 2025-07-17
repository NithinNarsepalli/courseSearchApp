package com.courseapp.coursesearchApp.document; // Correct package name

import lombok.Data; // From Lombok dependency
import org.springframework.data.annotation.Id; // From Spring Data
import org.springframework.data.elasticsearch.annotations.Document; // From Spring Data Elasticsearch
import org.springframework.data.elasticsearch.annotations.Field; // From Spring Data Elasticsearch
import org.springframework.data.elasticsearch.annotations.FieldType; // From Spring Data Elasticsearch
import org.springframework.data.elasticsearch.annotations.Setting; // From Spring Data Elasticsearch

import java.time.Instant; // For handling dates and times (Java 8 Date/Time API)

@Data // Lombok: Automatically generates getters, setters, equals, hashCode, toString
@Document(indexName = "courses") // Tells Spring Data Elasticsearch: "This class maps to an index named 'courses' in Elasticsearch."
@Setting(replicas = 0, shards = 1) // Simple settings for local development: no copies of data (replicas), 1 main piece of data (shard).
public class CourseDocument {

    @Id // Marks this field as the unique identifier (ID) for documents in Elasticsearch.
    private String id;

    // FieldType.Text: Good for full-text search. 'analyzer="english"' makes search smarter (e.g., "running" matches "run").
    @Field(type = FieldType.Text, analyzer = "english")
    private String title;

    @Field(type = FieldType.Text, analyzer = "english")
    private String description;

    // FieldType.Keyword: Good for exact matches, filtering, and sorting (e.g., "Math" exactly). Not analyzed.
    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Keyword)
    private String type; // e.g., ONE_TIME, COURSE, CLUB

    @Field(type = FieldType.Keyword) // Stored as a keyword, as it's a specific text string.
    private String gradeRange;

    @Field(type = FieldType.Integer) // Numeric type for age range queries.
    private Integer minAge;

    @Field(type = FieldType.Integer) // Numeric type for age range queries.
    private Integer maxAge;

    @Field(type = FieldType.Double) // Numeric type (decimal) for price range queries.
    private Double price;

    // FieldType.Date: For date and time values.
    // 'format = {}' and 'pattern = "..."' tells Elasticsearch how to parse/store the ISO-8601 string.
    @Field(type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Instant nextSessionDate;
}