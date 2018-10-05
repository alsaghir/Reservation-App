package com.reservation.reservationapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    // to define how JSON strings in the request body are deserialize
    // from requests in POJOs
    // This will override the auto-configured object mapper used and built
    // in by Spring Boot
    @Bean
    public ObjectMapper objectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        System.out.println("Object Mapper Calling is HERE");

        return objectMapper;
    }

    // will define how to serialize our java objects into JSON string
    // in the response body
    @Bean
    public ObjectWriter objectWriter(ObjectMapper objectMapper){
        return objectMapper.writerWithDefaultPrettyPrinter();
    }


/**
 * Resource Modeling
 *
 * Restful system composed of resources
 * Resource is anything that has a URI
 * Example of resources are reservations, amenities and service requests
 * Resources could be created, updated, retrieved or deleted
 *
 * Domains
 *
 * Resources are grouped in domains
 * Domain is a cohesive set of resources
 * Example of a domain is a Room
 * In a Room domain, there is reservation, amenity and service request
 *
 * API Endpoint
 *
 * In format /domain/resource/version/identifier if exists
 *
 * Collection resource : /room/reservation/v1
 * Singleton resource  : /room/reservation/v1/{id}
 *
 * Versioning allows to handle different versions of a resource
 *
 */

}
