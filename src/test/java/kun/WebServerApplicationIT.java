package kun;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Run integration tests against the server and the deployed application.
 */
@RunAsClient
@RunWith(Arquillian.class)
public class WebServerApplicationIT {

    @Test
    public void testHelloEndpoint() {
        try (Client client = ClientBuilder.newClient()) {
            Response response = client
                    .target(URI.create("http://localhost:8080/"))
                    .path("/share/hello/World")
                    .request()
                    .get();

            assertEquals(200, response.getStatus());
            assertEquals("Hello World.", response.readEntity(String.class));

        }
    }
}
