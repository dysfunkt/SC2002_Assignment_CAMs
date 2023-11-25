package cams.util.exception;

/**
 * Custom exception for invalid menu choices.
 * It extends the IllegalStateException class.
 */
public class MenuChoiceInvalidException extends IllegalStateException{

    /**
     * Creates a new instance of the MenuChoiceInvalidException class.
     *
     * @param tag A tag to differentiate where this is thrown from.
     */
    public MenuChoiceInvalidException(String tag) {
        super("Invalid Choice (" + tag + ")");
    }
}
