# Course Search Application

## Project Overview

This is a Spring Boot application designed to provide robust search and retrieval functionalities for course data, utilizing Elasticsearch as its primary search engine. The project focuses on building a RESTful API for efficient course discovery.

## Technologies Used

* **Backend Framework:** Spring Boot 3.3.1
* **Search Engine Integration:** Spring Data Elasticsearch (integrates with Elasticsearch 8.x client)
* **Build Tool:** Apache Maven
* **Programming Language:** Java 21
* **Utility Library:** Lombok (for reducing boilerplate code)
* **Version Control:** Git & GitHub

## Features

The application currently provides the following core functionalities:

### 1. Search Courses

* **Endpoint:** `GET /api/courses/search`
* **Description:** Allows users to search for courses based on various criteria.
* **Parameters:**
    * `q` (Optional): Query string to search across `title`, `description`, and `category`.
    * `minAge` (Optional): Minimum age for the course.
    * `maxAge` (Optional): Maximum age for the course.
    * `minPrice` (Optional): Minimum price for the course.
    * `maxPrice` (Optional): Maximum price for the course.
    * `category` (Optional): Filter by a specific course category.
    * `type` (Optional): Filter by a specific course type.
    * `startDate` (Optional): Filter for courses starting on or after a specific date (e.g., `2025-07-17T00:00:00Z`).
    * `sort` (Optional, default: `upcoming`): Sorting option (`priceasc`, `pricedesc`, `upcoming`).
    * `page` (Optional, default: `0`): Page number for pagination.
    * `size` (Optional, default: `10`): Number of results per page.
* **Example Usage:**
    * `http://localhost:8080/api/courses/search?q=math&category=Science&minAge=10&sort=priceasc`

### 2. Get All Courses

* **Endpoint:** `GET /api/courses/all`
* **Description:** Retrieves a list of all course documents currently available in Elasticsearch.
* **Example Usage:**
    * `http://localhost:8080/api/courses/all`

## Project Structure Highlights

* `pom.xml`: Maven build file, managing dependencies and project build configurations.
* `src/main/java/com/courseapp/coursesearchApp/`:
    * `CourseSearchAppApplication.java`: Main Spring Boot application entry point.
    * `controller/CourseSearchController.java`: Defines the REST API endpoints for course search and retrieval.
    * `document/CourseDocument.java`: Defines the data model for a course document stored in Elasticsearch.
    * `dto/CourseSearchResponse.java`: Data Transfer Object for structuring search API responses.
    * `(Optional) repository/CourseRepository.java`: If used, an interface for Spring Data Elasticsearch repository operations.
* `src/main/resources/`:
    * `application.properties`: Configuration file for application settings, including Elasticsearch connection URI.

## Setup and Running Locally

To set up and run this project on your local machine, follow these steps:

### Prerequisites

* **Java Development Kit (JDK) 21**
* **Apache Maven**
* **Elasticsearch (version 8.x recommended)**:
    * Ensure an Elasticsearch instance is running and accessible. You can run it via Docker (e.g., `docker run -p 9200:9200 -e "discovery.type=single-node" -e "xpack.security.enabled=false" docker.elastic.co/elasticsearch/elasticsearch:8.x.x`) or install it directly.
    * Update `spring.elasticsearch.uris` in `src/main/resources/application.properties` if your Elasticsearch is not on `http://localhost:9200`.

### Building and Running

1.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/NithinNarsepalli/courseSearchApp.git](https://github.com/NithinNarsepalli/courseSearchApp.git)
    cd courseSearchApp
    ```

2.  **Build the Project:**
    Use Maven to build the project and download all dependencies.
    ```bash
    ./mvnw clean install
    ```

3.  **Run the Application:**
    ```bash
    java -jar target/courseSearchApp-0.0.1-SNAPSHOT.jar
    ```
    The application will start on `http://localhost:8080`.

## Future Enhancements (Possible ideas for your assignment)

* Add autocomplete/suggestion functionality (if decided to be implemented later).
* Implement user authentication and authorization.
* Integrate with a front-end application.
* More advanced Elasticsearch queries (e.g., aggregations, geo-search).

---