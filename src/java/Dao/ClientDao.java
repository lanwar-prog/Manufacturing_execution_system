/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Client;
import Util.DbUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientDao {
        
    private Connection connection;

    public ClientDao() {
        connection = DbUtil.getConnection();
    }
    
        public Client addClient(Client client) {
        int returned_id = 0;
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into client(Nameorganization,Telephone,City,Minname,Street,NumberHouse,Email) values (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            // Parameters start with 1
            preparedStatement.setString(1, client.getNameorganization());
            preparedStatement.setString(2, client.getTelephone());
            preparedStatement.setString(3, client.getCity());
            preparedStatement.setString(4, client.getMinname());
            preparedStatement.setString(5, client.getStreet());
            preparedStatement.setString(6, client.getNumberhouse());
            preparedStatement.setString(7, client.getEmail());
            preparedStatement.executeUpdate();
            
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                returned_id = rs.getInt(1);
            }
            
            client.setId_client(returned_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return client;
    }
    
    public void deleteClient(Integer clientId) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from client where id_client=?");
            // Parameters start with 1
            preparedStatement.setInt(1, clientId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
       
    }
    
    public void updateClient(Client client) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update client set Nameorganization=?, Telephone=?, City=?, Minname=?, Street=?, NumberHouse=?, Email=? where id_client=?");
            // Parameters start with 1
            preparedStatement.setString(1, client.getNameorganization());
            preparedStatement.setString(2, client.getTelephone());
            preparedStatement.setString(3, client.getCity());
            preparedStatement.setString(4, client.getMinname());
            preparedStatement.setString(5, client.getStreet());
            preparedStatement.setString(6, client.getNumberhouse());
            preparedStatement.setString(7, client.getEmail());
            preparedStatement.setObject(8, client.getId_client());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<Client>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from client");
            while (rs.next()) {
                Client client = new Client();
                client.setId_client(rs.getInt("id_client"));
                client.setNameorganization(rs.getString("nameorganization"));
                client.setTelephone(rs.getString("telephone"));
                client.setCity(rs.getString("city"));
                client.setMinname(rs.getString("minname"));
                client.setStreet(rs.getString("street"));
                client.setNumberhouse(rs.getString("numberhouse"));
                client.setEmail(rs.getString("email"));
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }
    
    public Client getClientById(Integer clientId) {
        Client client = new Client();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from client where id_client=?");
            preparedStatement.setObject(1, clientId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                client.setId_client(rs.getInt("id_client"));
                client.setNameorganization(rs.getString("nameorganization"));
                client.setTelephone(rs.getString("telephone"));
                client.setCity(rs.getString("city"));
                client.setMinname(rs.getString("minname"));
                client.setStreet(rs.getString("street"));
                client.setNumberhouse(rs.getString("numberhouse"));
                client.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return client;
    }
    
    public List<String> getCitys() {
        List<String> citys = new ArrayList<String>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select distinct city from client");
            while (rs.next()) {
                citys.add(rs.getString("city"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return citys;
    }
    
    public Client clearClient() {
        Client client = new Client();
        client.setId_client(null);
        client.setNameorganization("");
        client.setTelephone("");
        client.setCity("");
        client.setMinname("");
        client.setStreet("");
        client.setNumberhouse("");
        client.setEmail("");
        
        return client;
    }
}
