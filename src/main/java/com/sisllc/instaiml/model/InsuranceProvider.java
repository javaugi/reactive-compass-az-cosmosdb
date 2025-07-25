/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@Container(containerName = "insuranceProviders")
public class InsuranceProvider {
    @Id 
    private String id;
    
    @PartitionKey
    private String providerName;
    private String specialty;
    private String networkStatus; // (network_status IN ('In-Network', 'Out-of-Network'))
    
    public InsuranceProvider() {
        if (id == null || id.isBlank()) {
           id = UUID.randomUUID().toString();
        }
    }       
    
}
