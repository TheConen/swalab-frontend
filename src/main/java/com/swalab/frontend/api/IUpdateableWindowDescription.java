package com.swalab.frontend.api;

public interface IUpdateableWindowDescription<T> {

    /**
     * triggers updating the description part of a scene
     */
    void updateDescriptionContent(T item);

    /**
     * triggers all (sub)editor frameworks for cleaning their latest settings / state
     */
    void clearEditorEnvironment();
}
