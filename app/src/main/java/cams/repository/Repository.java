package cams.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import cams.model.Model;
import cams.util.exception.ModelAlreadyExistsException;
import cams.util.exception.ModelNotFoundException;

/**
 * The Repository abstract class provides the basic functionality for storing, retrieving, and managing a list of Model objects.
 * All Repository classes should extend from this class.
 * @param <ModelObject> the type of Model object stored in the repository
 */
public abstract class Repository<ModelObject extends Model> implements Iterable<ModelObject>{

    /**
     * The list of Model objects stored in the repository.
     */
    List<ModelObject> listOfModelObjects;

    /**
     * Creates a new instance of the Repository class.
     */
    public Repository() {
        listOfModelObjects = new ArrayList<>();
    }
    
    /**
     * Returns an iterator over the list of Model objects of type {@code T}.
     *
     * @return an iterator over the list of Model objects
     */
    @Override
    public Iterator<ModelObject> iterator() {
        return listOfModelObjects.iterator();
    }

    /** 
     * Gets a Model object by ID
     * @param modelObjectID the ID of the Model object to get
     * @return the Model object with the given ID
     * @throws ModelNotFoundException if the Model object with the given ID does not exist
     */
    public ModelObject getByID(String modelObjectID) throws ModelNotFoundException {
        for (ModelObject modelObject : listOfModelObjects) {
            if (modelObject.getID().equalsIgnoreCase(modelObjectID)) {
                return modelObject;
            }
        }
        throw new ModelNotFoundException("No model object with ID " + modelObjectID + " exists.");
    }
    
    
    /** 
     * Gets a list Model object by a list of IDs
     * @param modelObjectIDList the list of ID of the Model objects to get
     * @return the list of of Model objects with the given IDs
     * @throws ModelNotFoundException if the Model object with the given ID does not exist
     */
    public List<ModelObject> getByIDList(List<String> modelObjectIDList) throws ModelNotFoundException {
        List<ModelObject> list = new ArrayList<>();
        aa:
        for (String modelObjectID : modelObjectIDList){
            for (ModelObject modelObject : listOfModelObjects) {
                if (modelObject.getID().equalsIgnoreCase(modelObjectID)) {
                    list.add(modelObject);
                    continue aa;
                }
            }
            throw new ModelNotFoundException("No model object with ID " + modelObjectID + " exists.");
        }
        return list;
    }

    /**
     * Checks whether the repository contains a Model object with the given ID.
     *
     * @param modelObjectID the ID of the Model object to check
     * @return true if the repository contains a Model object with the given ID, false otherwise
     */
    public boolean contains(String modelObjectID) {
        try {
            getByID(modelObjectID);
            return true;
        } catch (ModelNotFoundException e) {
            return false;
        }
    }

    /**
     * Adds a Model object to the repository.
     *
     * @param modelObject the model object to add
     * @throws ModelAlreadyExistsException if a model object with the same ID already exists in the repository
     */
    public void add(ModelObject modelObject) throws ModelAlreadyExistsException {
        if (contains(modelObject.getID())) {
            throw new ModelAlreadyExistsException("A model object with ID " + modelObject.getID() + " already exists.");
        } else {
            listOfModelObjects.add(modelObject);
        }
    }

    /**
     * Removes a Model object from the repository by ID.
     *
     * @param modelObjectID the ID of the model object to remove
     * @throws ModelNotFoundException if the model object with the given ID does not exist
     */
    public void remove(String modelObjectID) throws ModelNotFoundException {
        listOfModelObjects.remove(getByID(modelObjectID));
    }

    /**
     * Checks whether the repository is empty.
     *
     * @return true if the repository is empty, false otherwise
     */
    public boolean isEmpty() {
        return listOfModelObjects.isEmpty();
    }

    /**
     * Gets the size of the repository.
     *
     * @return the size of the repository
     */
    public int size() {
        return listOfModelObjects.size();
    }

    /**
     * Gets a list of all Model objects in the repository.
     *
     * @return a list of all Model objects in the repository
     */
    public List<ModelObject> getList() {
        List<ModelObject> list = new ArrayList<>();
        for (ModelObject o : listOfModelObjects) {
            list.add(o);
        }
        return list;
    }

    /**
     * Writes a list of String Arrays to a CSV file
     * Note: This will overwrite the file
     *
     * @param writeStrings List of String arrays to write to
     * @param writer The BufferedWriter object instance to the CSV file.
     */
    protected void writeToCsvFile(List<String[]> writeStrings, BufferedWriter writer) {
        PrintWriter w = new PrintWriter(writer);
        writeStrings.forEach((s) -> w.println(String.join("|||", s)));
        w.flush();
        w.close();
    }

    /**
     * Reads CSV file to a String[] list
     *
     * @param reader The BufferedReader object instance to the CSV File. 
     * @param skip How many lines in the file to skip (set 1 for header)
     * @return A list of string arrays containing all the CSV values
     */
    protected List<String[]> readAll(BufferedReader reader, int skip) {
        List<String> tmp = reader.lines().collect(Collectors.toList());
        if (tmp.size() == 0) return new ArrayList<>(); // Empty CSV file
        IntStream.range(0, skip).forEach((i) -> tmp.remove(0));
        List<String[]> result = new ArrayList<>();
        tmp.forEach((s) -> {
            if (s.trim().isEmpty()) return; // Ignore this line
            result.add(s.split("\\|\\|\\|"));
        });
        return result;
    }

    /** 
     * Load list of Model objects from repository file.
     */
    public abstract void load() throws IOException;

    /** 
     * Save list of Model objects from repository file.
     */
    public abstract void save() throws IOException;
}
