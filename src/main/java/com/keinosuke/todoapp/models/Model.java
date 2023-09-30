package com.keinosuke.todoapp.models;

import com.keinosuke.todoapp.views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Optional;

public class Model {
    private static Model model;

    private final ViewFactory viewFactory;
    private final DatabaseDriver databaseDriver;

    private final ObservableList<Task> obTasks;
    private final ObservableList<Task> obArchivedTasks;
    private final ObservableList<Task> obCompletedTasks;

    private Model() {
        this.viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();

        // unarchived task list
        List<Task> tasks = this.databaseDriver.getAllUnarchivedTasks();
        this.obTasks = FXCollections.observableList(tasks);
        this.obTasks.sort(Task.getDeadlineComparator());

        // archived task list
        List<Task> archivedTasks = this.databaseDriver.getAllArchivedTasks();
        this.obArchivedTasks = FXCollections.observableList(archivedTasks);
        this.obArchivedTasks.sort(Task.getDeadlineComparator());

        // completed task list
        List<Task> completedTasks = this.databaseDriver.getAllCompletedTasks();
        this.obCompletedTasks = FXCollections.observableList(completedTasks);
        this.obCompletedTasks.sort(Task.getDeadlineComparator());
    }

    public static synchronized Model getInstance(){
        if(model == null){
            model = new Model();
        }
        return model;
    }
    public ViewFactory getViewFactory(){
        return viewFactory;
    }
    public DatabaseDriver getDatabaseDriver() {return databaseDriver;}
    public ObservableList<Task> getObTasks(){
        return obTasks;
    }
    public ObservableList<Task> getObArchivedTasks(){
        return obArchivedTasks;
    }
    public ObservableList<Task> getObCompletedTasks(){
        return obCompletedTasks;
    }
    public void updateObTask(Task task){
        Optional<Task> removeTask = Model.getInstance().getObTasks().stream().filter(taskElm -> taskElm.getId() == task.getId()).findFirst();
        if(removeTask.isPresent()){
            Model.getInstance().getObTasks().remove(removeTask.get());
            Model.getInstance().getObTasks().add(task);
            Model.getInstance().getObTasks().sort(Task.getDeadlineComparator());
        }
    }

    public void updateObArchivedTask(Task task){
        Optional<Task> removeTask = Model.getInstance().getObArchivedTasks().stream().filter(taskElm -> taskElm.getId() == task.getId()).findFirst();
        if(removeTask.isPresent()){
            Model.getInstance().getObArchivedTasks().remove(removeTask.get());
            Model.getInstance().getObArchivedTasks().add(task);
            Model.getInstance().getObArchivedTasks().sort(Task.getDeadlineComparator());
        }
    }

    public void updateObCompletedTask(Task task){
        Optional<Task> removeTask = Model.getInstance().getObCompletedTasks().stream().filter(taskElm -> taskElm.getId() == task.getId()).findFirst();
        if(removeTask.isPresent()){
            Model.getInstance().getObCompletedTasks().remove(removeTask.get());
            Model.getInstance().getObCompletedTasks().add(task);
            Model.getInstance().getObCompletedTasks().sort(Task.getDeadlineComparator());
        }
    }

    public void removeObTask(Task task){
        Optional<Task> removeTask = Model.getInstance().getObTasks().stream().filter(taskElm -> taskElm.getId() == task.getId()).findFirst();
        removeTask.ifPresent(value -> Model.getInstance().getObTasks().remove(value));
    }

    public void removeObArchivedTask(Task task){
        Optional<Task> removeTask = Model.getInstance().getObArchivedTasks().stream().filter(taskElm -> taskElm.getId() == task.getId()).findFirst();
        removeTask.ifPresent(value -> Model.getInstance().getObArchivedTasks().remove(value));
    }

    public void removeObCompletedTask(Task task){
        Optional<Task> removeTask = Model.getInstance().getObCompletedTasks().stream().filter(taskElm -> taskElm.getId() == task.getId()).findFirst();
        removeTask.ifPresent(value -> Model.getInstance().getObCompletedTasks().remove(value));
    }

    // ObTask -> ObArchivedTask
    public void moveObTaskToObArchivedTask(Task task){
        removeObTask(task);
        if(task.getCompleted()){
            removeObCompletedTask(task);
        }
        Task newTask = new Task(task.getId(), task.getName(), task.getProgress(), task.getDeadline(),
                task.getCompleted(), task.getCompletedDate(), true);
        this.obArchivedTasks.add(newTask);
        this.obArchivedTasks.sort(Task.getDeadlineComparator());
        this.databaseDriver.updateTask(newTask);
    }

    // ObArchivedTask -> ObTask
    public void moveObArchivedTaskToObTask(Task task){
        removeObArchivedTask(task);
        Task newTask = new Task(task.getId(), task.getName(), task.getProgress(), task.getDeadline(),
                task.getCompleted(), task.getCompletedDate(), false);
        this.obTasks.add(newTask);
        this.obTasks.sort(Task.getDeadlineComparator());
        if(task.getCompleted()){
            this.obCompletedTasks.add(newTask);
            this.obCompletedTasks.sort(Task.getDeadlineComparator());
        }
        this.databaseDriver.updateTask(newTask);
    }

    // ObCompletedTask -> ObArchivedTask
    public void moveObCompletedTaskToObArchivedTask(Task task){
        removeObTask(task);
        removeObCompletedTask(task);
        Task newTask = new Task(task.getId(), task.getName(), task.getProgress(), task.getDeadline(),
                task.getCompleted(), task.getCompletedDate(), true);
        this.obArchivedTasks.add(newTask);
        this.obArchivedTasks.sort(Task.getDeadlineComparator());
        this.databaseDriver.updateTask(newTask);
    }

    // ObArchivedTask -> ObCompletedTask
//    public void moveObArchivedTaskToObCompletedTask(Task task){
//        removeObArchivedTask(task);
//        Task newTask = new Task(task.getId(), task.getName(), task.getProgress(), task.getDeadline(),
//                task.getCompleted(), task.getCompletedDate(), false);
//        this.obTasks.add(newTask);
//        this.obTasks.sort(Task.getDeadlineComparator());
//        this.obCompletedTasks.add(newTask);
//        this.obCompletedTasks.sort(Task.getDeadlineComparator());
//        this.databaseDriver.updateTask(newTask);
//    }

    // ObCompletedTask -> ObTask
    public void moveObCompletedTaskToObTask(Task task){
        removeObCompletedTask(task);
        Task newTask = new Task(task.getId(), task.getName(), task.getProgress(), task.getDeadline(),
                false, task.getCompletedDate(), task.getArchived());
        updateObTask(newTask);
        this.databaseDriver.updateTask(newTask);
    }

    // ObTask -> ObCompletedTask
    public void moveObTaskToObCompletedTask(Task task){
        Task newTask = new Task(task.getId(), task.getName(), task.getProgress(), task.getDeadline(),
                true, task.getCompletedDate(), task.getArchived());
        updateObTask(newTask);
        this.obCompletedTasks.add(newTask);
        this.obCompletedTasks.sort(Task.getDeadlineComparator());
        this.databaseDriver.updateTask(newTask);
    }
}
