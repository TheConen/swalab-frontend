package com.swalab.frontend.api;

public interface IEditorSettings<T> {

    /**
     * creates a specific object with it's own structure out of the available input data
     * @return an object which represents the user's input
     */
    T createObject();

    /**
     * sets the current content as default values into the editor
     * @param content for getting the information from or null
     */
    void setDefaultValues(T content);

    /**
     * checks all preconditions for an object creation
     * @return true if the creation is possible
     */
    boolean canObjectBeCreated(); // TODO change all implementations of this interface so the check is performed correctly before the object is created
}
