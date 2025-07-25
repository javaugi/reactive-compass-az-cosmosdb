package com.sisllc.instaiml.config;

import com.azure.cosmos.ConsistencyLevel;
import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;

import com.azure.spring.data.cosmos.config.AbstractCosmosConfiguration;
import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;
import com.azure.spring.data.cosmos.repository.config.EnableReactiveCosmosRepositories;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableCosmosRepositories(basePackages = CosmosDBConfig.PACKAGE_SCAN_BASE_REPO)
@EnableReactiveCosmosRepositories(basePackages = CosmosDBConfig.PACKAGE_SCAN_BASE_REPO)
public class CosmosDBConfig extends AbstractCosmosConfiguration {

    protected static final String PACKAGE_SCAN_BASE_REPO = "com.sisllc.instaiml.repository";

    @Autowired
    private CosmosDBProperties cosmosDbProperties;

    @Override
    public String getDatabaseName() {
        return cosmosDbProperties.getDatabase(); // Get database separately
    }

    @Bean
    public CosmosClientBuilder cosmosClientBuilder() {
        String connectionString = cosmosDbProperties.getConnectionString();
        System.out.println("CosmosDBConfig connectionString=" + connectionString);

        if (connectionString == null || connectionString.isEmpty()) {
            throw new IllegalArgumentException("Azure Cosmos DB connection string is missing!");
        }

        String endPoint = getValueFromConnectionString(connectionString, "AccountEndpoint");
        String key = getValueFromConnectionString(connectionString, "AccountKey");

        return new CosmosClientBuilder()
            .endpoint(endPoint)
            .key(key);
    }

    public SSLContext getSSLContext() {
        SSLContext sslContext = null;
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };

            // Configure the Cosmos client to use this trust manager
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (KeyManagementException | NoSuchAlgorithmException ex) {
            log.error("Error getSSLContext", ex);
            try {
                sslContext = SSLContext.getDefault();
            } catch (NoSuchAlgorithmException e) {
                log.error("Error getSSLContext getDefault", e);
            }
        }

        return sslContext;
    }

    @Bean
    public CosmosClient cosmosClient(CosmosClientBuilder cosmosClientBuilder) {
        return cosmosClientBuilder
            .consistencyLevel(ConsistencyLevel.SESSION)
            .contentResponseOnWriteEnabled(true)
            .buildClient();
    }

    @Override
    @Bean
    public CosmosAsyncClient cosmosAsyncClient(CosmosClientBuilder cosmosClientBuilder) {
        return cosmosClientBuilder
            .consistencyLevel(ConsistencyLevel.SESSION)
            .contentResponseOnWriteEnabled(true)
            .buildAsyncClient();
    }

    // Helper method to extract values from the connection string
    private String getValueFromConnectionString(String connectionString, String key) {
        for (String part : connectionString.split(";")) {
            if (part.startsWith(key + "=")) {
                return part.split("=")[1];
            }
        }
        throw new IllegalArgumentException("Missing " + key + " in connection string");
    }

}

/*
Recommended: import Cosmos DB emulator certificate into the JVM's cacerts
(1) Step 1 - export the emulator's certificate 
    Way 1: Starts up the emulator -> https://localhost:8081/_explorer/index.html using Chrome browser -> click the left end lock icon of the address bar 
    -> the connection is secure -> the certificate is valid -> view the certificate -> Details tab -> Export -> comos-emulator.cer or crt file
    Way 2: Type cert at Windows seach -> Manage Computer Certificate -> Personal -> Certificate -> right click DocumentDBEmulatorCertificate -> All
        tasks - Export -> Do not export private key -> Base 64 -> comos-emulator.cer or crt file

    Step 2: Import the certificate into JDK cert store
        set JAVA_HOME="C:\Program Files\Java\jdk-21"
        %JAVA_HOME%\bin\keytool.exe -importcert -trustcacerts -alias cosmosdb-emulator -file C:\Users\javau\dev\powershell\win_cosmosdb_emulator.cer -keystore %JAVA_HOME%\lib\security\cacerts -storepass changeit

(2) Disable certificate validation: You can disable certificate validation by setting the ssl property to false or by using a custom SSLContext that trusts all certificates. However, this approach is not recommended for production environments.
    System.setProperty("javax.net.ssl.trustStore", "path/to/truststore");
    System.setProperty("javax.net.ssl.trustStorePassword", "password");

    Or, using a custom SSLContext:
  

    SSLContext sslContext = SSLContext.getInstance("TLS");
    sslContext.init(null, new TrustManager[]{new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }}, null);

    CosmosClientBuilder builder = new CosmosClientBuilder()
        .endpoint("https://localhost:8081/")
        .key("your_key")
        .sslContext(sslContext);

(3) Add the emulator's certificate to the truststore: You can export the emulator's certificate and add it to the Java truststore. This approach 
    is more secure than disabling certificate validation.
You can export the certificate from the emulator and import it into the Java truststore using the following steps:
Export the certificate from the emulator: You can do this by browsing to https://localhost:8081/_explorer/index.html in your browser, clicking on the lock icon in the address bar, and exporting the certificate.
Import the certificate into the Java truststore: You can use the keytool command to import the certificate into the truststore.

keytool -importcert -file path/to/certificate.cer -keystore path/to/truststore.jks -storepass password
After importing the certificate, you can configure your Java application to use the truststore:
    System.setCertificateType("JKS");
    System.setProperty("javax.net.ssl.trustStore", "path/to/truststore");
    System.setProperty("javax.net.ssl.trustStorePassword", "password");
By using one of these approaches, you should be able to resolve the PKIX path validation failed error and connect to the Azure Cosmos DB Emulator using Java.
 */
