/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Client;
import Model.Supplier;
import Util.DbUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SupplierDao {
    
    private Connection connection;

    public SupplierDao() {
        connection = DbUtil.getConnection();
    }
    
    public Supplier addSupplier(Supplier supplier) {
        int returned_id = 0;
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into supplier(Name,Telephone,City,Minname,Street,NumberHouse,Email) values (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            // Parameters start with 1
            preparedStatement.setString(1, supplier.getName());
            preparedStatement.setString(2, supplier.getTelephone());
            preparedStatement.setString(3, supplier.getCity());
            preparedStatement.setString(4, supplier.getMinname());
            preparedStatement.setString(5, supplier.getStreet());
            preparedStatement.setString(6, supplier.getNumberhouse());
            preparedStatement.setString(7, supplier.getEmail());
            preparedStatement.executeUpdate();
            
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                returned_id = rs.getInt(1);
            }
            
            supplier.setId_supplier(returned_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return supplier;
    }
    
    public void deleteSupplier(Integer supplierId) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from supplier where id_supplier=?");
            // Parameters start with 1
            preparedStatement.setInt(1, supplierId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
       
    }
    
    public void updateSupplier(Supplier supplier) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update supplier set Name=?, Telephone=?, City=?, Minname=?, Street=?, NumberHouse=?, Email=? where id_supplier=?");
            // Parameters start with 1
            preparedStatement.setString(1, supplier.getName());
            preparedStatement.setString(2, supplier.getTelephone());
            preparedStatement.setString(3, supplier.getCity());
            preparedStatement.setString(4, supplier.getMinname());
            preparedStatement.setString(5, supplier.getStreet());
            preparedStatement.setString(6, supplier.getNumberhouse());
            preparedStatement.setString(7, supplier.getEmail());
            preparedStatement.setObject(8, supplier.getId_supplier());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Supplier> getAllSupplier() {
        List<Supplier> suppliers = new ArrayList<Supplier>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from supplier");
            while (rs.next()) {
                Supplier supplier = new Supplier();
                supplier.setId_supplier(rs.getInt("id_supplier"));
                supplier.setName(rs.getString("name"));
                supplier.setMinname(rs.getString("minname"));
                supplier.setCity(rs.getString("city"));
                supplier.setStreet(rs.getString("street"));
                supplier.setNumberhouse(rs.getString("numberhouse"));
                supplier.setTelephone(rs.getString("telephone"));
                supplier.setEmail(rs.getString("email"));
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return suppliers;
    }
    
    public Supplier getSupplierById(Integer supplierId) {
        Supplier supplier = new Supplier();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from supplier where id_supplier=?");
            preparedStatement.setObject(1, supplierId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                supplier.setId_supplier(rs.getInt("id_supplier"));
                supplier.setName(rs.getString("name"));
                supplier.setTelephone(rs.getString("telephone"));
                supplier.setCity(rs.getString("city"));
                supplier.setMinname(rs.getString("minname"));
                supplier.setStreet(rs.getString("street"));
                supplier.setNumberhouse(rs.getString("numberhouse"));
                supplier.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return supplier;
    }
    
    public Supplier clearSupplier() {
        Supplier supplier = new Supplier();
        supplier.setId_supplier(null);
        supplier.setName("");
        supplier.setTelephone("");
        supplier.setCity("");
        supplier.setMinname("");
        supplier.setStreet("");
        supplier.setNumberhouse("");
        supplier.setEmail("");
        
        return supplier;
    }
}
