/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Detail;
import Model.Workplace;
import Util.DbUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkplaceDao {
    
    private Connection connection;

    public WorkplaceDao() {
        connection = DbUtil.getConnection();
    }
    
        public Workplace addWorkplace(Workplace workplace) {
        int returned_id = 0;
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into workplace(Name,Created) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
            // Parameters start with 1
            preparedStatement.setString(1, workplace.getName());
            preparedStatement.setDate(2, new java.sql.Date(workplace.getCreated().getTime()));
            preparedStatement.executeUpdate();
            
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                returned_id = rs.getInt(1);
            }
            
            workplace.setId_workplace(returned_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return workplace;
    }
    
    public void deleteWorkplace(Integer workplaceId) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from workplace where id_workplace=?");
            // Parameters start with 1
            preparedStatement.setInt(1, workplaceId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public void updateWorkplace(Workplace workplace) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update workplace set Name=?, Created=? where id_workplace=?");
            // Parameters start with 1
            preparedStatement.setString(1, workplace.getName());
            preparedStatement.setDate(2, new java.sql.Date(workplace.getCreated().getTime()));
            preparedStatement.setObject(3, workplace.getId_workplace());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Workplace> getAllWorkplace() {
        List<Workplace> workplaces = new ArrayList<Workplace>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from workplace");
            while (rs.next()) {
                Workplace workplace = new Workplace();
                workplace.setId_workplace(rs.getInt("id_workplace"));
                workplace.setName(rs.getString("name"));
                workplace.setCreated(rs.getDate("created"));
                workplaces.add(workplace);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workplaces;
    }
    
    public Workplace getWorkplaceById(Integer workplaceId) {
        Workplace workplace = new Workplace();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from workplace where id_workplace=?");
            preparedStatement.setObject(1, workplaceId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                workplace.setId_workplace(rs.getInt("id_workplace"));
                workplace.setName(rs.getString("name"));
                workplace.setCreated(rs.getDate("created"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workplace;
    }
    
    public Workplace clearWorkplace() {
        Workplace workplace = new Workplace();
        workplace.setId_workplace(null);
        workplace.setName("");
        workplace.setCreated(new Date());
        
        return workplace;
    }
}
