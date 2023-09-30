package com.keinosuke.todoapp.models;

import com.keinosuke.todoapp.utils.TimeConverter;

import java.lang.reflect.Type;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class DatabaseDriver {

    private Connection connection;
    private Statement statement = null;

    // Definition of database column
    private final String dbColumnId = "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL";
    private final String dbColumnName = "name TEXT NOT NULL";
    private final String dbColumnProgress = "progress INTEGER NOT NULL DEFAULT 0";
    private final String dbColumnDeadline = "deadline INTEGER";
    private final String dbColumnIsCompleted = "isCompleted INTEGER NOT NULL DEFAULT 0";
    private final String dbColumnCompletedDate = "completedDate INTEGER";
    private final String dbColumnIsArchived = "isArchived INTEGER NOT NULL DEFAULT 0";

    // Tasks query
    private PreparedStatement getAllTasksQuery;
    private PreparedStatement getAllArchivedTasksQuery;
    private PreparedStatement getAllUnarchivedTasksQuery;
    private PreparedStatement getAllCompletedTasksQuery;
    private PreparedStatement getAllUncompletedTasksQuery;
    private PreparedStatement getTaskQuery;
    private PreparedStatement addTaskQuery;
    private PreparedStatement updateTaskQuery;
    private PreparedStatement deleteTaskQuery;
    private PreparedStatement deleteAllTasksQuery;
    public DatabaseDriver(){
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:todoApp.db");
            this.statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS tasks" +
                    "(" + dbColumnId + "," + dbColumnName + "," + dbColumnProgress + "," +
                    dbColumnDeadline + "," + dbColumnIsCompleted + "," + dbColumnCompletedDate +
                    "," + dbColumnIsArchived + ")";
            statement.setQueryTimeout(30);
            statement.executeUpdate(sql);
            getAllTasksQuery = connection.prepareStatement("SELECT * FROM tasks ORDER BY deadline");
            getAllArchivedTasksQuery = connection.prepareStatement("SELECT * FROM tasks WHERE isArchived = 1 ORDER BY deadline");
            getAllUnarchivedTasksQuery = connection.prepareStatement("SELECT * FROM tasks WHERE isArchived = 0 ORDER BY deadline");
            getAllCompletedTasksQuery = connection.prepareStatement("SELECT * FROM tasks WHERE isCompleted = 1 ORDER BY deadline");
            getAllUncompletedTasksQuery = connection.prepareStatement("SELECT * FROM tasks WHERE isCompleted = 0 ORDER BY deadline");
            getTaskQuery = connection.prepareStatement("SELECT * FROM tasks WHERE id = ?");
            addTaskQuery = connection.prepareStatement("INSERT INTO tasks(name, progress, deadline, isCompleted, completedDate, isArchived) VALUES(?,?,?,?,?,?)");
            updateTaskQuery = connection.prepareStatement("UPDATE tasks SET name = ?, progress = ?, deadline = ?, isCompleted = ?, completedDate = ?, isArchived = ? WHERE id = ?");
            deleteTaskQuery = connection.prepareStatement("DELETE FROM tasks WHERE id = ?");
            deleteAllTasksQuery = connection.prepareStatement("DELETE FROM tasks");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Task> getAllTasks(){
        List<Task> tasks = new ArrayList<>();
        try {
            ResultSet resultSet = getAllTasksQuery.executeQuery();

            tasks = resultSetToTasks(resultSet);
        }catch (Exception e){
            e.printStackTrace();
        }
        return tasks;
    }

    public List<Task> getAllArchivedTasks(){
        List<Task> tasks = new ArrayList<>();
        try {
            ResultSet resultSet = getAllArchivedTasksQuery.executeQuery();

            tasks = resultSetToTasks(resultSet);
        }catch (Exception e){
            e.printStackTrace();
        }
        return tasks;
    }

    public List<Task> getAllUnarchivedTasks(){
        List<Task> tasks = new ArrayList<>();
        try {
            ResultSet resultSet = getAllUnarchivedTasksQuery.executeQuery();

            tasks = resultSetToTasks(resultSet);
        }catch (Exception e){
            e.printStackTrace();
        }
        return tasks;
    }

    public List<Task> getAllCompletedTasks(){
        List<Task> tasks = new ArrayList<>();
        try {
            ResultSet resultSet = getAllCompletedTasksQuery.executeQuery();

            tasks = resultSetToTasks(resultSet);
        }catch (Exception e){
            e.printStackTrace();
        }
        return tasks;
    }

    public List<Task> getAllUncompletedTasks(){
        List<Task> tasks = new ArrayList<>();
        try {
            ResultSet resultSet = getAllUncompletedTasksQuery.executeQuery();

            tasks = resultSetToTasks(resultSet);
        }catch (Exception e){
            e.printStackTrace();
        }
        return tasks;
    }

    public Task getTask(int id){
        Task task = null;
        try {
            getTaskQuery.setInt(1, id);
            ResultSet resultSet = getTaskQuery.executeQuery();

            task = resultSetToTask(resultSet);
            getTaskQuery.clearParameters();
        }catch (Exception e){
            e.printStackTrace();
        }
        return task;
    }

    public void addTask(Task task){
        TaskDB taskDB = task.toTaskDB();
        try {
            addTaskQuery.setString(1, taskDB.getName());
            addTaskQuery.setInt(2, taskDB.getProgress());
            if(taskDB.getDeadline() == null){
                addTaskQuery.setNull(3, Types.INTEGER);
            }else{
                addTaskQuery.setLong(3, taskDB.getDeadline());
            }
            addTaskQuery.setInt(4, taskDB.getCompleted());
            if(taskDB.getCompletedDate() == null){
                addTaskQuery.setNull(5, Types.INTEGER);
            }else{
                addTaskQuery.setLong(5, taskDB.getCompletedDate());
            }
            addTaskQuery.setLong(6, taskDB.getArchived());
            addTaskQuery.executeUpdate();
            addTaskQuery.clearParameters();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateTask(Task task){
        TaskDB taskDB = task.toTaskDB();
        try {
            updateTaskQuery.setString(1, taskDB.getName());
            updateTaskQuery.setInt(2, taskDB.getProgress());
            if(taskDB.getDeadline() == null){
                updateTaskQuery.setNull(3, Types.INTEGER);
            }else{
                updateTaskQuery.setLong(3, taskDB.getDeadline());
            }
            updateTaskQuery.setInt(4, taskDB.getCompleted());
            if(taskDB.getCompletedDate() == null){
                updateTaskQuery.setNull(5, Types.INTEGER);
            }else{
                updateTaskQuery.setLong(5, taskDB.getCompletedDate());
            }
            updateTaskQuery.setLong(6, taskDB.getArchived());
            updateTaskQuery.setInt(7, taskDB.getId());
            updateTaskQuery.executeUpdate();
            updateTaskQuery.clearParameters();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteTask(int id){
        try {
            deleteTaskQuery.setInt(1, id);
            deleteTaskQuery.executeUpdate();
            deleteTaskQuery.clearParameters();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteAllTasks(){
        try {
            deleteAllTasksQuery.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private List<Task> resultSetToTasks(ResultSet resultSet) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        while (resultSet.next()){

            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int progress = resultSet.getInt("progress");

            Long deadline = resultSet.getLong("deadline");
            if(resultSet.wasNull()){
                deadline = null;
            }

            int isCompleted = resultSet.getInt("isCompleted");

            Long completedDate = resultSet.getLong("completedDate");
            if(resultSet.wasNull()){
                completedDate = null;
            }

            int isArchived = resultSet.getInt("isArchived");

            tasks.add(new TaskDB(id, name, progress, deadline, isCompleted, completedDate, isArchived).toTask());
        }
        return tasks;
    }

    private Task resultSetToTask(ResultSet resultSet) throws SQLException {
        Task task = null;
        while (resultSet.next()){

            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int progress = resultSet.getInt("progress");

            Long deadline = resultSet.getLong("deadline");
            if(resultSet.wasNull()){
                deadline = null;
            }

            int isCompleted = resultSet.getInt("isCompleted");

            Long completedDate = resultSet.getLong("completedDate");
            if(resultSet.wasNull()){
                completedDate = null;
            }

            int isArchived = resultSet.getInt("isArchived");

            task = new TaskDB(id, name, progress, deadline, isCompleted, completedDate, isArchived).toTask();
        }
        return task;
    }

}
