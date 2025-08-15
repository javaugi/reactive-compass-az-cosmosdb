/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.service;

import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosAsyncContainer;
import com.azure.cosmos.CosmosAsyncDatabase;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.SqlParameter;
import com.azure.cosmos.models.SqlQuerySpec;
import com.fasterxml.jackson.databind.JsonNode;
import com.sisllc.instaiml.config.CosmosDBProperties;
import com.sisllc.instaiml.model.Pharmacy;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@Service
public class PrescriptionAnalyticalService {

    //@Value("${azure.cosmos.database}")
    //private String databaseName;
    private final CosmosAsyncClient asyncClient;
    //private final ReactiveCosmosTemplate cosmosTemplate;
    private final CosmosDBProperties dbProperties;

    private static final String SIMPLE_CNT_QUERY = "SELECT p.pharmacyId, p.pharmacy.pharmacyCode, "
        + "    p.pharmacy.name AS pharmacyName, p.pharmacy.address AS pharmacyAddress"
        + "     , COUNT(1) AS activePrescriptionCount "
        + " FROM prescriptions p "
        + " WHERE p.status = 'active' "
        + " GROUP BY p.pharmacyId, p.pharmacy.pharmacyCode, p.pharmacy.name, p.pharmacy.address ";

    public void performAnalytics() {
        //this call works only if there is an embedded Pharmacy inside prescription
        /*
        activePrescriptionsByLocation();
        Pharmacy pharmacy = PharmacyGenerator.generate();
        Prescription prescription = generateWithEmbeddedPharmacy(pharmacy);
        cosmosTemplate.insert("pharmacies", pharmacy);
        cosmosTemplate.insert("prescriptions", prescription);
        // */

        log.info("PrescriptionAnalyticalService performAnalytics entered ...");
        printActivePrescriptionsByLocation();
        log.info("PrescriptionAnalyticalService performAnalytics another try");
        printActivePresByLoc();
        log.info("PrescriptionAnalyticalService performAnalytics Done");
    }


    /*
    private void activePrescriptionsByLocation() {
        try {
            SqlQuerySpec querySpec = new SqlQuerySpec(SIMPLE_CNT_QUERY);
            cosmosTemplate.runQuery(querySpec, Prescription.class, Prescription.class)
                .doOnNext(dto -> log.info("activePrescriptionsByLocation {}", dto))
                .switchIfEmpty(dto -> log.info("activePrescriptionsByLocation: No Data Found"))
                .subscribe();
        } catch (Exception ex) {
            log.error(" Error activePrescriptionsByLocation", ex);
        }
    }

    public static Pharmacy generate() {
        return Pharmacy.builder()
            .id(UUID.randomUUID().toString())
            .pharmacyCode("PHAR-" + FAKER.number().digits(6))
            .name("PHARMA " + FAKER.address().streetName())
            .email(FAKER.internet().emailAddress())
            .phone(FAKER.phoneNumber().phoneNumber())
            .address(FAKER.address().fullAddress())
            .createdDate(FAKER.date().past(FAKER.number().numberBetween(30, 90), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .updatedDate(FAKER.date().past(FAKER.number().numberBetween(1, 30), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .build();
    }
    public static Prescription generateWithEmbeddedPharmacy(Pharmacy pharmacy) {
        Prescription.EmbeddedPharmacy embedded = new Prescription.EmbeddedPharmacy();
        embedded.setPharmacyCode(pharmacy.getPharmacyCode());
        embedded.setName(pharmacy.getName());
        embedded.setAddress(pharmacy.getAddress());

        return Prescription.builder()
            .id(UUID.randomUUID().toString())
            .patientId(UUID.randomUUID().toString())
            .physicianId(UUID.randomUUID().toString())
            .medicationId(UUID.randomUUID().toString())
            .pharmacyId(pharmacy.getId()) // linking to full Pharmacy document
            .status("active")
            .pharmacy(embedded) // 💥 this is the key change!
            .createdDate(OffsetDateTime.now(ZoneOffset.UTC).minusDays(10))
            .updatedDate(OffsetDateTime.now(ZoneOffset.UTC))
            .build();
    }
    // */
    private void printActivePrescriptionsByLocation() {
        try {
            getActivePrescriptionsByLocation().stream().forEach(c -> {
                log.info("printActivePrescriptionsByLocation c {}", c);
            });
        } catch (Exception ex) {
            log.error(" Error printActivePrescriptionsByLocation", ex);
        }
    }
    
