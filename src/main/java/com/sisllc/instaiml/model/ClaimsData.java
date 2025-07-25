/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.CosmosIndexingPolicy;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import jakarta.persistence.Column;
import java.math.BigDecimal;
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
        "/memberId/?",
        "/providerId/?",
        "/insurancePlanId/?",
        "/claimStatus/?"
    },
    excludePaths = {
        "/*"
    }
)
@Container(containerName = "claimsData")
public class ClaimsData {
    @Id 
    private String id;
    
    @PartitionKey
    private String memberId;    
    private String providerId;    
    private String insurancePlanId;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal billedAmount;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal allowedAmount;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal paidAmount;
    
    private String diagnosisCodes;
    private String procedureCodes;
    
    private String claimStatus;
    private OffsetDateTime serviceDate;
    private OffsetDateTime claimDate;
    
    @CreatedDate
    private OffsetDateTime createdDate;   
    @LastModifiedDate
    private OffsetDateTime updatedDate;
    
    public ClaimsData() {
        if (id == null || id.isBlank()) {
           id = UUID.randomUUID().toString();
        }
        this.createdDate = OffsetDateTime.now();
        this.updatedDate = OffsetDateTime.now();             
        this.claimDate = OffsetDateTime.now();
    }        
}
