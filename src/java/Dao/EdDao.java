/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Ed;
import Util.DbUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EdDao {
        
    private Connection connection;

    public EdDao() {
        connection = DbUtil.getConnection();
    }
    
    public List<Ed> getAllEd() {
        List<Ed> eds = new ArrayList<Ed>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from ed");
            while (rs.next()) {
                Ed ed = new Ed();
                ed.setId_ed(rs.getInt("id_ed"));
                ed.setName(rs.getString("name"));
                ed.setDescription(rs.getString("description"));
                eds.add(ed);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eds;
    }
}
