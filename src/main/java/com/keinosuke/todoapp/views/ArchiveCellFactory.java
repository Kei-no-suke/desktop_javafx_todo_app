package com.keinosuke.todoapp.views;

import com.keinosuke.todoapp.controllers.archiveTaskList.ArchiveCellController;
import com.keinosuke.todoapp.models.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

public class ArchiveCellFactory extends ListCell<Task> {
    @Override
    protected void updateItem(Task task, boolean empty){
        super.updateItem(task, empty);
        if(empty){
            setText(null);
            setGraphic(null);
        }else{
            setText(null);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/archiveTaskList/ArchiveCell.fxml"));
            ArchiveCellController archiveCellController = new ArchiveCellController();
            loader.setController(archiveCellController);
            AnchorPane anchorPane = null;
            try {
                anchorPane = loader.load();
            }catch (Exception e){
                e.printStackTrace();
            }
            archiveCellController.setTaskFirst(task);
            setGraphic(anchorPane);
        }
    }
}
