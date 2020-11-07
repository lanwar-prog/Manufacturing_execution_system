/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Operation;
import Util.DbUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OperationDao {
    
    private Connection connection;

    public OperationDao() {
        connection = DbUtil.getConnection();
    }
    
    public Operation addOperation(Operation operation) {
        int returned_id = 0;
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into operation(Name,Time,Timesetup,Timecompletion,id_tas) values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            // Parameters start with 1
            preparedStatement.setString(1, operation.getName());
            preparedStatement.setObject(2, operation.getTime());
            preparedStatement.setObject(3, operation.getTimesetup());
            preparedStatement.setObject(4, operation.getTimecompletion());
            preparedStatement.setObject(5, operation.getId_tas());
            preparedStatement.executeUpdate();
            
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                returned_id = rs.getInt(1);
            }
            
            operation.setId_operation(returned_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return operation;
    }
    
    public void deleteOperation(Integer operationId) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from operation where id_operation=?");
            // Parameters start with 1
            preparedStatement.setInt(1, operationId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public void updateOperation(Operation operation) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update operation set Name=?, Time=?, Timesetup=?, Timecompletion=?, id_tas=? where id_operation=?");
            // Parameters start with 1
            preparedStatement.setString(1, operation.getName());
            preparedStatement.setObject(2, operation.getTime());
            preparedStatement.setObject(3, operation.getTimesetup());
            preparedStatement.setObject(4, operation.getTimecompletion());
            preparedStatement.setObject(5, operation.getId_tas());
            preparedStatement.setObject(6, operation.getId_operation());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Operation> getAllOperation() {
        List<Operation> operations = new ArrayList<Operation>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select o.*, t.name as taskName from operation o left join task t on t.id_task = o.id_tas");
            while (rs.next()) {
                Operation operation = new Operation();
                operation.setId_operation(rs.getInt("id_operation"));
                operation.setName(rs.getString("name"));
                operation.setTime(rs.getInt("time"));
                operation.setTimesetup(rs.getInt("timesetup"));
                operation.setTimecompletion(rs.getInt("timecompletion"));
                operation.setId_tas(rs.getInt("id_tas"));
                operation.setTaskname(rs.getString("taskname"));
                operations.add(operation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return operations;
    }
    
    public Operation getOperationById(Integer operationId) {
        Operation operation = new Operation();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from operation where id_operation=?");
            preparedStatement.setObject(1, operationId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                operation.setId_operation(rs.getInt("id_operation"));
                operation.setName(rs.getString("Name"));
                operation.setTime(rs.getInt("Time"));
                operation.setTimesetup(rs.getInt("Timesetup"));
                operation.setTimecompletion(rs.getInt("Timecompletion"));
                operation.setId_tas(rs.getInt("id_tas"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return operation;
    }
    
    public Operation clearOperation() {
        Operation operation = new Operation();
        operation.setId_operation(null);
        operation.setName("");
        operation.setTime(null);
        operation.setTimesetup(null);
        operation.setTimecompletion(null);
        operation.setId_tas(null);
        operation.setTaskname("");
        
        return operation;
    }
}
