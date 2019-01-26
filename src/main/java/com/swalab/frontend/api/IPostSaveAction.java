package com.swalab.frontend.api;

public interface IPostSaveAction {
    /**
     * called for executing method after the object was saved
     */
     void executeCustomizedPostSaveAction();
}
