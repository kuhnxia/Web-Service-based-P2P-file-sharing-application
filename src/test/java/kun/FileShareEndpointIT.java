package kun;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Form;
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
public class FileShareEndpointIT {

    private static final String BASE_URI = "http://localhost:8080/share";

    @Test
    public void testSayHelloEndpoint() {
        try (Client client = ClientBuilder.newClient()) {
            Response response = client
                    .target(URI.create(BASE_URI))
                    .path("/World")
                    .request()
                    .get();

            assertEquals(200, response.getStatus());
            assertEquals("Hello 'World'.", response.readEntity(String.class));
        }
    }

    @Test
    public void testRegisterFileEndpoint() {
        try (Client client = ClientBuilder.newClient()) {
            Form form = new Form();
            form.param("fileName", "test.jpg");
            form.param("ipAddress", "192.168.0.99");
            form.param("port", "7070");

            Response response = client
                    .target(URI.create(BASE_URI))
                    .path("/registerFile")
                    .request()
                    .post(Entity.form(form));

            assertEquals(200, response.getStatus());
            // Add additional assertions based on your service behavior
        }
    }


    @Test
    public void testCancelSharingEndpoint() {
        try (Client client = ClientBuilder.newClient()) {
            Form form = new Form();
            form.param("fileName", "test.jpg");
            form.param("ipAddress", "192.168.0.99");
            form.param("port", "7070");

            Response response = client
                    .target(URI.create(BASE_URI))
                    .path("/cancelSharing")
                    .request()
                    .build("DELETE", Entity.form(form))
                    .invoke();

            assertEquals(200, response.getStatus());
            // Add additional assertions based on your service behavior
        }
    }


    @Test
    public void testFindSharedFilesEndpoint() {
        try (Client client = ClientBuilder.newClient()) {
            Response response = client
                    .target(URI.create(BASE_URI))
                    .path("/findSharedFiles/testFile")
                    .request()
                    .get();

            assertEquals(200, response.getStatus());
            // Add additional assertions based on your service behavior
        }
    }

    @Test
    public void testGetSocketAddressByIdEndpoint() {
        try (Client client = ClientBuilder.newClient()) {
            Response response = client
                    .target(URI.create(BASE_URI))
                    .path("/getSocketAddressById/1")
                    .request()
                    .get();

            assertEquals(200, response.getStatus());
            // Add additional assertions based on your service behavior
        }
    }
}

