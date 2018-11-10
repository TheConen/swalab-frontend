package com.swalab.frontend.api;

public interface IEditorSettings<T> {

    /**
     * creates a specific object with it's own structure out of the available input data
     * @return an object which represents the user's input
     */
    T createObject();

    // TODO rename class and insert possibility to reset text fields or initial values for the editor

    /**
     * checks all preconditions for an object creation
     * @return true if the creation is possible
     */
    boolean canObjectBeCreated();
}
