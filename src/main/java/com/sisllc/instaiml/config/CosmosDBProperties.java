package com.sisllc.instaiml.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "azure.cosmos")
@Configuration
public class CosmosDBProperties {

    private String connectionString;
    private String database; // Separate property for database name
    private String resourceGroup;
}
/*
Here's why and what the preferred Spring Boot approach for properties is: (Spring Boot handles it automatically:)
Spring Boot, by default, is highly opinionated about externalized configuration. It automatically configures a PropertySourcesPlaceholderConfigurer
    (or its internal equivalent) for you, loading properties from a variety of default locations, including:
1. application.properties (or application.yml) in the classpath root (src/main/resources).
2. application.properties (or application.yml) in the config/ subdirectory of the classpath.
3. application.properties (or application.yml) in the current directory where the application is run.
4. application.properties (or application.yml) in the config/ subdirectory relative to the current directory.
5. Profile-specific versions of these files (e.g., application-dev.properties).
6. System properties (-Dkey=value).
7. Environment variables.
8. Command-line arguments.

This rich set of default locations and the auto-configured placeholder resolver mean that if your cosmos-queries.properties file is 
    in src/main/resources (or src/main/resources/config), Spring Boot will likely find and process it automatically without you 
    needing to define a bean.
*/