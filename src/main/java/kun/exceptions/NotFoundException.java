package kun.exceptions;

/**
 * Exception indicating that a resource was not found.
 *
 * This exception is thrown when attempting to access a resource that does not exist.
 *
 * @author Kun Xia
 */
public class NotFoundException extends RuntimeException {

    /**
     * Constructs a new NotFoundException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the getMessage() method).
     */
    public NotFoundException(String message) {
        super(message);
    }
}