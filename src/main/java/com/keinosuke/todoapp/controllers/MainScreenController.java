package com.keinosuke.todoapp.controllers;

import com.keinosuke.todoapp.models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    public BorderPane parent_pane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getTaskSelectedMenuItem().addListener((observable, oldValue, newValue) -> {
            switch (newValue){
                case ARCHIVE_LIST -> parent_pane.setCenter(Model.getInstance().getViewFactory().getArchiveListView());
                case COMPLETED_LIST -> parent_pane.setCenter(Model.getInstance().getViewFactory().getCompletedListView());
                default -> parent_pane.setCenter(Model.getInstance().getViewFactory().getTaskListView());
            }
        });
    }
}
