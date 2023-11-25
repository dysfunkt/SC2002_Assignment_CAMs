package cams.util.exception;

/**
 * Custom exception that is thrown when a delete operation is cancelled.
 * It extends the Exception class.
 */
public class OperationCancelledException extends Exception{

    /**
     * Creates a new instance of the OperationCancelledException class with a default error message.
     * The default message is "Operation was cancelled.".
     */
    public OperationCancelledException() {
        super("Operation was cancelled.");
    }

    /**
     * Creates a new instance of the OperationCancelledException class with a custom error message.
     *
     * @param message The custom error message to be used.
     */
    public OperationCancelledException(String message) {
        super(message);
    }
}
