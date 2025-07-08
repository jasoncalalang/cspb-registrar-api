package ph.edu.cspb.registrar;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Assumptions;
import au.com.dius.pact.provider.junitsupport.IgnoreNoPactsToVerify;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
            "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
            "spring.jpa.hibernate.ddl-auto=create-drop",
            "spring.flyway.enabled=false"
        })
@Provider("registrar-api")
@PactFolder("pacts")
@IgnoreNoPactsToVerify
class ContractVerificationTest {

    @LocalServerPort
    Integer port;

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    @ExtendWith(PactVerificationSpringProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) throws IOException {
        boolean hasPacts = Files.list(Paths.get("src/test/resources/pacts"))
                .anyMatch(p -> p.toString().endsWith(".json"));
        Assumptions.assumeTrue(hasPacts, "No pact files found");
        context.setTarget(new HttpTestTarget("localhost", port));
        context.verifyInteraction();
    }
}
