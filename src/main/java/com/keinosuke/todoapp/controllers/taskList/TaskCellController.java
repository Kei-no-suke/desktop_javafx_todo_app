package com.keinosuke.todoapp.controllers.taskList;

import com.keinosuke.todoapp.controllers.MainScreenController;
import com.keinosuke.todoapp.models.Model;
import com.keinosuke.todoapp.models.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class TaskCellController implements Initializable {
    public AnchorPane root;
    public CheckBox is_completed_box;
    public Button delete_btn;
    public Button archive_btn;
    public Button edit_btn;
    public ProgressBar progress_bar;
    public Tooltip progress_tooltip;

    private Task task;

    public Label taskNameLabel;
    public TextField taskNameTextField;

    public HBox task_name_lbl_box;
    public Label deadlineLabel;
    public HBox deadline_box;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        taskNameLabel = new Label();
        taskNameTextField = new TextField();
        deadlineLabel = new Label();
    }

    public Task getTask() {
        return task;
    }

    private void setTask(Task task){
        this.task = task;
    }

    // Invoked first after load
    public void setTaskFirst(Task task) {
        setTask(task);
        if(this.task != null){
            is_completed_box.setSelected(this.task.getCompleted());
            progress_bar.setProgress((double) this.task.getProgress() / 100);
            progress_tooltip.setText("Progress: " + this.task.getProgress() + " % ダブルクリックして編集");

            setTaskNameLabel(this.task);
            setTaskNameTextField(this.task);
            setTaskDeadline(this.task);
            task_name_lbl_box.getChildren().add(taskNameLabel);

            edit_btn.setOnAction(event -> {
                onActivateTaskEditScreen();
            });
            archive_btn.setOnAction(event -> {
                onArchiveBtnClicked();
            });
            delete_btn.setOnAction(event -> {
                Model.getInstance().getDatabaseDriver().deleteTask(this.task.getId());
                Model.getInstance().getObTasks().remove(this.task);
                if(this.task.getCompleted()){
                    Model.getInstance().getObCompletedTasks().remove(this.task);
                }
            });
            progress_bar.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2){
                    onActivateProgressEditScreen();
                }
            });
            is_completed_box.setOnAction(event -> {
                onChangeCompleted();
            });
            taskNameTextField.setOnAction(event -> {
                onChangeTaskName();
            });
            taskNameLabel.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2){
                    onActivateTaskNameTextField();
                }
            });
        }else{
            is_completed_box.setSelected(false);
            taskNameLabel.setText("Task Name");
            delete_btn.setDisable(true);
            progress_bar.setProgress(0.0d);
            progress_tooltip.setText("Progress: 0 % ダブルクリックして編集");
        }
    }

    private void onArchiveBtnClicked(){
        Model.getInstance().moveObTaskToObArchivedTask(task);
    }

    private void onChangeCompleted(){
        if(is_completed_box.isSelected()){
            Model.getInstance().moveObTaskToObCompletedTask(task);
        }else{
            Model.getInstance().moveObCompletedTaskToObTask(task);
        }
    }

    private void onChangeTaskName(){
        Task newTask;
        if(Objects.equals(taskNameTextField.getText(), "")){
            newTask = new Task(task.getId(), "No Name", task.getProgress(), task.getDeadline(),
                    task.getCompleted(), task.getCompletedDate(), task.getArchived());
        }else{
            newTask = new Task(task.getId(), taskNameTextField.getText(), task.getProgress(), task.getDeadline(),
                    task.getCompleted(), task.getCompletedDate(), task.getArchived());
        }
        setTask(newTask);
        task_name_lbl_box.getChildren().remove(taskNameTextField);
        updateTaskNameLabel(newTask);
        Model.getInstance().updateObTask(newTask);
        Model.getInstance().getDatabaseDriver().updateTask(newTask);
        task_name_lbl_box.getChildren().add(taskNameLabel);
    }

    private void onActivateTaskNameTextField(){
        setTaskNameTextField(this.task);
        task_name_lbl_box.getChildren().remove(taskNameLabel);
        task_name_lbl_box.getChildren().add(taskNameTextField);
    }

    private void onActivateProgressEditScreen(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/taskList/ProgressEditScreen.fxml"));
        ProgressEditScreenController progressEditScreenController = new ProgressEditScreenController();
        loader.setController(progressEditScreenController);

        AnchorPane parentNode = null;
        try {
            parentNode = loader.load();
        }catch (Exception e){
            e.printStackTrace();
        }
        progressEditScreenController.setTaskFirst(task);
        createStage(parentNode);
    }

    private void onActivateTaskEditScreen(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/taskList/TaskEditScreen.fxml"));
        TaskEditScreenController taskEditScreenController = new TaskEditScreenController();
        loader.setController(taskEditScreenController);

        AnchorPane parentNode = null;
        try {
            parentNode = loader.load();
        }catch (Exception e){
            e.printStackTrace();
        }
        taskEditScreenController.setTaskFirst(task);
        createStage(parentNode);
    }

    private void setTaskNameLabel(Task task){
        taskNameLabel.setText(task.getName());
        taskNameLabel.setTooltip(new Tooltip("Task Name: " + task.getName() + " Double-click to edit"));
    }

    private void updateTaskNameLabel(Task task){
        try {
            Model.getInstance().getDatabaseDriver().updateTask(task);
        }catch (Exception e){
            e.printStackTrace();
        }
        setTaskNameLabel(task);
    }

    private void setTaskNameTextField(Task task){
        taskNameTextField.setText(task.getName());
        taskNameTextField.setTooltip(new Tooltip("Press Enter to confirm"));
    }

    private void setTaskDeadline(Task task){
        if(task.getDeadline() != null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String deadlineStr = task.getDeadline().format(formatter);
            deadlineLabel.setText(deadlineStr);
            deadline_box.getChildren().add(deadlineLabel);
        }
    }

    private void deleteTaskDeadline(){
        deadline_box.getChildren().remove(deadlineLabel);
    }

    private void updateTaskDeadline(Task task){
        deleteTaskDeadline();
        setTaskDeadline(task);
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
