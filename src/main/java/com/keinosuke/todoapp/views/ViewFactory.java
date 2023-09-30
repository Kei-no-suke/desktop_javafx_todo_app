package com.keinosuke.todoapp.views;

import com.keinosuke.todoapp.controllers.MainScreenController;
import com.keinosuke.todoapp.controllers.archiveTaskList.ArchiveListController;
import com.keinosuke.todoapp.controllers.taskList.TaskCellController;
import com.keinosuke.todoapp.controllers.taskList.TaskListController;
import com.keinosuke.todoapp.models.Model;
import com.keinosuke.todoapp.models.Task;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ViewFactory {

    private AnchorPane taskListView;
    private AnchorPane archiveListView;
    private AnchorPane completedListView;

    private AnchorPane dateTaskListView;

    private final ObjectProperty<TaskMenuOptions> taskSelectedMenuItem;
    public ViewFactory() {
        this.taskSelectedMenuItem = new SimpleObjectProperty<>();
    }

    public ObjectProperty<TaskMenuOptions> getTaskSelectedMenuItem(){
        return taskSelectedMenuItem;
    }

    public AnchorPane getTaskListView() {
        if(taskListView == null){
            try{
                taskListView = new FXMLLoader(getClass().getResource("/fxml/taskList/TaskList.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return taskListView;
    }

    public AnchorPane getArchiveListView() {
        if(archiveListView == null){
            try {
                archiveListView = new FXMLLoader(getClass().getResource("/fxml/archiveTaskList/ArchiveList.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return archiveListView;
    }

    public AnchorPane getCompletedListView() {
        if(completedListView == null){
            try{
                completedListView = new FXMLLoader(getClass().getResource("/fxml/completedList/CompletedList.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return completedListView;
    }

    public void showMainScreenWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainScreen.fxml"));

        Node parentNode = null;
        try {
            parentNode = loader.load();
        }catch (Exception e){
            e.printStackTrace();
        }

        createStage((Parent) parentNode);
    }

    private void createStage(FXMLLoader loader){
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        }catch (Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Todo App");
        stage.show();
    }

    private void createStage(Parent parent){
        Scene scene = null;
        try {
            scene = new Scene(parent);
        }catch (Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Todo App");
        stage.show();
    }

    private void createStage(AnchorPane anchorPane){
        Scene scene = null;
        try {
            scene = new Scene(anchorPane);
        }catch (Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Todo App");
        stage.show();
    }
}
