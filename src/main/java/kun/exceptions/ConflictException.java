package kun.exceptions;
/**
 * Exception indicating a conflict in the operation.
 *
 * This exception is thrown when there is a conflict in the application logic,
 * such as attempting to register a file that already exists.
 *
 * @author Kun Xia
 */
public class ConflictException extends RuntimeException{
    /**
     * Constructs a new ConflictException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the getMessage() method).
     */
    public ConflictException(String message) {
        super(message);
    }
}
