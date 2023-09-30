package com.keinosuke.todoapp.views;

import com.keinosuke.todoapp.controllers.taskList.TaskCellController;
import com.keinosuke.todoapp.models.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

public class TaskCellFactory extends ListCell<Task> {
    @Override
    protected void updateItem(Task task, boolean empty){
        super.updateItem(task, empty);
        if(empty){
            setText(null);
            setGraphic(null);
        }else{
            setText(null);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/taskList/TaskCell.fxml"));
            TaskCellController taskCellController = new TaskCellController();
            loader.setController(taskCellController);
            AnchorPane anchorPane = null;
            try {
                anchorPane = loader.load();
            }catch (Exception e){
                e.printStackTrace();
            }
            taskCellController.setTaskFirst(task);
            setGraphic(anchorPane);
        }
    }
}
