package com.swalab.frontend.gui.object.builder;

import com.swalab.frontend.api.IEditorSettings;
import com.swalab.frontend.model.Appointment;

public class AppointmentEditingSettings implements IEditorSettings<Appointment> {
    @Override
    public Appointment createObject() {
        return null;
    }

    @Override
    public boolean canObjectBeCreated() {
        return false;
    }
}
