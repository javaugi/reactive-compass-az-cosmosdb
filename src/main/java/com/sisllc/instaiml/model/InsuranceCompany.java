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
        "/companyCode/?",
        "/companyName/?",
        "/contactInfo/?"
    },
    excludePaths = {
        "/*"
    }
)
@Container(containerName = "insuranceCompanies")
public class InsuranceCompany {
    @Id 
    private String id;
    
    @PartitionKey
    private String companyCode;
    
    private String companyName;
    private String stateLicenses;
    private String financialRating;
    private String contactInfo;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal marketShare;

    public InsuranceCompany() {
       if (id == null || id.isBlank()) {
           id = UUID.randomUUID().toString();
       }         
    }
}
