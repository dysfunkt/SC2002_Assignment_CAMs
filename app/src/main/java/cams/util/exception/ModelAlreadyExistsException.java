package cams.util.exception;

/**
 * Custom exception that is thrown when an attempt is made to add a model to a repository  that already contains a model with the same ID.
 * It extends the Exception class.
 */
public class ModelAlreadyExistsException extends Exception{
    
    /**
     * Creates a new instance of the ModelAlreadyExistsException class with a default error message.
     * The default message is "Model already exists".
     */
    public ModelAlreadyExistsException() {
        super("Model already exists");
    }

    /**
     * Creates a new instance of the ModelAlreadyExistsException class with a custom error message.
     *
     * @param message The custom error message to be used.
     */
    public ModelAlreadyExistsException(String message) {
        super(message);
    }
}
