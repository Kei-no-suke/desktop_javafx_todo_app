package com.keinosuke.todoapp.controllers;

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
import java.util.ResourceBundle;

public class AddTaskScreenController implements Initializable {

    public TextField task_name_fld;
    public Slider progress_slider;
    public TextField progress_fld;
    public DatePicker deadline_d_picker;
    public Button add_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        progress_slider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
            onChangeProgressSlider(newVal);
        });
        progress_fld.setOnAction(event -> {
            onChangeProgressField();
        });

        add_btn.setOnAction(event -> {
            onAddBtnClicked();
        });
    }

    public void onAddBtnClicked(){
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
        Task task = new Task(1, taskName, progress, deadline, false,
                null, false);
        Model.getInstance().getDatabaseDriver().addTask(task);
        List<Task> tasks = Model.getInstance().getDatabaseDriver().getAllTasks();
        ObservableList<Task> obTasks = Model.getInstance().getObTasks();
        obTasks.clear();
        obTasks.addAll(tasks);

        task_name_fld.setText("");
        progress_slider.setValue(0f);
        progress_fld.setText("");
        deadline_d_picker.setValue(null);
    }

    private void onChangeProgressSlider(Number newProgressVal){
        progress_fld.setText(String.valueOf(newProgressVal.intValue()));
    }

    private void onChangeProgressField(){
        progress_slider.setValue(Double.parseDouble(progress_fld.getText()));
    }
}
