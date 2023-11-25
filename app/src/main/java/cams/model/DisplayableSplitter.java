package cams.model;

/**
 * This interface represents an interface for objects that provide a splitter string for display.
 * This interface extends the Displayable interface.
 */
public interface DisplayableSplitter extends Displayable{
    /**
     * Gets the string splitter to display
     * @return String of the splitter to display
     */
    String getSplitterString();
}
