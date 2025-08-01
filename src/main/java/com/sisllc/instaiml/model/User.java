package com.sisllc.instaiml.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import java.time.OffsetDateTime;
import org.springframework.data.annotation.Id;
import java.util.UUID;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Builder(toBuilder = true)
@Container(containerName = "users")
public record User(
        @Id
        @PartitionKey
        String id,
    
        String name,
        String username,
        String password,
        String roles,

        String email,
        String phone,


        String firstName,

        String lastName,

        int age,

        String city,
        
        @CreatedDate
        OffsetDateTime createdDate, 

        @LastModifiedDate
        OffsetDateTime updatedDate

) {
    // Compact constructor (Java 17+).
    // Auto-generate ID if it's null or blank.
    public User {
        if (id == null || id.isBlank()) {
            id = UUID.randomUUID().toString();
        }
    }
}
