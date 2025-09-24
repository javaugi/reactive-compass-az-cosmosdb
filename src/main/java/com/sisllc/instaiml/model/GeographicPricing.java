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
import org.springframework.data.annotation.Id;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@CosmosIndexingPolicy(
    includePaths = {
        "/insurancePlanId/?",
        "/stateAbbr/?",
        "/zipCode/?"
    },
    excludePaths = {
        "/*"
    }
)
@Container(containerName = "geographicPricings")
public class GeographicPricing {
    @Id 
    private String id;
    
    @PartitionKey
    private String insurancePlanId;
        
    private String stateAbbr;
    private String zipCode;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal adjustmentFactor;
    
    private int ratingArea;

    private OffsetDateTime effectiveDate;
    private OffsetDateTime expirationDate;

    public GeographicPricing() {
        if (id == null || id.isBlank()) {
           id = UUID.randomUUID().toString();
        }
        this.effectiveDate = OffsetDateTime.now();
    }    
}
