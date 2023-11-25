package cams.util.iocontrol;

/**
 * This interface provides methods that an object that can be saved should implement.
 */
public interface Savable {
    
    /**
     * Generate an array of strings with all the attributes of the object in string form.
     * Used to save state of the object.
     * @return Array of attributes in string form.
     */
    public String[] toSaveString();
    
}
