# Course Search Application

## Project Overview

This project presents a robust Spring Boot application meticulously developed to provide efficient search and data retrieval capabilities for a comprehensive course catalog. Leveraging the power of Elasticsearch, this application exposes a well-defined RESTful API, demonstrating proficiency in backend development and modern search engine integration.

This solution reflects a dedicated approach to building scalable and functional systems, ensuring clarity and precision in its design and implementation.

## Technical Stack & Competencies Demonstrated

* **Backend Framework:** Spring Boot 3.3.1 - Proficient in building RESTful services and managing application lifecycle.
* **Search Engine Integration:** Spring Data Elasticsearch (Elasticsearch 8.x client) - Demonstrated ability to integrate and leverage advanced search functionalities.
* **Build Automation:** Apache Maven - Competent in dependency management, project compilation, and package generation.
* **Programming Language:** Java 21 - Strong grasp of Java's core concepts and modern features for enterprise application development.
* **Code Optimization:** Lombok - Applied for concise and maintainable code, reducing boilerplate.
* **Version Control:** Git & GitHub - Successfully utilized for collaborative development, meticulous change tracking, and project deployment.

## Key Features & API Endpoints

This application delivers the following core functionalities, designed for efficiency and user-friendliness:

### 1. Comprehensive Course Search

* **Endpoint:** `GET /api/courses/search`
* **Purpose:** Facilitates advanced searching of courses based on diverse criteria, ensuring precise results.
* **Parameters Supported:**
    * `q`: Full-text search across course `title`, `description`, and `category`.
    * `minAge`, `maxAge`: Filters for age-specific courses.
    * `minPrice`, `maxPrice`: Enables price-range based filtering.
    * `category`, `type`: Categorical and type-specific filtering for targeted searches.
    * `startDate`: Filters courses scheduled on or after a specified date.
    * `sort`: Configurable sorting by `priceasc`, `pricedesc`, or `upcoming` session dates.
    * `page`, `size`: Supports efficient pagination of search results.
* **Example Usage:** `http://localhost:8080/api/courses/search?q=math&category=Science&minAge=10&sort=priceasc`

### 2. Retrieve All Courses

* **Endpoint:** `GET /api/courses/all`
* **Purpose:** Provides an exhaustive list of all available course documents indexed within Elasticsearch.
* **Example Usage:** `http://localhost:8080/api/courses/all`

## Project Architecture & Structure

The project is structured adhering to standard Spring Boot and Maven conventions, promoting modularity and maintainability:

* **`pom.xml`**: Centralized Maven configuration for dependency management and build lifecycle.
* **`src/main/java/com/courseapp/coursesearchApp/`**: Houses the core Java source code.
    * `CourseSearchAppApplication.java`: The primary application entry point.
    * `controller/CourseSearchController.java`: Defines and implements the RESTful API endpoints.
    * `document/CourseDocument.java`: Represents the Elasticsearch document model for course entities.
    * `dto/CourseSearchResponse.java`: Custom Data Transfer Object for structuring search API responses.
    * `(repository/CourseRepository.java)`: (Include if you have this interface in your project, otherwise omit) An interface for streamlined data access operations with Spring Data Elasticsearch.
* **`src/main/resources/`**: Contains application configuration.
    * `application.properties`: Configures critical application properties, notably Elasticsearch connection details.

## Local Setup and Execution Guide

To set up and run this application locally, ensuring a smooth operational experience:

### Prerequisites

* **Java Development Kit (JDK) 21**: Verify installation and correct environment configuration.
* **Apache Maven**: Ensure Maven is installed and accessible via your system's PATH.
* **Elasticsearch (version 8.x)**: A running instance of Elasticsearch is imperative. For quick setup, Docker is recommended (e.g., `docker run -p 9200:9200 -e "discovery.type=single-node" -e "xpack.security.enabled=false" docker.elastic.co/elasticsearch/elasticsearch:8.x.x`). Adjust `spring.elasticsearch.uris` in `application.properties` if your Elasticsearch instance is not on `http://localhost:9200`.

### Build and Run Instructions

1.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/NithinNarsepalli/courseSearchApp.git](https://github.com/NithinNarsepalli/courseSearchApp.git)
    cd courseSearchApp
    ```

2.  **Build the Project:**
    Compile the project and resolve all dependencies using Maven:
    ```bash
    ./mvnw clean install
    ```

3.  **Execute the Application:**
    Launch the Spring Boot application:
    ```bash
    java -jar target/courseSearchApp-0.0.1-SNAPSHOT.jar
    ```
    The application will commence operation on `http://localhost:8080`.
    Upon startup, the application automatically reads `sample-courses.json` from `src/main/resources/` and bulk-indexes the course data into Elasticsearch.

## Commitment to Version Control & Future Development

All project updates are meticulously tracked using Git, with a commitment to descriptive commit messages that clearly articulate changes and progress. This ensures a comprehensive and auditable development history, reflecting adherence to professional coding practices.

---