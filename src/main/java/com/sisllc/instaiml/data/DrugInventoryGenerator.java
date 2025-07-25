/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.data;

import com.sisllc.instaiml.model.DrugInventory;
import java.util.UUID;

public class DrugInventoryGenerator extends DataGeneratorBase {

    public static DrugInventory generate(String pharmacyId, String medicationId) {
        return DrugInventory.builder()
            .id(UUID.randomUUID().toString())
            .pharmacyId(pharmacyId)
            .medicationId(medicationId)
            .quantity(JAVA_FAKER.number().numberBetween(15, 100))
            .reorderThreshold(JAVA_FAKER.number().numberBetween(10, 20))
            //.createdDate(JAVA_FAKER.date().past(JAVA_FAKER.number().numberBetween(30, 90), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            //.updatedDate(JAVA_FAKER.date().past(JAVA_FAKER.number().numberBetween(1, 30), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .build();
    }    
}