    private void printActivePresByLoc() {
        try {
            getActivePrescriptionsByLoc()
                .flatMap(list -> {
                    if (list.isEmpty()) {
                        return Mono.empty();
                    }
                    return Mono.just(list);
                })
                .doOnNext(dto -> log.info("1 printActivePresByLoc {}", dto))
                .switchIfEmpty(Mono.just("dummy")
                    .doOnNext(dummy -> log.info("2 printActivePresByLoc: No Data Found"))
                    .then(Mono.empty())
                )
                .switchIfEmpty(Mono.fromRunnable(() -> 
                    log.info("3. printActivePresByLoc: No Data Found")
                ))
                .subscribe();
        } catch (Exception ex) {
            log.error(" Error printActivePresByLoc", ex);
        }
    }    

    public List<PharmacyPrescriptionCount> getActivePrescriptionsByLocation() {
        CosmosAsyncDatabase database = asyncClient.getDatabase(dbProperties.getDatabase());
        CosmosAsyncContainer prescriptionContainer = database.getContainer("prescriptions");
        CosmosAsyncContainer pharmacyContainer = database.getContainer("pharmacies");

        // First get all active prescriptions grouped by pharmacyId
        String prescriptionQuery = "SELECT p.pharmacyId, COUNT(1) as count FROM p WHERE p.status = 'active' GROUP BY p.pharmacyId ";
        // Execute query and handle ArrayNode response
        Flux<GroupByResult> prescriptionCounts = prescriptionContainer
            .queryItems(prescriptionQuery, new CosmosQueryRequestOptions(), JsonNode.class)
            .byPage()
            .flatMap(page -> Flux.fromIterable(page.getResults()))
            .map(this::convertToGroupByResult);

        // Then get pharmacy details for each pharmacyId
        return prescriptionCounts.flatMap(group -> {
            String pharmacyQuery = String.format("SELECT * FROM ph WHERE ph.id = '%s'", group.getPharmacyId());
            return pharmacyContainer.queryItems(pharmacyQuery, new CosmosQueryRequestOptions(), Pharmacy.class)
                .single()
                .map(pharmacy -> new PharmacyPrescriptionCount(pharmacy, group.getCount()));
        }).collectList().block();
    }

    public Mono<List<PharmacyPrescriptionCount>> getActivePrescriptionsByLoc() {
        CosmosAsyncDatabase database = asyncClient.getDatabase(dbProperties.getDatabase());
        CosmosAsyncContainer prescriptionContainer = database.getContainer("prescriptions");
        CosmosAsyncContainer pharmacyContainer = database.getContainer("pharmacies");

        // Create parameterized query nd Add parameters
        SqlQuerySpec querySpec = new SqlQuerySpec("SELECT p.pharmacyId, COUNT(1) as count FROM p WHERE p.status = @status GROUP BY p.pharmacyId");
        querySpec.setParameters(Collections.singletonList(
            new SqlParameter("@status", "active")
        ));

        // Execute with explicit result type
        Flux<GroupByResult> prescriptionCounts = prescriptionContainer
            .queryItems(querySpec, new CosmosQueryRequestOptions(), JsonNode.class)
            .byPage()
            .flatMap(page -> Flux.fromIterable(page.getResults()))
            .map(this::convertToGroupByResult);

        // Rest of the processing remains the same
        return prescriptionCounts.flatMap(group -> {
            SqlQuerySpec pharmacyQuery = new SqlQuerySpec(
                "SELECT * FROM ph WHERE ph.id = @pharmacyId",
                Arrays.asList(new SqlParameter("@pharmacyId", group.getPharmacyId()))
            );
            return pharmacyContainer.queryItems(pharmacyQuery, new CosmosQueryRequestOptions(), Pharmacy.class)
                .single()
                .map(pharmacy -> new PharmacyPrescriptionCount(pharmacy, group.getCount()));
        })
        .collectList();
    }

    @Data
    @AllArgsConstructor
    public class GroupByResult {

        private String pharmacyId;
        private int count;
        // getters and setters
    }

    @Data
    @AllArgsConstructor
    public class PharmacyPrescriptionCount {

        private Pharmacy pharmacy;
        private int activePrescriptionCount;
        // constructor, getters
    }

    // Helper method to convert JsonNode to GroupByResult
    private GroupByResult convertToGroupByResult(JsonNode node) {
        // Handle both array format and object format responses
        if (node.isArray()) {
            // For GROUP BY array format: [pharmacyId, count]
            return new GroupByResult(node.get(0).asText(), node.get(1).asInt());
        } else {
            // For regular object format
            return new GroupByResult(
                node.get("pharmacyId").asText(),
                node.get("count").asInt()
            );
        }
    }
}
