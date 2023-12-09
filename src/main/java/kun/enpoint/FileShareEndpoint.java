package kun.enpoint;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import kun.exceptions.*;
import kun.exceptions.NotFoundException;
import kun.service.FileShareService;

import java.sql.SQLException;

/**
 * WildFly web service endpoint for file sharing operations.
 *
 * This class defines methods for registering files, canceling file sharing,
 * finding shared files, and retrieving socket addresses by file sharer ID.
 *
 * @author Kun Xia
 */
@Path("/")
public class FileShareEndpoint {

	@Inject
	private FileShareService service;

	/**
	 * Registers a new file for sharing.
	 *
	 * @param fileName   The name of the file to be registered.
	 * @param ipAddress  The IP address of the file sharer.
	 * @param port       The port number on which the file is shared.
	 * @return Response indicating the status of the registration.
	 */
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

	/**
	 * Cancels sharing of a file.
	 *
	 * @param fileName   The name of the file to stop sharing.
	 * @param ipAddress  The IP address of the file sharer.
	 * @param port       The port number on which the file is shared.
	 * @return Response indicating the status of canceling file sharing.
	 */
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
			response = "The shared file is no longer shareable.";
		} catch (SQLException e) {
			status = Response.Status.INTERNAL_SERVER_ERROR;
			response = e.getMessage();
		} catch (NotFoundException e) {
			status = Response.Status.NOT_FOUND;
			response = e.getMessage();
		}
		return Response.status(status)
				.entity(response)
				.build();
	}

	/**
	 * Finds shared files by filename.
	 *
	 * @param filename   The name of the file to search for.
	 * @return Response containing the status and search results.
	 */
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
				status = Response.Status.NOT_FOUND;
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

	/**
	 * Gets the socket address of a file sharer by its ID.
	 *
	 * @param id   The ID of the file sharer.
	 * @return Response containing the status and socket address.
	 */
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
