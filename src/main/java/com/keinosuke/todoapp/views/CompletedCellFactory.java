package com.keinosuke.todoapp.views;

import com.keinosuke.todoapp.controllers.archiveTaskList.ArchiveCellController;
import com.keinosuke.todoapp.controllers.completedList.CompletedCellController;
import com.keinosuke.todoapp.models.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

public class CompletedCellFactory extends ListCell<Task> {
    @Override
    protected void updateItem(Task task, boolean empty){
        super.updateItem(task, empty);
        if(empty){
            setText(null);
            setGraphic(null);
        }else{
            setText(null);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/completedList/CompletedCell.fxml"));
            CompletedCellController completedCellController = new CompletedCellController();
            loader.setController(completedCellController);
            AnchorPane anchorPane = null;
            try {
                anchorPane = loader.load();
            }catch (Exception e){
                e.printStackTrace();
            }
            completedCellController.setTaskFirst(task);
            setGraphic(anchorPane);
        }
    }
}
