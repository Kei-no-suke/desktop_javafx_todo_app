package com.keinosuke.todoapp.controllers.archiveTaskList;

import com.keinosuke.todoapp.models.Model;
import com.keinosuke.todoapp.models.Task;
import com.keinosuke.todoapp.views.ArchiveCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ArchiveListController implements Initializable {
    public ListView<Task> task_list_view;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        task_list_view.setItems(Model.getInstance().getObArchivedTasks());
        task_list_view.setCellFactory(param -> new ArchiveCellFactory());
        task_list_view.setEditable(true);
    }
}
