package com.swalab.frontend.util;

import com.swalab.frontend.api.ISelectionPropagation;
import com.swalab.frontend.gui.AbstractPaneContent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AutomatedSceneSwitcher implements ISelectionPropagation {
    private Map<Class, ContentPaneButtonMappingEntry> _mapping;


    public AutomatedSceneSwitcher(){
        _mapping = new HashMap<>();
    }

    public void addElements(Class clazz, AbstractPaneContent paneContent, ToggleButton button){
        _mapping.put(clazz,new ContentPaneButtonMappingEntry(button,paneContent));
    }

    @Override
    public <T> void select(Class clazz, long id) {
        ContentPaneButtonMappingEntry mappingEntry = _mapping.get(clazz);
        if(mappingEntry!=null){
            AbstractPaneContent paneContent = mappingEntry.getPaneContent();
            Optional<T> optional=(paneContent.<T>getElementById(id));
            if(optional.isPresent()){
                ToggleButton button = mappingEntry.getButton();
                button.setSelected(true);
                EventHandler<ActionEvent> onAction = button.getOnAction();
                onAction.handle(null);
                paneContent.<T>select(optional.get());
            }
        }

    }

}
