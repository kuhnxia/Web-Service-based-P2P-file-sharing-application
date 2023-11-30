package kun.enpoint;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
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

	@POST
	@Path("/registerFile")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response registerFile(
			@FormParam("fileName") String fileName,
			@FormParam("ipAddress") String ipAddress,
			@FormParam("port") int port) {
		String response = service.registerFile(fileName, ipAddress, port);
		return Response.ok(response).build();
	}

	@DELETE
	@Path("/cancelSharing")
	@Produces(MediaType.TEXT_PLAIN)
	public Response cancelSharing(
			@FormParam("fileName") String fileName,
			@FormParam("ipAddress") String ipAddress,
			@FormParam("port") int port) {
		String response = service.cancelSharing(fileName, ipAddress, port);
		return Response.ok(response).build();
	}

	@GET
	@Path("/findSharedFiles/{filename}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response findSharedFiles(@PathParam("filename") String filename) {
		String response = service.findSharedFiles(filename);
		return Response.ok(response).build();
	}

	@GET
	@Path("/getSocketAddressById/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getSocketAddressById(@PathParam("id") int id) {
		String response = service.getSocketAddressById(id);
		return Response.ok(response).build();
	}
}
