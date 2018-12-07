package com.swalab.frontend.util;

import com.swalab.frontend.gui.AbstractPaneContent;
import javafx.scene.control.ToggleButton;

public class ContentPaneButtonMappingEntry {

    private ToggleButton _button;
    private AbstractPaneContent _paneContent;

    public ContentPaneButtonMappingEntry(ToggleButton button, AbstractPaneContent paneContent) {
        _button = button;
        _paneContent = paneContent;
    }

    public ToggleButton getButton() {
        return _button;
    }

    public AbstractPaneContent getPaneContent() {
        return _paneContent;
    }
}
