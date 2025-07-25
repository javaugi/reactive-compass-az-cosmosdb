/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.CosmosIndexingPolicy;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import jakarta.persistence.Column;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;


@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@CosmosIndexingPolicy(
    includePaths = {
        "/insurancePlanId/?",
        "/name/?",
        "/gender/?",
        "/birthDate/?"
    },
    excludePaths = {
        "/*"
    }
)
@Container(containerName = "members")
public class Member {
    @Id 
    private String id;
    
    @PartitionKey
    private String insurancePlanId;
    
    private String name;
    private String gender;
    private String address;
    
    @Column(name = "tobacco_user")
    private boolean tobaccoUser;
    
    private OffsetDateTime birthDate;
    private OffsetDateTime enrollmentDate;
    private OffsetDateTime terminationDate;

    public Member() {
        if (id == null || id.isBlank()) {
           id = UUID.randomUUID().toString();
        }
    }        

}
