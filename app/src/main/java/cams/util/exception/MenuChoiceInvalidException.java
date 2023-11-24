package cams.util.exception;

public class MenuChoiceInvalidException extends IllegalStateException{
    public MenuChoiceInvalidException(String tag) {
        super("Invalid Choice (" + tag + ")");
    }
}
