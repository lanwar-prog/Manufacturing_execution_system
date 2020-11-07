/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Util.DbUtil;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageDao {
    
    private Connection connection;

    public ImageDao() {
        connection = DbUtil.getConnection();
    }
    
        public Blob getImageById(Integer detailId) {
            Blob image = null;
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select image from detail where id_detail=?");
            preparedStatement.setObject(1, detailId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                image = rs.getBlob("image");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return image;
    }
}
