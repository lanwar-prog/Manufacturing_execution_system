/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Materials;
import Model.Task;
import Util.DbUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TaskDao {
    
    private Connection connection;

    public TaskDao() {
        connection = DbUtil.getConnection();
    }
    
    public Task addTask(Task task) {
        int returned_id = 0;
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into task(Name) values (?)", Statement.RETURN_GENERATED_KEYS);
            // Parameters start with 1
            preparedStatement.setString(1, task.getName());
            preparedStatement.executeUpdate();
            
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                returned_id = rs.getInt(1);
            }
            
            task.setId_task(returned_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return task;
    }
    
    public void deleteTask(Integer taskId) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from task where id_task=?");
            // Parameters start with 1
            preparedStatement.setInt(1, taskId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public void updateTask(Task task) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update task set Name=? where id_task=?");
            // Parameters start with 1
            preparedStatement.setString(1, task.getName());
            preparedStatement.setObject(2, task.getId_task());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Task> getAllTask() {
        List<Task> tasks = new ArrayList<Task>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from task");
            while (rs.next()) {
                Task task = new Task();
                task.setId_task(rs.getInt("id_task"));
                task.setName(rs.getString("name"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }
    
    public Task getTaskById(Integer taskId) {
        Task task = new Task();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from task where id_task=?");
            preparedStatement.setObject(1, taskId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                task.setId_task(rs.getInt("id_task"));
                task.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return task;
    }
    
    public Task clearTask() {
        Task task = new Task();
        task.setId_task(null);
        task.setName("");
        
        return task;
    }
}
