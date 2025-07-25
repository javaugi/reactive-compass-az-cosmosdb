Azure Cosmos DB supports multiple APIs, each designed to match a different data model and query language. Here's a breakdown of the Core SQL API, 
    MongoDB API, Table API, and Gremlin API, along with concrete examples for each.

🌐 1. Cosmos DB Core SQL API
✅ Purpose:
Primary and most feature-rich API of Cosmos DB.
Uses SQL-like syntax to query JSON documents.
Best for document-oriented data (e.g., customer records, product catalogs).

✅ Features:
Native support for indexing and querying JSON.
Supports rich querying, stored procedures, triggers, UDFs.

📘 Example:
Stored document:
{
  "id": "1",
  "name": "Alice",
  "city": "New York",
  "orders": 5
}
SQL Query:
SELECT c.name, c.orders FROM c WHERE c.city = "New York"
Use Case: Applications needing complex querying over JSON data (e.g., e-commerce, IoT).

🍃 2. Cosmos DB MongoDB API
✅ Purpose:
Emulates the MongoDB wire protocol.
Allows existing MongoDB apps to work without code changes.
Best for teams migrating from or using MongoDB tools and drivers.
✅ Features:
Supports MongoDB clients (like Compass, Mongoose, pymongo).
Schema-less document storage.
Compatibility varies by API version (3.6 to 6.0+ supported).
📘 Example (Node.js using Mongoose):
const userSchema = new mongoose.Schema({
  name: String,
  city: String,
  orders: Number
});

const User = mongoose.model('User', userSchema);
User.find({ city: 'New York' }).select('name orders');
Use Case: MongoDB apps with little or no code changes, rapid prototyping, JSON data.

📦 3. Cosmos DB Table API
✅ Purpose:
Compatible with Azure Table Storage.
Ideal for storing sparse, semi-structured key-value data.

We’ll use the azure-data-tables SDK.
Cosmos DB Table API stores entities using a PartitionKey and RowKey.
You interact with it like a NoSQL key-value store (similar to DynamoDB or HBase).
✅ Features:
Uses partition key and row key.
Scalable, simple CRUD interface.
Works well with existing Azure Storage SDKs.

📘 Example 
import com.azure.data.tables.models.TableEntity;

public class CustomerEntity extends TableEntity {

    public CustomerEntity(String partitionKey, String rowKey) {
        super(partitionKey, rowKey);
    }

    public CustomerEntity() {}

    public String getName() {
        return getProperty("Name");
    }

    public void setName(String name) {
        setProperty("Name", name);
    }

    public String getEmail() {
        return getProperty("Email");
    }

    public void setEmail(String email) {
        setProperty("Email", email);
    }
}

🕸️ 4. Cosmos DB Gremlin API
✅ Purpose:
Stores and queries graph data.
Uses Gremlin (Apache TinkerPop standard).
Best for relationship-heavy scenarios (social graphs, fraud detection).

✅ Features:
Supports vertices and edges.
Traversal-based querying.
Graph-aware indexing and partitioning.

📘 Example (Gremlin traversal):
Graph:
Vertex: Alice
Vertex: Bob
Edge: knows

g.V().has('name', 'Alice').out('knows').values('name')

This finds all people Alice knows.
Use Case: Social networks, recommendation engines, fraud detection.

🔄 Comparison Summary
Feature                 SQL API                     MongoDB API                     Table API                       Gremlin API
Data Model              JSON documents              JSON documents (MongoDB-style)	Key-value (NoSQL table)         Graph (vertices & edges)
Query Language          SQL-like                    MongoDB Query Language          LINQ / Table SDK                Gremlin
Compatibility           Native to Cosmos DB         MongoDB clients (e.g. Compass)	Azure Table Storage compatible	Apache TinkerPop
Best Use Case           Complex JSON querying       MongoDB app migrations          Sparse key-value data           Relationship-heavy data
Indexing                Automatic on all fields     Partial support by version      Limited                         Optimized for graph queries
Triggers/Stored Procs	Yes                         No                              No                              No


Tips for Complex JSON Querying in Cosmos DB
Use Case                                Technique
Filter by nested object fields          u.address.city = "X"
Query array elements                    Use JOIN x IN u.array
Select nested arrays or props           Use dot notation (u.orders[0].orderId) or JOIN
Mixed filter and projection             SELECT u.name, o.total WHERE o.total > 100

✅ Complex Query Example
You can still run SQL-like queries using the repository or CosmosTemplate.

Example: Find all members who use tobacco and enrolled after a certain date:

SELECT * FROM c
WHERE c.tobaccoUser = true AND c.enrollmentDate > "2022-01-01T00:00:00Z"

In Java, using CosmosTemplate:

String query = """
    SELECT * FROM c
    WHERE c.tobaccoUser = true AND c.enrollmentDate > @date
    """;

