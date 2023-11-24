package cams.util.exception;

public class ParticipantAlreadyRegisteredException extends Exception{
    public ParticipantAlreadyRegisteredException() {
        super("There are already registered participants!");
    }
    public ParticipantAlreadyRegisteredException(String message) {
        super(message);
    }
}
