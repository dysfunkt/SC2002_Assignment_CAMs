package cams.util.exception;

public class ModelAlreadyExistsException extends Exception{
    
    public ModelAlreadyExistsException() {
        super("Model already exists");
    }

    public ModelAlreadyExistsException(String message) {
        super(message);
    }
}
