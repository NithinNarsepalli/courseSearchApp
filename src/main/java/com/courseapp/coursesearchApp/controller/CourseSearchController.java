package com.courseapp.coursesearchApp.controller;

import com.courseapp.coursesearchApp.document.CourseDocument;
import com.courseapp.coursesearchApp.dto.CourseSearchResponse; // Import the new DTO
import org.springframework.data.domain.PageRequest; // For pagination
import org.springframework.data.domain.Sort; // For sorting
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.http.ResponseEntity; // For returning a richer HTTP response
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
//import java.util.Optional; // To handle optional parameters
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
public class CourseSearchController {

    private final ElasticsearchOperations elasticsearchOperations;

    public CourseSearchController(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    // --- NEW SEARCH ENDPOINT WITH FILTERS, SORTING, AND PAGINATION ---
    // Accessible at: http://localhost:8080/api/courses/search?...
    @GetMapping("/search")
    public ResponseEntity<CourseSearchResponse> searchCourses(
            @RequestParam(name = "q", required = false) String query, // Optional search keyword
            @RequestParam(name = "minAge", required = false) Integer minAge, // Optional minAge filter
            @RequestParam(name = "maxAge", required = false) Integer maxAge, // Optional maxAge filter
            @RequestParam(name = "category", required = false) String category, // Optional category filter
            @RequestParam(name = "type", required = false) String type, // Optional type filter
            @RequestParam(name = "minPrice", required = false) Double minPrice, // Optional minPrice filter
            @RequestParam(name = "maxPrice", required = false) Double maxPrice, // Optional maxPrice filter
            @RequestParam(name = "startDate", required = false) Instant startDate, // Optional nextSessionDate filter (on or after)
            @RequestParam(name = "sort", defaultValue = "upcoming") String sort, // Sort parameter with a default
            @RequestParam(name = "page", defaultValue = "0") int page, // Pagination: page number (default 0)
            @RequestParam(name = "size", defaultValue = "10") int size // Pagination: page size (default 10)
    ) {
        Criteria criteria = new Criteria(); // Start with an empty criteria, we'll build it dynamically

        // 1. Full-text search (if 'q' is provided)
        if (query != null && !query.trim().isEmpty()) {
            criteria = criteria.or("name").contains(query)
                               .or("description").contains(query)
                               .or("category").contains(query); // Still using 'or' for multi-field search
        }

        // 2. Range Filters
        if (minAge != null) {
            // For numeric ranges, 'greaterThanEqual' and 'lessThanEqual' are used
            criteria = criteria.and("minAge").greaterThanEqual(minAge);
        }
        if (maxAge != null) {
            criteria = criteria.and("maxAge").lessThanEqual(maxAge);
        }
        if (minPrice != null) {
            criteria = criteria.and("price").greaterThanEqual(minPrice);
        }
        if (maxPrice != null) {
            criteria = criteria.and("price").lessThanEqual(maxPrice);
        }

        // 3. Exact Filters
        if (category != null && !category.trim().isEmpty()) {
            // For exact matches, use 'is' on a 'keyword' field or 'text' field that is keyword-analyzed
            // Assuming 'category' is mapped as keyword for exact match.
            criteria = criteria.and("category").is(category);
        }
        if (type != null && !type.trim().isEmpty()) {
            // Assuming 'type' is mapped as keyword for exact match.
            criteria = criteria.and("type").is(type);
        }

        // 4. Date Filter (on or after)
        if (startDate != null) {
            criteria = criteria.and("nextSessionDate").greaterThanEqual(startDate);
        }

        CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);

        // 5. Sorting
        Sort sortOrder;
        switch (sort.toLowerCase()) {
            case "priceasc":
                sortOrder = Sort.by(Sort.Direction.ASC, "price");
                break;
            case "pricedesc":
                sortOrder = Sort.by(Sort.Direction.DESC, "price");
                break;
            case "upcoming": // Default sort (soonest upcoming first)
            default:
                sortOrder = Sort.by(Sort.Direction.ASC, "nextSessionDate");
                break;
        }
        criteriaQuery.addSort(sortOrder);

        // 6. Pagination
        // PageRequest.of(pageNumber, pageSize)
        PageRequest pageRequest = PageRequest.of(page, size);
        criteriaQuery.setPageable(pageRequest);


        // Execute the search
        SearchHits<CourseDocument> searchHits = elasticsearchOperations.search(criteriaQuery, CourseDocument.class);

        // Prepare the response
        List<CourseDocument> courses = searchHits.stream()
                                                .map(SearchHit::getContent)
                                                .collect(Collectors.toList());

        CourseSearchResponse response = new CourseSearchResponse(searchHits.getTotalHits(), courses);

        return ResponseEntity.ok(response);
    }

    // Keep the /all endpoint for easy testing of all data if needed
    @GetMapping("/all")
    public ResponseEntity<List<CourseDocument>> getAllCourses() {
        SearchHits<CourseDocument> searchHits = elasticsearchOperations.search(new CriteriaQuery(new Criteria()), CourseDocument.class);
        List<CourseDocument> courses = searchHits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
        return ResponseEntity.ok(courses);
    }
}