/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.CosmosIndexingPolicy;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@CosmosIndexingPolicy(
    includePaths = {
        "/companyCode/?",
        "/companyName/?",
        "/contactInfo/?"
    },
    excludePaths = {
        "/*"
    }
)
@Container(containerName = "pharmacies")
public class Pharmacy {
    @Id
    private String id;

    @PartitionKey       
    private String pharmacyCode;
    private String name;
    
    private String email;
    private String phone;
    private String address;
    
    @CreatedDate
    private OffsetDateTime createdDate;   

    @LastModifiedDate
    private OffsetDateTime updatedDate;

    public Pharmacy() {
        if (id == null || id.isBlank()) {
            id = UUID.randomUUID().toString();
        }
        this.createdDate = OffsetDateTime.now();
        this.updatedDate = OffsetDateTime.now();
    }            
}