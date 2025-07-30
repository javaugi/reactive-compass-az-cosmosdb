/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.data;

import com.sisllc.instaiml.model.Prescription;
import java.time.ZoneOffset;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class PrescriptionGenerator extends DataGeneratorBase {
    
    public static Prescription generate(String patientId, String physicianId, String medicationId, String pharmacyId) {
        return Prescription.builder()
            .id(UUID.randomUUID().toString())
            .patientId(patientId)
            .physicianId(physicianId)
            .medicationId(medicationId)
            .pharmacyId(pharmacyId)
            .quantity(JAVA_FAKER.number().numberBetween(1, 30))
            .refills(JAVA_FAKER.number().numberBetween(1, 4))
            .status(JAVA_FAKER.options().option("active", "expired", "filled", "cancelled"))
            .fillDate(JAVA_FAKER.date().past(JAVA_FAKER.number().numberBetween(2, 20), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .prescriptionDate(JAVA_FAKER.date().past(JAVA_FAKER.number().numberBetween(30, 90), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))            
            .createdDate(JAVA_FAKER.date().past(JAVA_FAKER.number().numberBetween(30, 90), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .updatedDate(JAVA_FAKER.date().past(JAVA_FAKER.number().numberBetween(1, 30), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .build();
    }    
    
}
