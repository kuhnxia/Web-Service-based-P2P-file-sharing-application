package kun.enpoint;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import kun.service.FileShareService;

@Path("/")
public class FileShareEndpoint {

	@Inject
	private FileShareService service;

	@GET
	@Path("/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response sayHello(final @PathParam("name") String name) {
		String response = service.hello(name);
		
		return Response.ok(response).build();
	}
}
