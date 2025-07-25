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
        "/insuranceCompanyId/?",
        "/planName/?",
        "/planType/?",
        "/active/?"
    },
    excludePaths = {
        "/*"
    }
)
@Container(containerName = "insurancePlans")
public class InsurancePlan {
    @Id 
    private String id;
    
    @PartitionKey
    private String insuranceCompanyId;
        
    private String planName;
    private String planType;
    private String networkType;
    private String tierLevel;
    private boolean active;

    private OffsetDateTime effectiveDate;
    private OffsetDateTime expirationDate;

    @CreatedDate
    private OffsetDateTime createdDate;   
    @LastModifiedDate
    private OffsetDateTime updatedDate;
    
    public InsurancePlan() {
        if (id == null || id.isBlank()) {
           id = UUID.randomUUID().toString();
        }
        this.active = true;
        this.createdDate = OffsetDateTime.now();
        this.updatedDate = OffsetDateTime.now();                      
        this.effectiveDate = OffsetDateTime.now();
    }    
}
