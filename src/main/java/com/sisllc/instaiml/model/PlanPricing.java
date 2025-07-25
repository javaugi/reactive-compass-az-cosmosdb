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
        "/pricingType/?",
        "/ageBracket/?",
        "/coverageLevel/?"
    },
    excludePaths = {
        "/*"
    }
)
@Container(containerName = "planPricings")
public class PlanPricing {
    @Id 
    private String id;
    
    @PartitionKey
    private String insurancePlanId;
        
    private String pricingType; //(e.g. premium, deductible),
    
    @Column(precision = 10, scale = 2)
    private BigDecimal basePremium;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal tobaccoSurcharge;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal familyCoverageAdjustment;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal miscAdjustment;
    
    private String ageBracket;
    private String coverageLevel;
    
    private OffsetDateTime effectiveDate;
    private OffsetDateTime expirationDate;

    public PlanPricing() {
        if (id == null || id.isBlank()) {
           id = UUID.randomUUID().toString();
        }
        this.effectiveDate = OffsetDateTime.now();
    }    
}
