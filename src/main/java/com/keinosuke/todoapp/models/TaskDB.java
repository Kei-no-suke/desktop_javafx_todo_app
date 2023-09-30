package com.keinosuke.todoapp.models;

import com.keinosuke.todoapp.utils.TimeConverter;

import java.time.LocalDateTime;

public class TaskDB {
    private final int id;
    private final String name;
    private final int progress;
    private final Long deadline;
    private final int isCompleted;
    private final Long completedDate;
    private final int isArchived;

    public TaskDB(
            int id,
            String name,
            int progress,
            Long deadline,
            int isCompleted,
            Long completedDate,
            int isArchived
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

    public Long getDeadline() {
        return deadline;
    }

    public int getCompleted() {
        return isCompleted;
    }

    public Long getCompletedDate() {
        return completedDate;
    }

    public int getArchived() {
        return isArchived;
    }

    public Task toTask(){
        LocalDateTime taskDeadline = null;
        if(deadline != null){
            taskDeadline = TimeConverter.longToLocalDateTime(deadline);
        }

        LocalDateTime taskCompletedDate = null;
        if(completedDate != null){
            taskCompletedDate = TimeConverter.longToLocalDateTime(completedDate);
        }

        Boolean taskIsCompleted = isCompleted != 0;
        Boolean taskIsArchived = isArchived != 0;

        return new Task(id, name, progress, taskDeadline, taskIsCompleted, taskCompletedDate, taskIsArchived);
    }
}
