package kun.dao;

import kun.connector.DatabaseConnector;
import kun.entity.SharedFile;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The SharedFileDao class provides methods to interact with the SharedFiles table in the database.
 */
@ApplicationScoped
public class SharedFileDao {

    /**
     * Creates a new shared file entry in the database.
     *
     * @param sharedFile The SharedFile object representing the file to be registered.
     * @return A message indicating the result of the operation.
     */
    @Inject
    private DatabaseConnector databaseConnector;

    public boolean createSharedFile(SharedFile sharedFile) throws SQLException {
        Connection connection = databaseConnector.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO SharedFiles (file_name, ip_address, port) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, sharedFile.getFilename());
        preparedStatement.setString(2, sharedFile.getIpAddress());
        preparedStatement.setInt(3, sharedFile.getPort());

        int affectedRows = preparedStatement.executeUpdate();
        if (affectedRows == 0)
            throw new SQLException("Creating shared file failed, no rows affected.");

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()){
            return true;
        } else {
            throw new SQLException("Creating shared file failed, no ID obtained.");
        }

    }


    /**
     * Retrieves a shared file from the database based on its ID.
     *
     * @param fileId The ID of the shared file.
     * @return The SharedFile object representing the retrieved file, or null if not found.
     */
    public SharedFile getSharedFileById(int fileId) throws SQLException {
        Connection connection = databaseConnector.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM SharedFiles WHERE id = ?");
        preparedStatement.setInt(1, fileId);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return mapResultSetToSharedFile(resultSet);
        } else {
            return null;
        }
    }

    /**
     * Retrieves all shared files from the database.
     *
     * @return A list of SharedFile objects representing all shared files in the database.
     */
    public List<SharedFile> getAllSharedFiles() throws SQLException {
        List<SharedFile> sharedFiles = new ArrayList<>();

        Connection connection = databaseConnector.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM SharedFiles");

        while (resultSet.next()) {
            SharedFile sharedFile = mapResultSetToSharedFile(resultSet);
            sharedFiles.add(sharedFile);
        }

        return sharedFiles;
    }

    /**
     * Deletes a shared file from the database based on its ID.
     *
     * @param fileId The ID of the shared file to be deleted.
     * @return A message indicating the result of the operation.
     */
    public boolean deleteSharedFile(int fileId) throws SQLException {
        Connection connection = databaseConnector.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM SharedFiles WHERE id = ?");

        preparedStatement.setInt(1, fileId);
        preparedStatement.executeUpdate();

        return true;

    }

    /**
     * Maps a ResultSet to a SharedFile object.
     *
     * @param resultSet The ResultSet containing the shared file information.
     * @return The SharedFile object representing the mapped file.
     * @throws SQLException If a SQL exception occurs.
     */
    private SharedFile mapResultSetToSharedFile(ResultSet resultSet) throws SQLException {
        SharedFile sharedFile = new SharedFile(
                resultSet.getString("file_name"),
                resultSet.getString("ip_address"),
                resultSet.getInt("port")
        );
        sharedFile.setId(resultSet.getInt("id"));
        return sharedFile;
    }
}