SqlParameterList params = new SqlParameterList();
params.add(new SqlParameter("@date", OffsetDateTime.parse("2022-01-01T00:00:00Z")));

CosmosQueryRequestOptions options = new CosmosQueryRequestOptions();

Flux<Member> members = cosmosTemplate.find(query, params, options, Member.class);

This works because your class fields (tobaccoUser, enrollmentDate, etc.) match the field names in Cosmos DB JSON.

🔁 When Do You Need @JsonProperty?
Only when:
You want to map a Java field to a different JSON field name.
You're using Jackson directly for (de)serialization.
You’re using Map<String, Object> instead of a strongly typed class.

Example:

@JsonProperty("full_name")
private String name;

✅ Summary
Annotation          Needed?             Purpose
@Id                 ✅ Yes               Maps to id in Cosmos DB JSON
@PartitionKey       ✅ Yes               Required for partitioned access
@JsonProperty       ❌ No                Only needed if JSON field name differs
@Container          ✅ Yes               Maps the class to the Cosmos DB container

So, your setup is good, and you're free to do complex queries as-is.


❓ Is @Column(name = "full_name") valid for Azure Cosmos DB?
❌ No. @Column is not used or recognized by Azure Cosmos DB or Spring Data for Azure Cosmos.

@Column is part of JPA/Hibernate (for relational databases like PostgreSQL, MySQL).
Cosmos DB is NoSQL, and its mapping system uses different annotations:
@Id, @PartitionKey, @Container for entity mapping.

Optionally, @JsonProperty for JSON field name control.

✅ Use @JsonProperty("full_name") if needed
Use @JsonProperty("full_name") only if the JSON field name is different from your Java field name.

🔍 Example:
If Cosmos DB stores:

{
  "id": "123",
  "full_name": "Alice"
}
You can map it in Java like this:

public class Member {
    @Id
    private String id;

    @JsonProperty("full_name")
    private String name;
}
This maps the JSON field "full_name" to the Java field name.

✅ Summary Table
Annotation      Use in Cosmos DB?    Purpose
@Id             ✅ Yes               Maps to the id field in Cosmos DB JSON
@PartitionKey	✅ Yes               Marks the partition key
@Container      ✅ Yes               Maps the entity to a Cosmos container
@JsonProperty	✅ Yes (optional)    Maps mismatched JSON field names
@Column         ❌ No                Only used in JPA (relational databases)

🟢 TL;DR:
✅ Use @JsonProperty("json_name") when your Java field name ≠ JSON field name.
❌ Do not use @Column with Cosmos DB — it’s for SQL/JPA.
✅ If field names match, you don’t need any annotation — default Jackson + Spring Data Cosmos behavior will handle it.


Here’s how to (1) auto-map nested JSON into Java POJOs with Spring Data Azure Cosmos, (2) globally control snake_case ↔ camelCase naming with 
    Jackson, and (3) write Cosmos SQL queries when your Java fields use @JsonProperty("first_name").

1. Auto-Mapping Nested JSON Structures
If the JSON stored in Cosmos DB contains nested objects/arrays, just model them as nested POJO types in your entity. Jackson + Spring 
    Data Cosmos will deserialize automatically as long as field names line up (or you use @JsonProperty).

Example JSON in Cosmos
{
  "id": "m-1001",
  "insurancePlanId": "plan-abc",
  "first_name": "Alice",
  "demographics": {
    "gender": "F",
    "birth_date": "1985-03-19T00:00:00Z"
  },
  "addresses": [
    { "type": "home", "line1": "123 Main", "city": "Austin", "state": "TX" },
    { "type": "mailing", "line1": "PO Box 88", "city": "Austin", "state": "TX" }
  ]
}

@Container(containerName = "members")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    private String id;

    @PartitionKey
    private String insurancePlanId;

    @JsonProperty("first_name")
    private String firstName;

    private Demographics demographics;          // nested object
    private List<Address> addresses;            // nested array

    @Data
    public static class Demographics {
        private String gender;

        @JsonProperty("birth_date")
        private OffsetDateTime birthDate;
    }

    @Data
    public static class Address {
        private String type;
        private String line1;
        private String city;
        private String state;
    }
}
As long as the JSON names match (directly or via @JsonProperty), Jackson will populate the nested fields automatically.

2. Global snake_case ↔ camelCase Naming Strategy
Instead of sprinkling @JsonProperty everywhere, you can tell Jackson to expect snake_case in JSON and map to camelCase in Java.

Option A – application.yml

spring:
  jackson:
    property-naming-strategy: SNAKE_CASE

With this in place:
    JSON first_name → Java firstName
    JSON birth_date → Java birthDate
    JSON insurance_plan_id → Java insurancePlanId

You can then remove most @JsonProperty annotations (keep only for exceptions).

Option B – Custom ObjectMapper Bean
Useful if you need a dedicated mapper for Cosmos serialization (e.g., you’re doing manual reads/writes or advanced config).

