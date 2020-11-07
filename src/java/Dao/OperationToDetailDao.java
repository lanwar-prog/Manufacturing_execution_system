/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.OperationToDetail;
import Util.DbUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OperationToDetailDao {

    private Connection connection;

    public OperationToDetailDao() {
        connection = DbUtil.getConnection();
    }
    
    public OperationToDetail addOperationToDetail(OperationToDetail operationToDetail) {
        int returned_id = 0;
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into operationdetail(id_detail,id_operation,priority) values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            // Parameters start with 1
            preparedStatement.setObject(1, operationToDetail.getId_detail());
            preparedStatement.setObject(2, operationToDetail.getId_operation());
            preparedStatement.setObject(3, operationToDetail.getPriority());
            preparedStatement.executeUpdate();
            
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                returned_id = rs.getInt(1);
            }
            
            operationToDetail.setId_operdet(returned_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return operationToDetail;
    }
    
    public void deleteOperationToDetail(Integer operationtonDetailId) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from operationdetail where id_operdet=?");
            // Parameters start with 1
            preparedStatement.setInt(1, operationtonDetailId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public void updateOperationToDetail(OperationToDetail operationToDetail) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update operationdetail set id_detail=?, id_operation=?, priority=? where id_operdet=?");
            // Parameters start with 1
            preparedStatement.setObject(1, operationToDetail.getId_detail());
            preparedStatement.setObject(2, operationToDetail.getId_operation());
            preparedStatement.setObject(3, operationToDetail.getPriority());
            preparedStatement.setObject(4, operationToDetail.getId_operdet());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<OperationToDetail> getAllOperationToDetails(Integer detailId) {
        List<OperationToDetail> operationToDetails = new ArrayList<OperationToDetail>();
        try {
            PreparedStatement preparedStatement = connection.
            prepareStatement("select o.*, od.id_operdet, od.priority, t.name as taskName from operationdetail od join operation o on o.id_operation = od.id_operation join task t on t.id_task = o.id_tas where od.id_detail=? order by id_detail, id_operdet");
            preparedStatement.setObject(1, detailId);
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
                OperationToDetail operationToDetail = new OperationToDetail();
                operationToDetail.setId_operdet(rs.getInt("id_operdet"));
                operationToDetail.setId_operation(rs.getInt("id_operation"));
                operationToDetail.setName(rs.getString("name"));
                operationToDetail.setTime(rs.getInt("time"));
                operationToDetail.setTimesetup(rs.getInt("timesetup"));
                operationToDetail.setTimecompletion(rs.getInt("timecompletion"));
                operationToDetail.setId_tas(rs.getInt("id_tas"));
                operationToDetail.setPriority(rs.getInt("priority"));
                operationToDetail.setTaskname(rs.getString("taskname"));
                operationToDetails.add(operationToDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return operationToDetails;
    }
    
    public OperationToDetail getOperationToDetailById(Integer operationToDetailId) {
        OperationToDetail operationToDetail = new OperationToDetail();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from operationdetail where id_operdet=?");
            preparedStatement.setObject(1, operationToDetailId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                operationToDetail.setId_operdet(rs.getInt("id_operdet"));
                operationToDetail.setId_detail(rs.getInt("id_detail"));
                operationToDetail.setId_operation(rs.getInt("id_operation"));
                operationToDetail.setPriority(rs.getInt("priority"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return operationToDetail;
    }
    
    public OperationToDetail clearOperationToDetail(Integer detail_id) {
        OperationToDetail operationToDetail = new OperationToDetail();
        operationToDetail.setId_operdet(null);
        operationToDetail.setId_detail(detail_id);
        operationToDetail.setId_operation(null);
        operationToDetail.setPriority(null);
        operationToDetail.setTaskname("");
        
        return operationToDetail;
    }
}

