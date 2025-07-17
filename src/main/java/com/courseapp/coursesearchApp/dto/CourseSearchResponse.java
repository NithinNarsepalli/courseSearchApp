package com.courseapp.coursesearchApp.dto;

import com.courseapp.coursesearchApp.document.CourseDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data // Lombok annotation to generate getters, setters, toString, equals, hashCode
@NoArgsConstructor // Lombok annotation to generate a no-argument constructor
@AllArgsConstructor // Lombok annotation to generate a constructor with all fields
public class CourseSearchResponse {
    private long total; // Total number of hits found
    private List<CourseDocument> courses; // List of matching course documents
}