@Bean
public ObjectMapper cosmosObjectMapper() {
    return new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
}
If you need Cosmos SDK to use a custom mapper, use CosmosClientBuilder#contentResponseOnWriteEnabled(true) and CosmosClientBuilder#endpointDiscoveryEnabled(true) 
    as needed, then wire in serialization config via JacksonAdapter (advanced; most apps rely on default Spring Boot Jackson config, which works fine when 
    using Spring Data Cosmos abstractions).

3. Querying When Using @JsonProperty("first_name")
Cosmos DB SQL queries operate on the JSON as stored in the container, not your Java field names.
So if the document field is first_name, you must query c.first_name, even though your Java field is firstName.

Example Query

SELECT * FROM c WHERE c.first_name = @fn

String sql = "SELECT * FROM c WHERE c.first_name = @fn";
SqlParameterList params = new SqlParameterList(new SqlParameter("@fn", "Alice"));
Flux<Member> results = cosmosTemplate.find(sql, params, new CosmosQueryRequestOptions(), Member.class);

Jackson then maps the returned JSON first_name → Java firstName (via @JsonProperty or global snake_case).

Using Projection / Alias in Query
You can alias fields in Cosmos SQL. Helpful if you want the query result JSON to use camelCase without relying on @JsonProperty.

SELECT c.id,
       c.insurancePlanId,
       c.first_name AS firstName
FROM c
WHERE c.first_name = @fn

In this case, the result JSON has "firstName" so you wouldn’t need @JsonProperty (but be consistent—if you later SELECT *, you’ll get the raw snake_case again).

Best practice: Query with SELECT * most of the time and rely on naming strategy or @JsonProperty for stable mapping.

4. Mixed Strategy Recommendation
Scenario                                                Recommended Approach
Small model, a few renamed fields                       Use @JsonProperty on those fields
Large domain, snake_case data model                     Configure global SNAKE_CASE; drop most @JsonProperty
You control writes; want camelCase in Cosmos            Store camelCase JSON; no mapping needed
Legacy snake_case container; want camelCase POJOs       Global SNAKE_CASE is easiest

5. Quick Demo: Removing @JsonProperty Once Strategy Is Global
After enabling global snake_case:

@Container(containerName = "members")
@Data
public class Member {
    @Id
    private String id;

    @PartitionKey
    private String insurancePlanId;

    private String firstName;  // maps from 'first_name' JSON automatically
    private Demographics demographics;
    private List<Address> addresses;

    @Data
    public static class Demographics {
        private String gender;
        private OffsetDateTime birthDate; // 'birth_date'
    }

    @Data
    public static class Address {
        private String type;
        private String line1;
        private String city;
        private String state;
    }
}
6. Gotchas
Issue                               Cause                                   Fix
Field null after read               JSON name mismatch                      Add @JsonProperty or naming strategy
Date parsing error                  OffsetDateTime format                   Register JavaTimeModule + ISO 8601 strings
Query returns but mapping fails     Projection fields don’t match POJO      Use SELECT * or DTO with matching shape

if I don't config global SNAKE_CASE strategy or use @JsonProperty I can use  insurancePlanId, firstname, lastname etc in the queries directly, 
    right? if this is true it is prefered by me

ChatGPT said:
✅ Yes — you're absolutely correct, and that’s a perfectly valid and often simpler approach!

🔹 If You Don't Use @JsonProperty or Global SNAKE_CASE, Then:
The field names in your Java class (e.g., insurancePlanId, firstName) are used directly as JSON keys in Cosmos DB.

You can query using those exact camelCase names in SQL queries.

✅ Example

@Container(containerName = "members")
@Data
public class Member {
    @Id
    private String id;

    @PartitionKey
    private String insurancePlanId;

    private String firstName;
    private String lastName;
    private boolean tobaccoUser;
}

Stored JSON in Cosmos DB:
{
  "id": "123",
  "insurancePlanId": "planA",
  "firstName": "Alice",
  "lastName": "Smith",
  "tobaccoUser": false
}

Cosmos DB SQL Query:
SELECT * FROM c
WHERE c.insurancePlanId = "planA" AND c.firstName = "Alice"
Everything maps directly — no @JsonProperty, no global config, no guesswork.

🔹 ✅ When This Approach is Preferred
You control the data model in Cosmos DB.
You want simple, 1:1 mapping between Java fields and stored JSON.
You want to avoid the complexity of @JsonProperty or naming strategies.

🧠 TL;DR
Do You Use                              Can You Query with camelCase (c.firstName)      Mapping Notes
No @JsonProperty, no naming strategy	✅ Yes                                          JSON will store camelCase as-is
@JsonProperty("first_name")             ❌ No → must query c.first_name                 JSON uses snake_case
Global SNAKE_CASE strategy              ❌ No → must query c.first_name	Maps            JSON snake_case to Java camelCase