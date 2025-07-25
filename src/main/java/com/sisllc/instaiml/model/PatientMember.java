/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.CosmosIndexingPolicy;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
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
        "/patientId/?",
        "/memberId/?"
    },
    excludePaths = {
        "/*"
    }
)
@Container(containerName = "patientMembers")
public class PatientMember {
    @Id
    @PartitionKey
    private String id;
    
    private String patientId;
    private String memberId;
    
    public PatientMember() {
        if (id == null || id.isBlank()) {
           id = UUID.randomUUID().toString();
        }
    }            
}
