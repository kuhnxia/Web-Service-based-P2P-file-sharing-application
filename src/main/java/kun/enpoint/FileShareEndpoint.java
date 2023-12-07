package kun.enpoint;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import kun.exceptions.*;
import kun.service.FileShareService;

import java.sql.SQLException;

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

		Response.Status status = null;
		String response = null;
		try {
			service.registerFile(fileName, ipAddress, port);
			status = Response.Status.OK;
			response = "File is Registered Successfully!";
		} catch (SQLException e) {
			status = Response.Status.INTERNAL_SERVER_ERROR;
			response = e.getMessage();
		} catch (ConflictException e) {
			status = Response.Status.CONFLICT;
			response = e.getMessage();
		}

		return Response.status(status)
				.entity(response)
				.build();

	}

	@PUT
	@Path("/cancelSharing")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response cancelSharing(
			@FormParam("fileName") String fileName,
			@FormParam("ipAddress") String ipAddress,
			@FormParam("port") int port) {

		Response.Status status = null;
		String response = null;
		try {
			service.cancelSharing(fileName, ipAddress, port);
			status = Response.Status.OK;
			response = "The shared file has no longer shareable.";
		} catch (SQLException e) {
			status = Response.Status.INTERNAL_SERVER_ERROR;
			response = e.getMessage();
		} catch (NotModifiedException e) {
			status = Response.Status.NOT_MODIFIED;
			response = e.getMessage();
		}
		return Response.status(status)
				.entity(response)
				.build();
	}

	@GET
	@Path("/findSharedFiles/{filename}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response findSharedFiles(@PathParam("filename") String filename) {
		Response.Status status = null;
		String response = null;
		try {
			response = service.findSharedFiles(filename);
			if (!response.equals("")) {
				status = Response.Status.OK;
			} else {
				status = Response.Status.NO_CONTENT;
				response = "No Match Result.";
			}
		} catch (SQLException e) {
			status = Response.Status.INTERNAL_SERVER_ERROR;
			response = e.getMessage();
		}
		return Response.status(status)
				.entity(response)
				.build();
	}

	@GET
	@Path("/getSocketAddressById/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getSocketAddressById(@PathParam("id") int id) {
		Response.Status status = null;
		String response = null;
		try {
			response = service.getSocketAddressById(id);
			if (!response.equals("")) {
				status = Response.Status.OK;
			} else {
				status = Response.Status.NOT_FOUND;
				response = "The id doesn't exist.";
			}
		} catch (SQLException e) {
			status = Response.Status.INTERNAL_SERVER_ERROR;
			response = e.getMessage();
		}
		return Response.status(status)
				.entity(response)
				.build();
	}
}
