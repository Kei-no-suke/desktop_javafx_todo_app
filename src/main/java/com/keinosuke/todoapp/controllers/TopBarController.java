package com.keinosuke.todoapp.controllers;

import com.keinosuke.todoapp.models.Model;
import com.keinosuke.todoapp.views.TaskMenuOptions;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class TopBarController implements Initializable {
    public Button task_list_btn;
    public Button archive_btn;
    public Button completed_btn;
    public Button add_task_btn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        add_task_btn.setOnAction(event -> {
            onAddTaskBtnClicked();
        });
        task_list_btn.setOnAction(event -> {
            onTaskListBtnClicked();
        });
        archive_btn.setOnAction(event -> {
            onArchiveBtnClicked();
        });
        completed_btn.setOnAction(event -> {
            onCompletedBtnClicked();
        });
    }

    private void onAddTaskBtnClicked(){
        AnchorPane anchorPane = null;
        try {
            anchorPane = new FXMLLoader(getClass().getResource("/fxml/AddTaskScreen.fxml")).load();
        }catch (Exception e){
            e.printStackTrace();
        }
        createStage(anchorPane);
    }

    private void onTaskListBtnClicked(){
        Model.getInstance().getViewFactory().getTaskSelectedMenuItem().set(TaskMenuOptions.TASK_LIST);
    }

    private void onArchiveBtnClicked(){
        Model.getInstance().getViewFactory().getTaskSelectedMenuItem().set(TaskMenuOptions.ARCHIVE_LIST);
    }

    private void onCompletedBtnClicked(){
        Model.getInstance().getViewFactory().getTaskSelectedMenuItem().set(TaskMenuOptions.COMPLETED_LIST);
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
