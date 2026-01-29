package nexus.exception;

/**
 * Custom exception class for the Nexus app.
 */
public class NexusException extends Exception {
    /**
     * Constructs NexusException with the specified error message.
     * @param message Error message describing the exception.
     */
    public NexusException(String message) {
        super(message);
    }
}
