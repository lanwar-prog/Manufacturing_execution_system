/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Detail;
import Util.DbUtil;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailDao {
    
    private Connection connection;

    public DetailDao() {
        connection = DbUtil.getConnection();
    }
    
        public Detail addDetail(Detail detail) {
        int returned_id = 0;
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into detail(Name,Description,Created, Image) values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            // Parameters start with 1
            preparedStatement.setString(1, detail.getName());
            preparedStatement.setString(2, detail.getDescription());
            preparedStatement.setDate(3, new java.sql.Date(detail.getCreated().getTime()));
            preparedStatement.setBlob(4, detail.getImage());
            preparedStatement.executeUpdate();
            
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                returned_id = rs.getInt(1);
            }
            
            detail.setId_detail(returned_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return detail;
    }
    
    public void deleteDetail(Integer detailId) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from detail where id_detail=?");
            // Parameters start with 1
            preparedStatement.setInt(1, detailId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
       
    }
    
    public void deleteImage(Integer detailId) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update detail set image=null where id_detail=?");
            // Parameters start with 1
            preparedStatement.setInt(1, detailId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public void updateDetail(Detail detail) {
        try {
            PreparedStatement preparedStatement = detail.getImage() == null ?
                    connection.prepareStatement("update detail set Name=?, Description=?, Created=? where id_detail=?") :
                    connection.prepareStatement("update detail set Name=?, Description=?, Created=?, Image=? where id_detail=?");
            // Parameters start with 1
            preparedStatement.setString(1, detail.getName());
            preparedStatement.setString(2, detail.getDescription());
            preparedStatement.setDate(3, new java.sql.Date(detail.getCreated().getTime()));
            if (detail.getImage() == null) {
                preparedStatement.setObject(4, detail.getId_detail());
            } else {
                preparedStatement.setBlob(4, detail.getImage());
                preparedStatement.setObject(5, detail.getId_detail());
            }
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Detail> getAllDetails() {
        List<Detail> details = new ArrayList<Detail>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from detail");
            while (rs.next()) {
                Detail detail = new Detail();
                detail.setId_detail(rs.getInt("id_detail"));
                detail.setName(rs.getString("name"));
                detail.setDescription(rs.getString("description"));
                detail.setCreated(rs.getDate("created"));
                Blob img = rs.getBlob("image");
                detail.setImage(img == null ? null : img.getBinaryStream(1, img.length()));
                details.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return details;
    }
    
    public Detail getDetailById(Integer detailId) {
        Detail detail = new Detail();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from detail where id_detail=?");
            preparedStatement.setObject(1, detailId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                detail.setId_detail(rs.getInt("id_detail"));
                detail.setName(rs.getString("name"));
                detail.setDescription(rs.getString("description"));
                detail.setCreated(rs.getDate("created"));
                Blob img = rs.getBlob("image");
                detail.setImage(img == null ? null : img.getBinaryStream(1, img.length()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return detail;
    }
    
    public Detail clearDetail() {
        Detail detail = new Detail();
        detail.setId_detail(null);
        detail.setName("");
        detail.setDescription("");
        detail.setCreated(new Date());
        detail.setImage(null);
        
        return detail;
    }
}
