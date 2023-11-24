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


public abstract class Repository<ModelObject extends Model> implements Iterable<ModelObject>{
    List<ModelObject> listOfModelObjects;

    public Repository() {
        listOfModelObjects = new ArrayList<>();
    }

    @Override
    public Iterator<ModelObject> iterator() {
        return listOfModelObjects.iterator();
    }
    protected List<ModelObject> getList() {
        return listOfModelObjects;
    }

    public ModelObject getByID(String modelObjectID) throws ModelNotFoundException {
        for (ModelObject modelObject : listOfModelObjects) {
            if (modelObject.getID().equalsIgnoreCase(modelObjectID)) {
                return modelObject;
            }
        }
        throw new ModelNotFoundException("No model object with ID " + modelObjectID + " exists.");
    }

    public boolean contains(String modelObjectID) {
        try {
            getByID(modelObjectID);
            return true;
        } catch (ModelNotFoundException e) {
            return false;
        }
    }

    public void add(ModelObject modelObject) throws ModelAlreadyExistsException {
        if (contains(modelObject.getID())) {
            throw new ModelAlreadyExistsException("A model object with ID " + modelObject.getID() + " already exists.");
        } else {
            listOfModelObjects.add(modelObject);
        }
    }

    public void remove(String modelObjectID) throws ModelNotFoundException {
        listOfModelObjects.remove(getByID(modelObjectID));
    }

    public boolean isEmpty() {
        return listOfModelObjects.isEmpty();
    }

    public int size() {
        return listOfModelObjects.size();
    }

    public void update(ModelObject modelObject) throws ModelNotFoundException {
        ModelObject oldModelObject = getByID(modelObject.getID());
        listOfModelObjects.set(listOfModelObjects.indexOf(oldModelObject), modelObject);
    }

    protected void writeToCsvFile(List<String[]> writeStrings, BufferedWriter writer) {
        PrintWriter w = new PrintWriter(writer);
        writeStrings.forEach((s) -> w.println(String.join("|||", s)));
        w.flush();
        w.close();
    }

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

    public abstract void load() throws IOException;

    public abstract void save() throws IOException;
}
