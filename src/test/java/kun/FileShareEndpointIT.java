package kun;

import java.net.URI;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.Response;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Run integration tests against the server and the deployed application.
 */
@RunAsClient
@RunWith(Arquillian.class)
public class FileShareEndpointIT {
    // Before run the testers, first ensure you are not registering a file with
    // name "test.jpg" and socket address "1.1.1.1:9999".
    private static final String BASE_URI = "http://localhost:8080/share";
    private static final String registerPath = "/registerFile";
    private static final String cancelPath = "/cancelSharing";
    private static final String searchPath = "/findSharedFiles/";
    private static final String getSocketPath = "/getSocketAddressById/";
    private static final String testFile = "test.jpg";
    private static Form form;
    @Before
    public void buildForm() {
        form = new Form();
        form.param("fileName", "test.jpg");
        form.param("ipAddress", "1.1.1.1");
        form.param("port", "9999");
    }
    @Test
    public void testRegisterFileOKAndCancelSharingOk() {
        //Register test.jpg, OK!
        Response response = registerFile();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        //Cancel test.jpg, OK!
        response = cancelSharing();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testRegisterFileConflict() {
        //Register test.jpg, OK!
        Response response = registerFile();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        //Register test.jpg again, Conflict!
        response = registerFile();
        assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());

        //Cancel test.jpg, OK!
        response = cancelSharing();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }


    @Test
    public void testCancelSharingNotFound() {
        //Cancel test.jpg, Not found!
        Response response = cancelSharing();
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
    @Test
    public void testFindSharedFilesOK() {
        //Before search, first register test.jpg, OK!
        Response response = registerFile();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        //Then found test.jpg, OK!
        response = findSharedFiles();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        //Cancel test.jpg, OK!
        response = cancelSharing();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
    @Test
    public void testFindSharedFilesNotFound() {
        //Search test.jpg, Not Found!
        Response response = findSharedFiles();
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
    @Test
    public void testFindSharedFilesWithIntegerIDs() {
        //Before search, first register test.jpg, OK!
        Response response = registerFile();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        //Then found test.jpg, OK!
        response = findSharedFiles();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        //Ensure response message including integers
        String[] ids = response.readEntity(String.class).split(" ");
        for (String id : ids) {
            try {
                Integer.parseInt(id);
                assertTrue(true);
            } catch (NumberFormatException e) {
                assertFalse(false);
            }
        }

        //Cancel test.jpg, OK!
        response = cancelSharing();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
    @Test
    public void testGetSocketAddressByIdOK() {
        //Before search, first register test.jpg, OK!
        Response response = registerFile();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        //Then found test.jpg, OK!
        response = findSharedFiles();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        //Ensure response message including integers
        String[] ids = response.readEntity(String.class).split(" ");
        try {
            int intId = Integer.parseInt(ids[0]);
            assertTrue(true);

            //Get socket address by one of ids of test.jpg, Ok!
            getSocketAddressById(intId);
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        } catch (NumberFormatException e) {
            assertFalse(false);
        }

        //Cancel test.jpg, OK!
        response = cancelSharing();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    }

    @Test
    public void testGetSocketAddressByIdNotFound() {
        //Ids begin with 1 in MySQL tables.
        Response response = getSocketAddressById(0);
        assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());

    }

    private Response registerFile() {
        try (Client client = ClientBuilder.newClient()) {

            return client
                    .target(URI.create(BASE_URI))
                    .path(registerPath)
                    .request()
                    .post(Entity.form(form));

        }
    }

    private Response cancelSharing() {
        try (Client client = ClientBuilder.newClient()) {
            return client
                    .target(URI.create(BASE_URI))
                    .path(cancelPath)
                    .request()
                    .put(Entity.form(form));
        }
    }

    private Response findSharedFiles() {
        try (Client client = ClientBuilder.newClient()) {
            return client
                    .target(URI.create(BASE_URI))
                    .path(searchPath + testFile)
                    .request()
                    .get();
        }
    }
    private Response getSocketAddressById(int id) {
        try (Client client = ClientBuilder.newClient()) {
            return client
                    .target(URI.create(BASE_URI))
                    .path(getSocketPath + id)
                    .request()
                    .get();
        }
    }
}
