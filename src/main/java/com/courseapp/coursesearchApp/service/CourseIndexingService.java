package com.courseapp.coursesearchApp.service; // Correct package name

import com.courseapp.coursesearchApp.document.CourseDocument; // Import our CourseDocument class
import com.fasterxml.jackson.core.type.TypeReference; // From Jackson for JSON deserialization
import com.fasterxml.jackson.databind.ObjectMapper; // From Jackson for JSON processing
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule; // IMPORTANT: To correctly parse Instant/LocalDateTimes from JSON
import jakarta.annotation.PostConstruct; // Standard Java EE annotation, available in Spring Boot
import org.slf4j.Logger; // For logging messages
import org.slf4j.LoggerFactory; // For creating a logger
import org.springframework.core.io.ClassPathResource; // Spring utility to read files from classpath
import org.springframework.data.elasticsearch.core.ElasticsearchOperations; // Spring Data Elasticsearch core interface
import org.springframework.stereotype.Service; // Spring annotation to mark this class as a service component

import java.io.IOException; // For file/stream errors
import java.io.InputStream; // For reading file content
import java.util.List; // To hold our list of CourseDocument objects

@Service // Marks this class as a Spring Service component. Spring will create and manage it.
public class CourseIndexingService {

    private static final Logger log = LoggerFactory.getLogger(CourseIndexingService.class); // Logger for helpful messages
    private final ElasticsearchOperations elasticsearchOperations; // Spring Data Elasticsearch template for operations
    private final ObjectMapper objectMapper; // Jackson object mapper to read JSON

    // Constructor for dependency injection. Spring automatically provides ElasticsearchOperations.
    public CourseIndexingService(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // Register module to handle Java 8 Date/Time types (like Instant)
    }

    @PostConstruct // This annotation ensures this method runs automatically AFTER the service is created and dependencies are injected.
    public void indexSampleData() {
        log.info("Starting course data indexing process...");

        // IMPORTANT for development: Delete and recreate the index for a fresh start each time.
        // In production, you'd manage index migrations more carefully.
        if (elasticsearchOperations.indexOps(CourseDocument.class).exists()) {
            log.info("Courses index already exists. Deleting for a fresh start.");
            elasticsearchOperations.indexOps(CourseDocument.class).delete();
        }

        log.info("Creating 'courses' index with mappings from CourseDocument...");
        // This creates the 'courses' index in Elasticsearch based on the @Document and @Field annotations in CourseDocument.
        elasticsearchOperations.indexOps(CourseDocument.class).createWithMapping();


        try (InputStream inputStream = new ClassPathResource("sample-courses.json").getInputStream()) {
            // Read the JSON file and convert it into a List of CourseDocument objects
            List<CourseDocument> courses = objectMapper.readValue(inputStream, new TypeReference<List<CourseDocument>>() {});
            log.info("Successfully loaded {} courses from sample-courses.json.", courses.size());

            // Bulk index the documents into Elasticsearch
            elasticsearchOperations.save(courses); // Spring Data Elasticsearch handles the bulk indexing efficiently
            log.info("Successfully indexed {} courses into Elasticsearch.", courses.size());

        } catch (IOException e) {
            // Log any errors that occur during file reading or indexing
            log.error("Failed to read or index sample data: {}", e.getMessage(), e);
        }
    }
}