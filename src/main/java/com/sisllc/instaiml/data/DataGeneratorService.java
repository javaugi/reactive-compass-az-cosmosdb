/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.data;

import com.azure.spring.data.cosmos.core.CosmosTemplate;
import com.azure.spring.data.cosmos.core.ReactiveCosmosTemplate;
import com.azure.spring.data.cosmos.repository.support.CosmosEntityInformation;
import com.sisllc.instaiml.model.ClaimsData;
import com.sisllc.instaiml.model.CoverageDetail;
import com.sisllc.instaiml.model.DrugInventory;
import com.sisllc.instaiml.model.GeographicPricing;
import com.sisllc.instaiml.model.InsuranceCompany;
import com.sisllc.instaiml.model.InsurancePlan;
import com.sisllc.instaiml.model.InsuranceProvider;
import com.sisllc.instaiml.model.Medication;
import com.sisllc.instaiml.model.Member;
import com.sisllc.instaiml.model.Patient;
import com.sisllc.instaiml.model.PatientMember;
import com.sisllc.instaiml.model.Pharmacy;
import com.sisllc.instaiml.model.Physician;
import com.sisllc.instaiml.model.PlanPricing;
import com.sisllc.instaiml.model.Prescription;
import com.sisllc.instaiml.model.User;
import com.sisllc.instaiml.repository.ClaimsDataRepository;
import com.sisllc.instaiml.repository.CoverageDetailRepository;
import com.sisllc.instaiml.repository.DrugInventoryRepository;
import com.sisllc.instaiml.repository.GeographicPricingRepository;
import com.sisllc.instaiml.repository.InsuranceCompanyRepository;
import com.sisllc.instaiml.repository.InsurancePlanRepository;
import com.sisllc.instaiml.repository.InsuranceProviderRepository;
import com.sisllc.instaiml.repository.MedicationRepository;
import com.sisllc.instaiml.repository.MemberRepository;
import com.sisllc.instaiml.repository.PatientMemberRepository;
import com.sisllc.instaiml.repository.PatientRepository;
import com.sisllc.instaiml.repository.PharmacyRepository;
import com.sisllc.instaiml.repository.PhysicianRepository;
import com.sisllc.instaiml.repository.PlanPricingRepository;
import com.sisllc.instaiml.repository.PrescriptionRepository;
import com.sisllc.instaiml.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.support.Repositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataGeneratorService extends DataGeneratorBase {

    private final CosmosTemplate cosmosTemplate;
    private final ReactiveCosmosTemplate reactiveCosmosTemplate; // For async
    private final ApplicationContext applicationContext;

    private static final List<String> unames = List.of("MyAdmin1@1", "MyAdmin1@2", "MyAdmin1@3", "MyAdmin1@4", "MyAdmin1@5");
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final PharmacyRepository pharmacyRepository;
    private final MedicationRepository medicationRepository;
    private final PatientRepository patientRepository;
    private final PhysicianRepository physicianRepository;
    private final DrugInventoryRepository drugInventoryRepository;
    private final PrescriptionRepository prescriptionRepository;

    List<User> users = new ArrayList<>();

    List<Pharmacy> pharmacies = new ArrayList<>();
    List<Medication> medications = new ArrayList<>();
    List<Patient> patients = new ArrayList<>();
    List<Physician> physicians = new ArrayList<>();
    List<DrugInventory> drugInventories = new ArrayList<>();
    List<Prescription> prescriptions = new ArrayList<>();

    private final InsuranceCompanyRepository insuranceCompanyRepository;
    private final InsuranceProviderRepository insuranceProviderRepository;
    private final MemberRepository memberRepository;
    private final PatientMemberRepository patientMemberRepository;
    private final InsurancePlanRepository insurancePlanRepository;
    private final PlanPricingRepository planPricingRepository;
    private final GeographicPricingRepository geographicPricingRepository;
    private final CoverageDetailRepository coverageDetailRepository;
    private final ClaimsDataRepository claimsDataRepository;

    List<InsuranceCompany> insuranceCompanies = new ArrayList<>();
    List<InsuranceProvider> insuranceProviders = new ArrayList<>();
    List<Member> members = new ArrayList<>();
    List<PatientMember> patientMembers = new ArrayList<>();
    List<InsurancePlan> insurancePlans = new ArrayList<>();
    List<PlanPricing> planPricings = new ArrayList<>();
    List<GeographicPricing> geographicPricings = new ArrayList<>();
    List<CoverageDetail> coverageDetails = new ArrayList<>();
    List<ClaimsData> claimsData = new ArrayList<>();

    public void seedData() {
        retrieveAllData();

        log.info("seedData users={}", users.size());
        if (users.isEmpty()) {
            generateUsers();
        }
        if (pharmacies.isEmpty()) {
            generatePharmacies();
        }
        if (medications.isEmpty()) {
            generateMedications();
        }
        if (patients.isEmpty()) {
            generatePatients();
        }
        if (physicians.isEmpty()) {
            generatePhysicians();
        }
        if (drugInventories.isEmpty()) {
            generateDrugInventory();
        }
        if (prescriptions.isEmpty()) {
            generatePrescriptions();
        }


        log.info("seedData insuranceCompanies={}", insuranceCompanies.size());
        if (insuranceCompanies.isEmpty()) {
            generateInsuranceCompanies();
        }
        if (insuranceProviders.isEmpty()) {
            generateInsuranceProviders();
        }
        if (insurancePlans.isEmpty()) {
            generateInsurancePlans();
        }
        if (members.isEmpty()) {
            generateMembers();
        }
        if (patientMembers.isEmpty()) {
            generatePatientMembers();
        }
        if (planPricings.isEmpty()) {
            generatePlanPricings();
        }
        if (geographicPricings.isEmpty()) {
            generateGeographicPricings();
        }
        if (coverageDetails.isEmpty()) {
            generateCoverageDetails();
        }
        if (claimsData.isEmpty()) {
            generateClaimsData();
        }
        log.info("Done seedData patients count={}", cosmosTemplate.count("patients"));
    }

    private void retrieveAllData() {
        this.users = this.userRepository.findAll().collectList().block();

        this.pharmacies = this.pharmacyRepository.findAll().collectList().block();
        this.medications = this.medicationRepository.findAll().collectList().block();
        this.patients = this.patientRepository.findAll().collectList().block();
        this.physicians = this.physicianRepository.findAll().collectList().block();
        this.drugInventories = this.drugInventoryRepository.findAll().collectList().block();
        this.prescriptions = this.prescriptionRepository.findAll().collectList().block();

        this.insuranceCompanies = this.insuranceCompanyRepository.findAll().collectList().block();
        this.insuranceProviders = this.insuranceProviderRepository.findAll().collectList().block();
        this.members = this.memberRepository.findAll().collectList().block();
        this.patientMembers = this.patientMemberRepository.findAll().collectList().block();
        this.insurancePlans = this.insurancePlanRepository.findAll().collectList().block();
        this.planPricings = this.planPricingRepository.findAll().collectList().block();
        this.geographicPricings = this.geographicPricingRepository.findAll().collectList().block();
        this.coverageDetails = this.coverageDetailRepository.findAll().collectList().block();
        this.claimsData = this.claimsDataRepository.findAll().collectList().block();
    }

    private String getUPWD(int i) {
        if (i < unames.size()) {
            return unames.get(i);
        }

        return FAKER.name().username();
    }

    private void generateUsers() {
        // 1. Generate 50 fake patients
        users = IntStream.range(0, 50)
            .mapToObj(i -> UserGenerator.generate(getUPWD(i), passwordEncoder))
            .collect(Collectors.toList());

        userRepository.saveAll(users)
            .blockLast(); //wait for completion
    }

    private void generatePharmacies() {
        // 1. Generate 50 fake patients
        pharmacies = IntStream.range(0, 50)
            .mapToObj(i -> PharmacyGenerator.generate())
            .collect(Collectors.toList());

        pharmacyRepository.saveAll(pharmacies)
            .blockLast(); //wait for completion
    }

    private void generateMedications() {
        medications = IntStream.range(0, 50)
            .mapToObj(i -> MedicationGenerator.generate())
            .collect(Collectors.toList());

        medicationRepository.saveAll(medications)
            .blockLast(); //wait for completion
    }

    private void generatePatients() {
        patients = IntStream.range(0, 50)
            .mapToObj(i -> PatientGenerator.generate())
            .collect(Collectors.toList());

        patientRepository.saveAll(patients)
            .blockLast(); //wait for completion
    }

    private void generatePhysicians() {
        physicians = IntStream.range(0, 50)
            .mapToObj(i -> PhysicianGenerator.generate())
            .collect(Collectors.toList());

        physicianRepository.saveAll(physicians)
            .blockLast(); //wait for completion
    }

    private void generateDrugInventory() {
        drugInventories = IntStream.range(0, 50)
            .mapToObj(i -> DrugInventoryGenerator.generate(
                pharmacies.get(rand.nextInt(pharmacies.size())).getId(),
                medications.get(rand.nextInt(medications.size())).getId())
            )
            .collect(Collectors.toList());

        drugInventoryRepository.saveAll(drugInventories)
            .blockLast(); //wait for completion
    }

    private void generatePrescriptions() {
        prescriptions = IntStream.range(0, 50)
            .mapToObj(i -> PrescriptionGenerator.generate(
                patients.get(rand.nextInt(patients.size())).getId(),
                physicians.get(rand.nextInt(physicians.size())).getId(),
                medications.get(rand.nextInt(medications.size())).getId(),
                pharmacies.get(rand.nextInt(pharmacies.size())).getId())
            )
            .collect(Collectors.toList());

        prescriptionRepository.saveAll(Flux.fromIterable(prescriptions))
            .blockLast(); //wait for completion
    }

    protected void addPrescriptionsByPatients(List<Patient> patients) {
        //  Generate prescriptions for each patient (async example)
        CosmosEntityInformation<Prescription, String> entityInfo = getCosmosEntityInfo(Prescription.class);
        Flux.fromIterable(patients)
            .flatMap(patient -> {
                List<Prescription> allList = IntStream.range(0, 10)
                    .mapToObj(i -> PrescriptionGenerator.generate(patient.getId(),
                        physicians.get(rand.nextInt(physicians.size())).getId(),
                        medications.get(rand.nextInt(medications.size())).getId(),
                        pharmacies.get(rand.nextInt(pharmacies.size())).getId())
                    )
                    .collect(Collectors.toList());

                return reactiveCosmosTemplate.insertAll(entityInfo, allList);
            })
            .blockLast(); // Wait for completion

    }

    private <T, String> CosmosEntityInformation<T, String> getCosmosEntityInfo(Class<T> entityClass) {
        Repositories repos = new Repositories(applicationContext);
        return (CosmosEntityInformation<T, String>) repos.getEntityInformationFor(entityClass);
    }

    private void generateInsuranceCompanies() {
        insuranceCompanies = IntStream.range(0, 50)
            .mapToObj(i -> InsuranceCompanyGenerator.generate())
            .collect(Collectors.toList());
        insuranceCompanyRepository.saveAll(Flux.fromIterable(insuranceCompanies))
            .blockLast(); //wait for completion
    }

    private void generateInsuranceProviders() {
        insuranceProviders = IntStream.range(0, 50)
            .mapToObj(i -> InsuranceProviderGenerator.generate())
            .collect(Collectors.toList());
        insuranceProviderRepository.saveAll(Flux.fromIterable(insuranceProviders))
            .blockLast(); //wait for completion
    }

    private void generateInsurancePlans() {
        insurancePlans = IntStream.range(0, 50)
            .mapToObj(i -> InsurancePlanGenerator.generate(
                insuranceCompanies.get(rand.nextInt(insuranceCompanies.size())).getId())
            )
            .collect(Collectors.toList());
        insurancePlanRepository.saveAll(Flux.fromIterable(insurancePlans))
            .blockLast(); //wait for completion
    }

    private void generateMembers() {
        members = IntStream.range(0, 50)
            .mapToObj(i -> MemberGenerator.generate(
                insurancePlans.get(rand.nextInt(insurancePlans.size())).getId())
            )
            .collect(Collectors.toList());
        memberRepository.saveAll(Flux.fromIterable(members))
            .blockLast(); //wait for completion
    }

    private void generatePatientMembers() {
        this.patientMembers = IntStream.range(0, 50)
            .mapToObj(i -> PatientMemberGenerator.generate(
                patients.get(rand.nextInt(patients.size())).getId(),
                members.get(rand.nextInt(members.size())).getId())
            )
            .collect(Collectors.toList());
        patientMemberRepository.saveAll(Flux.fromIterable(patientMembers))
            .blockLast(); //wait for completion
    }

    private void generatePlanPricings() {
        this.planPricings = IntStream.range(0, 50)
            .mapToObj(i -> PlanPricingGenerator.generate(
                insurancePlans.get(rand.nextInt(insurancePlans.size())).getId()))
            .collect(Collectors.toList());
        planPricingRepository.saveAll(Flux.fromIterable(this.planPricings))
            .blockLast(); //wait for completion
    }

    private void generateGeographicPricings() {
        this.geographicPricings= IntStream.range(0, 50)
            .mapToObj(i -> GeographicPricingGenerator.generate(
                insurancePlans.get(rand.nextInt(insurancePlans.size())).getId()))
            .collect(Collectors.toList());
        this.geographicPricingRepository.saveAll(Flux.fromIterable(this.geographicPricings))
            .blockLast(); //wait for completion
    }

    private void generateCoverageDetails() {
        this.coverageDetails = IntStream.range(0, 50)
            .mapToObj(i -> CoverageDetailGenerator.generate(
                insurancePlans.get(rand.nextInt(insurancePlans.size())).getId())
            )
            .collect(Collectors.toList());
        coverageDetailRepository.saveAll(Flux.fromIterable(coverageDetails))
            .blockLast(); //wait for completion
    }

    private void generateClaimsData() {
        this.claimsData = IntStream.range(0, 50)
            .mapToObj(i -> ClaimsDataGenerator.generate(
                members.get(rand.nextInt(members.size())).getId(),
                insuranceProviders.get(rand.nextInt(insuranceProviders.size())).getId(),
                insurancePlans.get(rand.nextInt(insurancePlans.size())).getId())
            )
            .collect(Collectors.toList());
        claimsDataRepository.saveAll(Flux.fromIterable(claimsData))
            .blockLast(); //wait for completion
    }
}
