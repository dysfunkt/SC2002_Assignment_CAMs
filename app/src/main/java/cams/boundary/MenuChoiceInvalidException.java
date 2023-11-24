package cams.boundary;

public class MenuChoiceInvalidException extends IllegalStateException{
    public MenuChoiceInvalidException(String tag) {
        super("Invalid Choice (" + tag + ")");
    }
}
