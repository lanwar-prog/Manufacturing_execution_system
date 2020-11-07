/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Order;
import Util.DbUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDao {
    
    private Connection connection;

    public OrderDao() {
        connection = DbUtil.getConnection();
    }
    
    public Order addOrder(Order order) {
        int returned_id = 0;
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into clientorder(id_client,Date,Urgency,State,NumberDetails,id_detail,DateExecution) values (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            // Parameters start with 1
            preparedStatement.setObject(1, order.getId_client());
            preparedStatement.setDate(2, new java.sql.Date(order.getDate().getTime()));
            preparedStatement.setObject(3, order.getUrgency());
            preparedStatement.setObject(4, order.getState());
            preparedStatement.setObject(5, order.getNumberdetails());
            preparedStatement.setObject(6, order.getId_detail());
            preparedStatement.setDate(7, new java.sql.Date(order.getDateexecution().getTime()));
            preparedStatement.executeUpdate();
            
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                returned_id = rs.getInt(1);
            }
            
            order.setId_order(returned_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return order;
    }
    
    public void deleteOrder(Integer orderId) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from clientorder where id_order=?");
            // Parameters start with 1
            preparedStatement.setInt(1, orderId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public void updateOrder(Order order) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update clientorder set id_client=?, Date=?, Urgency=?, State=?, NumberDetails=?, id_detail=?, DateExecution=? where id_order=?");
            // Parameters start with 1
            preparedStatement.setObject(1, order.getId_client());
            preparedStatement.setDate(2, new java.sql.Date(order.getDate().getTime()));
            preparedStatement.setObject(3, order.getUrgency());
            preparedStatement.setObject(4, order.getState());
            preparedStatement.setObject(5, order.getNumberdetails());
            preparedStatement.setObject(6, order.getId_detail());
            preparedStatement.setDate(7, new java.sql.Date(order.getDateexecution().getTime()));
            preparedStatement.setObject(8, order.getId_order());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Order> getAllOrders(Integer state) {
        List<Order> orders = new ArrayList<Order>();
        try {
            ResultSet rs;
            if (state == null) {
                Statement statement = connection.createStatement();
                rs = statement.executeQuery("select o.*, c.nameorganization as clientname, c.city as clientcity, d.name as detailname from clientorder o left join client c on c.id_client = o.id_client left join detail d on d.id_detail = o.id_detail order by o.id_order");
            } else {
                PreparedStatement preparedStatement = connection.
                    prepareStatement("select o.*, c.nameorganization as clientname, c.city as clientcity, d.name as detailname from clientorder o left join client c on c.id_client = o.id_client left join detail d on d.id_detail = o.id_detail where o.state=? order by o.id_order");
                preparedStatement.setObject(1, state);
                rs = preparedStatement.executeQuery();
            }
            while (rs.next()) {
                Order order = new Order();
                order.setId_order(rs.getInt("id_order"));
                order.setId_client(rs.getInt("id_client"));
                order.setDate(rs.getDate("date"));
                order.setUrgency(rs.getInt("urgency"));
                order.setState(rs.getInt("state"));
                order.setNumberdetails(rs.getInt("numberdetails"));
                order.setId_detail(rs.getInt("id_detail"));
                order.setDateexecution(rs.getDate("dateexecution"));
                order.setClientname(rs.getString("clientname"));
                order.setClientcity(rs.getString("clientcity"));
                order.setDetailname(rs.getString("detailname"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }
    
    public Order getOrderById(Integer orderId) {
        Order order = new Order();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select o.*, c.city as clientcity from clientorder o left join client c on c.id_client = o.id_client where o.id_order=?");
            preparedStatement.setObject(1, orderId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                order.setId_order(rs.getInt("id_order"));
                order.setId_client(rs.getInt("id_client"));
                order.setDate(rs.getDate("date"));
                order.setUrgency(rs.getInt("urgency"));
                order.setState(rs.getInt("state"));
                order.setNumberdetails(rs.getInt("numberdetails"));
                order.setId_detail(rs.getInt("id_detail"));
                order.setDateexecution(rs.getDate("dateexecution"));
                order.setClientcity(rs.getString("clientcity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return order;
    }
    
    public Order clearOrder() {
        Order order = new Order();
        order.setId_order(null);
        order.setId_client(null);
        order.setDate(new Date());
        order.setUrgency(0);
        order.setState(0);
        order.setNumberdetails(null);
        order.setId_detail(null);
        order.setDateexecution(new Date());
        order.setClientname("");
        order.setClientcity("");
        order.setDetailname("");
        
        return order;
    }
}
