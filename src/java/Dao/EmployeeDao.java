/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Employee;
import Util.DbUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeDao {
        
    private Connection connection;

    public EmployeeDao() {
        connection = DbUtil.getConnection();
    }
    
        public Employee addEmployee(Employee employee) {
        int returned_id = 0;
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into employee(Name,Surname,Login,Role,Password,Phone,Datehider) values (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            // Parameters start with 1
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getSurname());
            preparedStatement.setString(3, employee.getLogin());
            preparedStatement.setObject(4, employee.getRole());
            preparedStatement.setString(5, employee.getPassword());
            preparedStatement.setString(6, employee.getPhone());
            preparedStatement.setDate(7, new java.sql.Date(employee.getDatehider().getTime()));
            preparedStatement.executeUpdate();
            
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                returned_id = rs.getInt(1);
            }
            
            employee.setId_employee(returned_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return employee;
    }
    
    public void deleteEmployee(Integer employeeId) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from employee where id_employee=?");
            // Parameters start with 1
            preparedStatement.setInt(1, employeeId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
       
    }
    
    public void updateEmployee(Employee employee) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update employee set Name=?, Surname=?, Login=?, Role=?, Password=?, Phone=?, Datehider=? where id_employee=?");
            // Parameters start with 1
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getSurname());
            preparedStatement.setString(3, employee.getLogin());
            preparedStatement.setObject(4, employee.getRole());
            preparedStatement.setString(5, employee.getPassword());
            preparedStatement.setString(6, employee.getPhone());
            preparedStatement.setDate(7, new java.sql.Date(employee.getDatehider().getTime()));
            preparedStatement.setObject(8, employee.getId_employee());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Employee> getAllEmployee() {
        List<Employee> employees = new ArrayList<Employee>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from employee");
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId_employee(rs.getInt("id_employee"));
                employee.setName(rs.getString("name"));
                employee.setSurname(rs.getString("surname"));
                employee.setLogin(rs.getString("login"));
                employee.setRole(rs.getInt("role"));
                employee.setPassword(rs.getString("password"));
                employee.setPhone(rs.getString("phone"));
                employee.setDatehider(rs.getDate("datehider"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }
    
    public Employee getEmployeeById(Integer employeeId) {
        Employee employee = new Employee();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from employee where id_employee=?");
            preparedStatement.setObject(1, employeeId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                employee.setId_employee(rs.getInt("id_employee"));
                employee.setName(rs.getString("name"));
                employee.setSurname(rs.getString("surname"));
                employee.setLogin(rs.getString("login"));
                employee.setRole(rs.getInt("role"));
                employee.setPassword(rs.getString("password"));
                employee.setPhone(rs.getString("phone"));
                employee.setDatehider(rs.getDate("datehider"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employee;
    }
    
    public Employee clearEmployee() {
        Employee employee = new Employee();
        employee.setId_employee(null);
        employee.setName("");
        employee.setSurname("");
        employee.setLogin("");
        employee.setRole(null);
        employee.setPassword("");
        employee.setPhone("");
        employee.setDatehider(new Date());
        
        return employee;
    }
}
