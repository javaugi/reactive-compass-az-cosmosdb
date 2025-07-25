clienttelemetry.ClientTelemetry : Unable to get azure vm metadata
io.netty.channel.AbstractChannel$AnnotatedSocketException: Network is unreachable: getsockopt: /169.254.169.254:80
Caused by: java.net.SocketException: Network is unreachable: getsockopt
means that your application is trying to retrieve Azure VM metadata, but it can't reach the Azure Instance Metadata Service (IMDS) at the IP 
address 169.254.169.254, which is a link-local address only available from within an Azure virtual machine.

📌 Why this happens
The IP 169.254.169.254 is used by Azure to expose metadata about the current VM (e.g., region, instance ID, etc.) to code running inside the VM.

If your application:
    Is running outside Azure (e.g., on your local dev machine, in Docker, or on-premises), or
    Is running in Azure but in a restricted network (e.g., no access to IMDS or NSG blocks it),

you’ll get this “Network is unreachable” error.

✅ When is this safe to ignore?
    This often comes from the Azure SDK telemetry trying to auto-detect environment details (e.g., for logging, metrics, tracing). If:
    You’re not using any Azure-specific features that depend on the metadata (like Managed Identity), and
    The rest of your application works fine,
    then it’s safe to ignore or suppress this warning.

🛠️ How to fix or suppress
Option 1: Run inside Azure VM (if needed)
If your code depends on the Azure VM metadata, you must run it inside an Azure VM or container with access to 169.254.169.254.

Option 2: Disable telemetry (if not needed)
Some Azure SDKs allow disabling telemetry to prevent such network calls:

AzureClientBuilder clientBuilder = new AzureClientBuilder()
    .httpLogOptions(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.NONE))
    .clientOptions(new ClientOptions().setApplicationId("your-app-id"))
    .retryPolicy(new RetryPolicy())
    .addPolicy(new UserAgentPolicy(null, null, null)); // custom policy if needed

Or set the environment variable to disable telemetry:

AZURE_CLIENT_TELEMETRY_DISABLED=true

Or in Java:
System.setProperty("AZURE_CLIENT_TELEMETRY_DISABLED", "true");

Option 3: Ignore in logs
If you’re using this during local development, you can filter or suppress this log message in your logging configuration (e.g., logback.xml, log4j.properties, etc.).

✅ Summary
    Cause: SDK tries to reach Azure metadata endpoint from a non-Azure environment.
    Impact: Usually harmless unless your app depends on metadata (e.g., region detection, MSI).
    Solution:
        Disable telemetry (AZURE_CLIENT_TELEMETRY_DISABLED=true), or
        Run inside Azure if metadata is required, or I
        gnore in logs during local dev/test.
