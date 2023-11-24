package cams.util.exception;

public class AlreadyProcessedException extends Exception{
    public AlreadyProcessedException() {
        super("Already processed.");
    }

    public AlreadyProcessedException(String message) {
        super(message);
    }
}
