package cams.model;

/**
 * This interface represents an interface for objects that provide a header string for display.
 * This interface extends the Displayable interface.
 */
public interface DisplayableHeader extends Displayable{
    /**
     * Gets header string to display.
     * @return String representing the header to be displayed.
     */
    String getHeaderString();
}
