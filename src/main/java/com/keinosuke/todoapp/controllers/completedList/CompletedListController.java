package com.keinosuke.todoapp.controllers.completedList;

import com.keinosuke.todoapp.models.Model;
import com.keinosuke.todoapp.models.Task;
import com.keinosuke.todoapp.views.ArchiveCellFactory;
import com.keinosuke.todoapp.views.CompletedCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class CompletedListController implements Initializable {

    public ListView<Task> task_list_view;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        task_list_view.setItems(Model.getInstance().getObCompletedTasks());
        task_list_view.setCellFactory(param -> new CompletedCellFactory());
        task_list_view.setEditable(true);
    }
}
