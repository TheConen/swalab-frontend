package com.swalab.frontend.gui.object.builder;

import com.swalab.frontend.api.IEditorSettings;
import com.swalab.frontend.gui.composites.InlineEditor;
import com.swalab.frontend.model.WarehousePartAndOrder;

public class WarehousePartAndOrderEditingSettings implements IEditorSettings<WarehousePartAndOrder> {
    @Override
    public WarehousePartAndOrder createObject() {
        return null;
    }

    @Override
    public boolean canObjectBeCreated() {
        return false;
    }
}
