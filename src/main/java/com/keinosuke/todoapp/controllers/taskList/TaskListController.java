package com.keinosuke.todoapp.controllers.taskList;

import com.keinosuke.todoapp.models.Model;
import com.keinosuke.todoapp.models.Task;
import com.keinosuke.todoapp.views.ArchiveCellFactory;
import com.keinosuke.todoapp.views.TaskCellFactory;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;

public class TaskListController implements Initializable {

    public AnchorPane task_display_area;
    public ListView<Task> task_list_view;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        task_list_view.setItems(Model.getInstance().getObTasks());
        task_list_view.setCellFactory(param -> new TaskCellFactory());
        task_list_view.setEditable(true);
    }

}
