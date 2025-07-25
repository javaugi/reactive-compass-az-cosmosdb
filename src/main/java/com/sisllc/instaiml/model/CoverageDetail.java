/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
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
@Container(containerName = "coverageDetails")
public class CoverageDetail {
    @Id 
    private String id;
    
    @PartitionKey
    private String insurancePlanId;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal deductibleIndividual;

    @Column(precision = 10, scale = 2)
    private BigDecimal deductibleFamily;

    @Column(precision = 10, scale = 2)
    private BigDecimal oopMaxIndividual;

    @Column(precision = 10, scale = 2)
    private BigDecimal oopMaxFamily;

    @Column(precision = 10, scale = 2)
    private BigDecimal primaryCareCopay;

    @Column(precision = 10, scale = 2)
    private BigDecimal specialistCopay;

    @Column(precision = 10, scale = 2)
    private BigDecimal erCopay;

    @Column(precision = 10, scale = 2)
    private BigDecimal prescriptionTier1;

    @Column(precision = 10, scale = 2)
    private BigDecimal prescriptionTier2;

    @Column(precision = 10, scale = 2)
    private BigDecimal prescriptionTier3;
    
    private OffsetDateTime effectiveDate;
    private OffsetDateTime expirationDate;

    public CoverageDetail() {
        if (id == null || id.isBlank()) {
           id = UUID.randomUUID().toString();
        }
        this.effectiveDate = OffsetDateTime.now();
    }        
}
