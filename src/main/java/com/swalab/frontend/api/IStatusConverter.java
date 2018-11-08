package com.swalab.frontend.api;

import com.swalab.frontend.model.Status;

public interface IStatusConverter {

    static String NOT_SUPPORTED=" is not supported yet";

    /**
     * converts a status into a string
     * @param status
     * @return
     */
    String toString(Status status);

    /**
     * converts a string into a status
     * @param string
     * @return
     */
    Status toStatus(String string);
}
