package com.swalab.frontend.gui.composites;

import com.swalab.frontend.api.INamedArtefact;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class NamedArtefactBasedListCellFactory<T extends INamedArtefact> implements Callback<ListView<T>,ListCell<T>> {

    @Override
    public ListCell<T> call(ListView<T> listView) {
        return new ListCell<>() {
            @Override
            public void updateItem(T item, boolean isEmpty) {
                super.updateItem(item, isEmpty);
                if (item == null || isEmpty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.getName());
                }
            }
        };
    }

}
