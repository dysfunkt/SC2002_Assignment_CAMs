package cams.model;

import cams.util.iocontrol.Savable;

/**
 * This interface represents the models in the system.
 * This interface extends the Savable interface.
 */
public interface Model extends Savable{

    /**
     * Gets the specific Id of the model.
     * @return the specific Id of the model.
     */
    String getID();

}
