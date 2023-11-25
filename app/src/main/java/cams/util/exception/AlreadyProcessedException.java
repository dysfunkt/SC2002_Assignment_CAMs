package cams.util.exception;

/**
 * Custom exception that is thrown when a requested model cannot be found in a repository.
 * It extends the Exception class.
 */
public class AlreadyProcessedException extends Exception{

    /**
     * Creates a new instance of the AlreadyProcessedException class with a default error message.
     * The default message is "Already processed.".
     */
    public AlreadyProcessedException() {
        super("Already processed.");
    }

    /**
     * Creates a new instance of the AlreadyProcessedException class with a custom error message.
     * @param message The custom error message to be used.
     */
    public AlreadyProcessedException(String message) {
        super(message);
    }
}
