/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.data;

import com.sisllc.instaiml.model.Medication;
import java.time.ZoneOffset;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MedicationGenerator extends DataGeneratorBase {

    public static Medication generate() {
        return Medication.builder()
            .id(UUID.randomUUID().toString())
            .name(JAVA_FAKER.medical().medicineName())
            .description(JAVA_FAKER.medical().medicineName())
            .dosage(JAVA_FAKER.number().numberBetween(1, 3) + " per day")
            .unit(JAVA_FAKER.number().numberBetween(20, 300) + " mg")
            .createdDate(JAVA_FAKER.date().past(JAVA_FAKER.number().numberBetween(30, 90), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .updatedDate(JAVA_FAKER.date().past(JAVA_FAKER.number().numberBetween(1, 30), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .build();
    }    
}
