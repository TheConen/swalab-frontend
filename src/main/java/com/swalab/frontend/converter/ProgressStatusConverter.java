package com.swalab.frontend.converter;

import com.swalab.frontend.api.IStatusConverterConstants;
import com.swalab.frontend.model.Status;
import javafx.util.StringConverter;

public class ProgressStatusConverter extends StringConverter<Status> implements IStatusConverterConstants {

    private static final String OPEN = "Open";
    private static final String FINISHED = "Finished";
    private static final String IN_PROGRESS = "In progress";

    @Override
    public String toString(Status status) {
        if(status==null){
            return "Not set yet";
        }
        switch (status) {
            case OPEN:
                return OPEN;
            case FINISHED:
                return FINISHED;
            case IN_PROGRESS:
                return IN_PROGRESS;
            default:
                throw new IllegalArgumentException(status + NOT_SUPPORTED);
        }
    }

    @Override
    public Status fromString(String string) {
        if(string==null){
            string=OPEN;
        }
        switch (string) {
            case OPEN:
                return Status.OPEN;
            case IN_PROGRESS:
                return Status.IN_PROGRESS;
            case FINISHED:
                return Status.FINISHED;
            default:
                throw new IllegalArgumentException(string + NOT_SUPPORTED);
        }
    }
}
