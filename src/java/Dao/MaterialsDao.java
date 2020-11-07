/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Model.Materials;
import Util.DbUtil;

public class MaterialsDao {
    
    private Connection connection;

    public MaterialsDao() {
        connection = DbUtil.getConnection();
    }
    
    public Materials addMaterials(Materials materials) {
        int returned_id = 0;
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into materials(Name,Number,ed_id,supplier_id) values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            // Parameters start with 1
            preparedStatement.setString(1, materials.getName());
            preparedStatement.setObject(2, materials.getNumber());
            preparedStatement.setObject(3, materials.getEd_id());
            preparedStatement.setObject(4, materials.getSupplier_id());
            preparedStatement.executeUpdate();
            
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                returned_id = rs.getInt(1);
            }
            
            materials.setId_material(returned_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return materials;
    }
    
    public void deleteMaterials(Integer materialsId) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from materials where id_material=?");
            // Parameters start with 1
            preparedStatement.setInt(1, materialsId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public void updateMaterials(Materials materials) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update materials set Name=?, Number=?, ed_id=?, supplier_id=? where id_material=?");
            // Parameters start with 1
            preparedStatement.setString(1, materials.getName());
            preparedStatement.setObject(2, materials.getNumber());
            preparedStatement.setObject(3, materials.getEd_id());
            preparedStatement.setObject(4, materials.getSupplier_id());
            preparedStatement.setObject(5, materials.getId_material());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Materials> getAllMaterials() {
        List<Materials> materials = new ArrayList<Materials>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select m.*, e.Name as ed_name, s.Name as s_name, s.City as s_city from materials m left join ed e on e.id_ed = m.ed_id left join supplier s on s.id_supplier = m.supplier_id order by m.id_material");
            while (rs.next()) {
                Materials material = new Materials();
                material.setId_material(rs.getInt("id_material"));
                material.setName(rs.getString("name"));
                material.setNumber(rs.getInt("number"));
                material.setEd_id(rs.getInt("ed_id"));
                material.setSupplier_id(rs.getInt("supplier_id"));
                material.setEd_name(rs.getString("ed_name"));
                material.setS_name(rs.getString("s_name"));
                material.setS_city(rs.getString("s_city"));
                materials.add(material);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return materials;
    }
    
    public Materials getMaterialsById(Integer materialsId) {
        Materials materials = new Materials();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from materials where id_material=?");
            preparedStatement.setObject(1, materialsId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                materials.setId_material(rs.getInt("id_material"));
                materials.setName(rs.getString("name"));
                materials.setNumber(rs.getInt("number"));
                materials.setEd_id(rs.getInt("ed_id"));
                materials.setSupplier_id(rs.getInt("supplier_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return materials;
    }
    
    public Materials clearMaterials() {
        Materials materials = new Materials();
        materials.setId_material(null);
        materials.setName("");
        materials.setNumber(null);
        materials.setEd_id(null);
        materials.setSupplier_id(null);
        materials.setEd_name("");
        materials.setS_name("");
        materials.setS_city("");
        
        return materials;
    }
}
