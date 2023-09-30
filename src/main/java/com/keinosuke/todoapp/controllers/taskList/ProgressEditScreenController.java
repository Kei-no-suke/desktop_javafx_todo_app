package com.keinosuke.todoapp.controllers.taskList;

import com.keinosuke.todoapp.models.Model;
import com.keinosuke.todoapp.models.Task;
import com.keinosuke.todoapp.utils.TimeConverter;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProgressEditScreenController implements Initializable {
    public Slider progress_slider;
    public TextField progress_fld;
    public Button save_btn;

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

        // Assign a value to a UI component
        progress_fld.setText(String.valueOf(this.task.getProgress()));
        progress_slider.setValue(this.task.getProgress());

        // Assign an event listener to a UI component
        progress_slider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
            onChangeProgressSlider(newVal);
        });
        progress_fld.setOnAction(event -> {
            onChangeProgressField();
        });
        save_btn.setOnAction(event -> {
            onSaveBtnClicked();
        });
    }

    private void onSaveBtnClicked(){
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
        Task newTask = new Task(task.getId(), task.getName(), progress, task.getDeadline(),
                task.getCompleted(), task.getCompletedDate(), task.getArchived());
        Optional<Task> removeTask = Model.getInstance().getObTasks().stream().filter(taskElm -> taskElm.getId() == task.getId()).findFirst();
        if(removeTask.isPresent()){
            Model.getInstance().getObTasks().remove(removeTask.get());
            Model.getInstance().getObTasks().add(newTask);
            Model.getInstance().getObTasks().sort(Task.getDeadlineComparator());
            Model.getInstance().getDatabaseDriver().updateTask(newTask);
        }
    }

    private void onChangeProgressSlider(Number newProgressVal){
//        Task newTask = new Task(task.getId(), task.getName(), newProgressVal.intValue(), task.getDeadline(),
//                task.getCompleted(), task.getCompletedDate(), task.getArchived());
//        setTask(newTask);
        progress_fld.setText(String.valueOf(newProgressVal.intValue()));
    }

    private void onChangeProgressField(){
//        Task newTask = new Task(task.getId(), task.getName(), Integer.parseInt(progress_fld.getText()), task.getDeadline(),
//                task.getCompleted(), task.getCompletedDate(), task.getArchived());
//        setTask(newTask);
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
        progress_slider.setValue(progressValue);
    }
}
