package com.keinosuke.todoapp.models;

import com.keinosuke.todoapp.utils.TimeConverter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Logger;

public class Task {
    private final int id;
    private final String name;
    private final int progress;
    private final LocalDateTime deadline;
    private final Boolean isCompleted;
    private final LocalDateTime completedDate;
    private final Boolean isArchived;

    public Task(
            int id,
            String name,
            int progress,
            LocalDateTime deadline,
            Boolean isCompleted,
            LocalDateTime completedDate,
            Boolean isArchived
    ){
        this.id = id;
        this.name = name;
        this.progress = progress;
        this.deadline = deadline;
        this.isCompleted = isCompleted;
        this.completedDate = completedDate;
        this.isArchived = isArchived;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public int getProgress() {
        return progress;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public LocalDateTime getCompletedDate() {
        return completedDate;
    }

    public Boolean getArchived() {
        return isArchived;
    }

    public TaskDB toTaskDB(){
        Long taskDBDeadline = null;
        if(deadline != null){
            taskDBDeadline = TimeConverter.localDateTimeToLong(deadline);
        }

        Long taskDBCompletedDate = null;
        if(completedDate != null){
            taskDBCompletedDate = TimeConverter.localDateTimeToLong(completedDate);
        }

        int taskDBIsCompleted = 0;
        if(isCompleted){
            taskDBIsCompleted = 1;
        }
        int taskDBIsArchived = 0;
        if(isArchived){
            taskDBIsArchived = 1;
        }
        return new TaskDB(id, name, progress, taskDBDeadline, taskDBIsCompleted, taskDBCompletedDate, taskDBIsArchived);
    }

    public static Comparator<Task> getDeadlineComparator(){
        return (t1, t2) -> {
            Long t1DLong = null;
            Long t2DLong = null;
            if(t1.getDeadline() != null){
                t1DLong = TimeConverter.localDateTimeToLong(t1.getDeadline());
            }
            if(t2.getDeadline() != null){
                t2DLong = TimeConverter.localDateTimeToLong(t2.getDeadline());
            }
            if(Objects.equals(t1DLong, t2DLong)){
                return t1.getId() - t2.getId();
            }else{
                if(t1DLong == null){
                    return 1;
                }
                if(t2DLong == null){
                    return -1;
                }
                return Math.toIntExact(t1DLong - t2DLong);
            }
        };
    }
}
