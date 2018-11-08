package com.swalab.frontend.converter;

import com.swalab.frontend.api.IStatusConverter;
import com.swalab.frontend.model.Status;

public class ProgressStatusConverter implements IStatusConverter {

    private static final String OPEN = "Open";
    private static final String FINISHED = "Finished";
    private static final String IN_PROGRESS = "In progress";

    @Override
    public String toString(Status status) {
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
    public Status toStatus(String string) {
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
