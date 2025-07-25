/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.data;

import static com.sisllc.instaiml.data.DataGeneratorBase.JAVA_FAKER;
import com.sisllc.instaiml.model.ClaimsData;
import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ClaimsDataGenerator extends DataGeneratorBase {

    public static ClaimsData generate(String memberId, String providerId, String insurancePlanId) {
        return ClaimsData.builder()
            .id(UUID.randomUUID().toString())
            .memberId(memberId)
            .providerId(providerId)
            .insurancePlanId(insurancePlanId)
            .billedAmount(new BigDecimal(JAVA_FAKER.number().randomDouble(2, 10, 3000)))
            .allowedAmount(new BigDecimal(JAVA_FAKER.number().randomDouble(2, 10, 3000)))
            .paidAmount(new BigDecimal(JAVA_FAKER.number().randomDouble(2, 10, 3000)))
            .diagnosisCodes(NET_FAKER.medicalProcedure().icd10())
            .procedureCodes(NET_FAKER.medicalProcedure().icd10())
            .claimStatus(NET_FAKER.options().option("SUBMITTED", "RE-SUBMITTED", "PROCESSED", "IN_PROGRESS", "PENDING", "REJECTED", "PAID"))
            .serviceDate(JAVA_FAKER.date().past(JAVA_FAKER.number().numberBetween(20, 50), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .claimDate(JAVA_FAKER.date().past(JAVA_FAKER.number().numberBetween(5, 20), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .createdDate(JAVA_FAKER.date().past(JAVA_FAKER.number().numberBetween(100, 300), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .updatedDate(JAVA_FAKER.date().past(JAVA_FAKER.number().numberBetween(30, 100), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .build();
    }    
}
