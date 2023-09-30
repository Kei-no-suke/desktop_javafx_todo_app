package com.keinosuke.todoapp.controllers.completedList;

import com.keinosuke.todoapp.models.Model;
import com.keinosuke.todoapp.models.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CompletedCellController implements Initializable {
    public AnchorPane root;
    public CheckBox is_completed_box;
    public Button delete_btn;
    public Label task_name_lbl;
    public Button archive_btn;

    private Task task;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void setTask(Task task){
        this.task = task;
    }

    public void setTaskFirst(Task task){
        setTask(task);
        if(this.task != null){
            is_completed_box.setSelected(this.task.getCompleted());
            task_name_lbl.setText(this.task.getName());

            delete_btn.setOnAction(event -> {
                Model.getInstance().getDatabaseDriver().deleteTask(this.task.getId());
                Model.getInstance().removeObTask(this.task);
            });
            archive_btn.setOnAction(event -> {
                onArchiveBtnClicked();
            });
            is_completed_box.setOnAction(event -> {
                onChangeCompleted();
            });
        }
    }

    public void onArchiveBtnClicked(){
        Model.getInstance().moveObCompletedTaskToObArchivedTask(task);
    }

    private void onChangeCompleted(){
        if(is_completed_box.isSelected()){
            Model.getInstance().moveObTaskToObCompletedTask(task);
        }else{
            Model.getInstance().moveObCompletedTaskToObTask(task);
        }
    }
}
