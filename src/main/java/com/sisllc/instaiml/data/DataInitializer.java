/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.data;

import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.models.CosmosContainerProperties;
import com.azure.cosmos.models.IncludedPath;
import com.azure.cosmos.models.IndexingMode;
import com.azure.cosmos.models.IndexingPolicy;
import com.azure.cosmos.models.ThroughputProperties;
import com.sisllc.instaiml.service.InsurancePricingAnalyticalService;
import com.sisllc.instaiml.service.PrescriptionAnalyticalService;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
@Profile({"default", "dev", "test", "qa", "snapshot", "staging"})
public class DataInitializer {
    
    //private final CosmosTemplate cosmosTemplate;
    //private final ReactiveCosmosTemplate reactiveCosmosTemplate; // For async
    private final CosmosClient cosmosClient;
    private final DataGeneratorService dataGenService;
    private final InsurancePricingAnalyticalService anylyticalService;
    private final PrescriptionAnalyticalService presAnalyticalService;
    
    @Value("${azure.cosmos.database}")
    private String databaseName;
       
    @EventListener(ApplicationReadyEvent.class)
    public void initContainers() {
        log.info("initContainers ... databaseName={}",databaseName);
        createContainers();
        
        dataGenService.seedData();
        log.info("initContainers seedData Done.");
        anylyticalService.performAnalytics();
        log.info("initContainers anylyticalService.performAnalytics Done.");
        presAnalyticalService.performAnalytics();
        log.info("All Done");
    }
    
    public void createContainers() {        
        cosmosClient.createDatabaseIfNotExists(databaseName);

        // Create containers
        createContainerIfNotExists("users", "/id");

        createContainerIfNotExists("drugInventories", "/medicationId");
        createContainerIfNotExists("medications", "/name");
        createContainerIfNotExists("patients", "/name");
        createContainerIfNotExists("physicians", "/name");
        createContainerIfNotExists("pharmacies", "/pharmacyCode");
        createContainerIfNotExists("prescriptions", "/patientId");
        
        createContainerIfNotExists("claimsData", "/memberId");
        createContainerIfNotExists("coverageDetails", "/insurancePlanId");
        createContainerIfNotExists("geographicPricings", "/insurancePlanId");
        createContainerIfNotExists("insuranceCompanies", "/companyCode");
        createContainerIfNotExists("insurancePlans", "/insuranceCompanyId");
        createContainerIfNotExists("insuranceProviders", "/providerName");
        createContainerIfNotExists("members", "/insurancePlanId");
        createContainerIfNotExists("planPricings", "/insurancePlanId");
        createContainerIfNotExists("patientMembers", "/id");
    }    
    
    private void createContainerIfNotExists(String containerName, String partitionKeyPath) {
        log.info("createContainerIfNotExists ... containerName={}, partitionKeyPath={}",containerName, partitionKeyPath);
        CosmosContainerProperties containerProperties = 
            new CosmosContainerProperties(containerName, partitionKeyPath);
        
        // Optional: Configure indexing policy
        IndexingPolicy indexingPolicy = new IndexingPolicy();
        indexingPolicy.setAutomatic(true);
        indexingPolicy.setIndexingMode(IndexingMode.CONSISTENT);
        indexingPolicy.setIncludedPaths(Arrays.asList(new IncludedPath("/*")));
        containerProperties.setIndexingPolicy(indexingPolicy);

        // Set throughput (400 RU/s minimum)
        ThroughputProperties throughputProperties = 
            ThroughputProperties.createManualThroughput(400);

        cosmosClient.getDatabase(databaseName)
            .createContainerIfNotExists(containerProperties, throughputProperties);
        log.info("Done createContainerIfNotExists for containerName={}",containerName);
    }    
    
}
