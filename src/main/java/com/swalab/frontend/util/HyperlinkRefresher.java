package com.swalab.frontend.util;

import com.swalab.frontend.api.INamedIDArtefact;
import com.swalab.frontend.api.ISelectionPropagation;
import javafx.scene.control.Hyperlink;

public class HyperlinkRefresher {
    private ISelectionPropagation _propagation;

    public HyperlinkRefresher(ISelectionPropagation propagation) {
        _propagation = propagation;
    }

    public <T extends INamedIDArtefact> void refreshHyperlink(Hyperlink link, T content, Class targetClass) {
        link.setText(content.getName());
        link.setOnAction(ae -> _propagation.select(targetClass, content.getId()));
    }
}
