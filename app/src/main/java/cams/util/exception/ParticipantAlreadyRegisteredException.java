package cams.util.exception;

/**
 * Custom exception that is thrown when user attempts to delete a camp or make a camp invisible when the camp already has participants.
 * It extends the Exception class.
 */
public class ParticipantAlreadyRegisteredException extends Exception{

    /**
     * Creates a new instance of the ParticipantAlreadyRegisteredException class with a default error message.
     * The default message is "There are already registered participants!".
     */
    public ParticipantAlreadyRegisteredException() {
        super("There are already registered participants!");
    }

    /**
     * Creates a new instance of the ParticipantAlreadyRegisteredException class with a custom error message.
     *
     * @param message The custom error message to be used.
     */
    public ParticipantAlreadyRegisteredException(String message) {
        super(message);
    }
}
