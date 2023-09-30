package com.keinosuke.todoapp.controllers.taskList;

import com.keinosuke.todoapp.models.Model;
import com.keinosuke.todoapp.models.Task;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class TaskEditScreenController implements Initializable {
    public TextField task_name_fld;
    public Slider progress_slider;
    public TextField progress_fld;
    public DatePicker deadline_d_picker;
    public Button done_btn;
    private Task task;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void setTask(Task task){
        this.task = task;
    }

    public void setTaskFirst(Task task){
        // Assign a value to a class field
        setTask(task);

        if(this.task != null){
            // Assign a value to a UI component
            task_name_fld.setText(task.getName());
            progress_slider.setValue(this.task.getProgress());
            progress_fld.setText(String.valueOf(this.task.getProgress()));
            if(this.task.getDeadline() == null){
                deadline_d_picker.setValue(null);
            }else{
                deadline_d_picker.setValue(this.task.getDeadline().toLocalDate());
            }

            // Assign an event listener to a UI component
            progress_slider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
                onChangeProgressSlider(newVal);
            });
            progress_fld.setOnAction(event -> {
                onChangeProgressField();
            });
            done_btn.setOnAction(event -> {
                onDoneBtnClicked();
            });
        }
    }

    public void onDoneBtnClicked(){
        // Data scrubbing of UI component values
        String taskName;
        if(Objects.equals(task_name_fld.getText(), "")){
            taskName = "No Name";
        }else{
            taskName = task_name_fld.getText();
        }
        int progress;
        if(Objects.equals(progress_fld.getText(), "")){
            progress = 0;
        }else{
            try {
                progress = Integer.parseInt(progress_fld.getText());
            }catch (Exception e){
                progress = 0;
            }
        }
        LocalDateTime deadline;
        if(deadline_d_picker.getValue() == null){
            deadline = null;
        }else{
            deadline = deadline_d_picker.getValue().atStartOfDay();
        }
        Task newTask = new Task(this.task.getId(), taskName, progress, deadline, this.task.getCompleted(),
                this.task.getCompletedDate(), this.task.getArchived());
        Model.getInstance().updateObTask(newTask);
        if(this.task.getCompleted()){
            Model.getInstance().updateObCompletedTask(newTask);
        }

        Model.getInstance().getDatabaseDriver().updateTask(newTask);
    }

    private void onChangeProgressSlider(Number newProgressVal){
        progress_fld.setText(String.valueOf(newProgressVal.intValue()));
    }

    private void onChangeProgressField(){
        double progressValue;
        if(Objects.equals(progress_fld.getText(), "")){
            progressValue = 0d;
        }else{
            try {
                progressValue = Double.parseDouble(progress_fld.getText());
            }catch (Exception e){
                progressValue = 0d;
            }
        }
        progress_slider.setValue(Double.parseDouble(progress_fld.getText()));
    }
}
