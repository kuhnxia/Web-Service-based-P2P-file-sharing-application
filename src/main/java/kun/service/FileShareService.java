package kun.service;

import jakarta.inject.Inject;
import kun.dao.SharedFileDao;
import kun.entity.SharedFile;
import kun.exceptions.ConflictException;
import kun.exceptions.NotFoundException;

import java.sql.SQLException;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
/**
 * The FileShareImpl class implements the FileShare interface and provides the server-side
 * implementation for file sharing operations using Jboss.
 * @author Kun Xia
 */
@ApplicationScoped
public class FileShareService {

    @Inject
    SharedFileDao sharedFileDao;

    /**
     * Registers a file for sharing in the P2P file sharing system.
     *
     * @param fileName  The name of the file to be registered.
     * @param ipAddress The IP address of the client sharing the file.
     * @param port      The port on which the client is sharing the file.
     * @return A boolean indicating the success or failure of the registration process.
     * @throws SQLException If a SQL exception occurs.
     */
    public boolean registerFile(String fileName, String ipAddress, int port) throws SQLException {
        SharedFile newSharedFile = new SharedFile(fileName, ipAddress, port);

        // Get all the share files from the database.
        List<SharedFile> sharedFiles = sharedFileDao.getAllSharedFiles();

        // Check if the share file already exists.
        for (SharedFile sharedFile : sharedFiles){
            if (newSharedFile.equals(sharedFile))
                throw new ConflictException("The shared file already exists");
        }

        // Register the file.
        return sharedFileDao.createSharedFile(newSharedFile);

    }

    /**
     * Cancels the sharing of a file in the P2P file sharing system.
     *
     * @param fileName  The name of the file to be canceled for sharing.
     * @param ipAddress The IP address of the client canceling the sharing.
     * @param port      The port on which the client is canceling the sharing.
     * @return A boolean indicating the success or failure of the canceling process.
     * @throws SQLException If a SQL exception occurs.
     */
    public boolean cancelSharing(String fileName, String ipAddress, int port) throws SQLException {
        SharedFile newSharedFile = new SharedFile(fileName, ipAddress, port);

        // Get all the share files from the database.
        List<SharedFile> sharedFiles = sharedFileDao.getAllSharedFiles();

        // Check if the share file exists.
        for (SharedFile sharedFile : sharedFiles){
            if (newSharedFile.equals(sharedFile)){
                // Delete the identical share file record.
                return sharedFileDao.deleteSharedFile(sharedFile.getId());
            }
        }

        throw new NotFoundException("The shared file doesn't exist");
    }

    /**
     * Searches for shared files with a given filename in the P2P file sharing system.
     *
     * @param filename The name of the file to be searched.
     * @return A string containing the IDs of the shared files with the target filename.
     * @throws SQLException If a SQL exception occurs.
     */
    public String findSharedFiles(String filename) throws SQLException {
        // Get all the share files from the database.
        List<SharedFile> sharedFiles = sharedFileDao.getAllSharedFiles();

        String filesInfo = "";

        // Find all shared files with the target filename.
        for (SharedFile sharedFile : sharedFiles) {
            if (filename != null && sharedFile.getFilename().equals(filename)) {
                filesInfo += sharedFile.getId() + " ";
            }
        }
        return filesInfo;
    }

    /**
     * Retrieves the socket address (IP address and port) of a shared file by its ID.
     *
     * @param id The ID of the shared file.
     * @return The socket address in the format "ip_address:port".
     * @throws SQLException If a SQL exception occurs.
     */
    public String getSocketAddressById(int id) throws SQLException {
        String socketInfo = "";
        // Get the target file to share.
        SharedFile targetFile = sharedFileDao.getSharedFileById(id);
        if (targetFile != null)
            socketInfo = targetFile.getIpAddress() + ":" + targetFile.getPort();
        return socketInfo;

    }
}
