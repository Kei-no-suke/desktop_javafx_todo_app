package com.keinosuke.todoapp.controllers.archiveTaskList;

import com.keinosuke.todoapp.models.Model;
import com.keinosuke.todoapp.models.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.controlsfx.control.PropertySheet;

import java.net.URL;
import java.util.ResourceBundle;

public class ArchiveCellController implements Initializable {
    public AnchorPane root;
    public Label task_name_lbl;
    public Button delete_btn;
    public Button unarchive_btn;

    private Task task;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void setTask(Task task){
        this.task = task;
    }

    public void setTaskFirst(Task task){
        setTask(task);
        if(this.task != null){
            task_name_lbl.setText(this.task.getName());

            delete_btn.setOnAction(event -> {
                Model.getInstance().getDatabaseDriver().deleteTask(this.task.getId());
                Model.getInstance().removeObArchivedTask(this.task);
            });
            unarchive_btn.setOnAction(event -> {
                onUnarchiveBtnClicked();
            });
        }
    }

    private void onUnarchiveBtnClicked(){
        Model.getInstance().moveObArchivedTaskToObTask(task);
    }
}
