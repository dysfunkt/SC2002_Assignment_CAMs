package cams.boundary.modeldisplayer;

import java.util.List;
import java.util.Objects;

import cams.model.Displayable;
import cams.model.DisplayableHeader;
import cams.model.DisplayableSplitter;
import cams.model.camp.Camp;

/**
 * This class provides utility mthods for displaying objects and lists of displayable objects
 */
public class ModelDisplayer {
    
    /** 
     * Helper method to display a single Displayable item.
     * @param displayable Displayable item to display.
     */
    public static void displaySingleDisplayable(Displayable displayable) {
        if (displayable == null) {
            System.out.println("Nothing found.");
            return;
        }
        if (displayable instanceof Camp) {
            System.out.println(((DisplayableHeader)displayable).getHeaderString());
            System.out.println(displayable.getDisplayableString());
        } else {
            System.out.println(displayable.getDisplayableString());
            System.out.println(((DisplayableSplitter)displayable).getSplitterString());
        }
    }

    /**
     * Helper method to display a list of Displayable items.
     * @param displayableList List of Displayable items to display.
     */
    public static void displayListOfDisplayable(List<? extends Displayable> displayableList) {
        if (Objects.isNull(displayableList) || displayableList.isEmpty()) {
            System.out.println("Nothing found.");
            return;
        }
        if (displayableList.get(0) instanceof Camp) {
            System.out.println(((DisplayableHeader)displayableList.get(0)).getHeaderString());
            for (Displayable displayable : displayableList) {
                System.out.println(displayable.getDisplayableString());
            }
        } else {
            for (Displayable displayable : displayableList) {
                System.out.println(displayable.getDisplayableString());
                System.out.println(((DisplayableSplitter)displayable).getSplitterString());
            }
        }
    }
}
