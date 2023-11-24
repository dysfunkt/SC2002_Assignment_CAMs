package cams.util.exception;

public class OperationCancelledException extends Exception{
    public OperationCancelledException() {
        super("Operation was cancelled.");
    }
    public OperationCancelledException(String message) {
        super(message);
    }
}
