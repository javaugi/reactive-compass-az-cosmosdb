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
//*
@CosmosIndexingPolicy(
    includePaths = {
        "/patientId/?",
        "/physicianId/?",
        "/medicationId/?",
        "/pharmacyId/?",
        "/status/?"
    },
    excludePaths = {
        "/*"
    }
) 
// */

/*
- The /? suffix means index the scalar value at that path, but not nested objects or arrays.
- If you want to index nested fields or arrays, you'd use /field/* or /field/[]/subfield/?.
*/
@Container(containerName = "prescriptions")
public class Prescription {
    @Id
    private String id;

    @PartitionKey
    private String patientId;
    
    private String physicianId;
    private String medicationId;
    private String pharmacyId;

    private Integer quantity;
    private Integer refills;
    private String status; // (string, e.g., "active", "filled", "cancelled")    

    private OffsetDateTime fillDate;
    private OffsetDateTime prescriptionDate;
    
    @CreatedDate
    private OffsetDateTime createdDate;   

    @LastModifiedDate
    private OffsetDateTime updatedDate;
        
    public Prescription() {
        if (id == null || id.isBlank()) {
            id = UUID.randomUUID().toString();
        }
        this.createdDate = OffsetDateTime.now();
        this.updatedDate = OffsetDateTime.now();  
        this.status = "active";
        this.fillDate = OffsetDateTime.now();        
    }
}
