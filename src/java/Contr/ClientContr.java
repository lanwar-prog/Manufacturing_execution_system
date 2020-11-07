/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Contr;

import Dao.ClientDao;
import Model.Client;
import com.mysql.jdbc.StringUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClientContr extends HttpServlet {

    private static final long serialVersionUID = 102831973239L;
    private static String INSERT_OR_EDIT = "/AddClient.jsp";
    private static String LIST = "/ListClient.jsp";
    private ClientDao dao;
    
    public ClientContr() {
        super();
        dao = new ClientDao();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String forward="";
        String action = request.getParameter("action");
        
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

        if (request.getSession().getAttribute("login") != null) {
            try {
                if (action.equalsIgnoreCase("delete")){
                    Integer clientId = Integer.valueOf(request.getParameter("id"));
                    dao.deleteClient(clientId);
                    forward = LIST;
                    request.setAttribute("clients", dao.getAllClients());    
                } else if (action.equalsIgnoreCase("edit")){
                    forward = INSERT_OR_EDIT;
                    Integer clientId = Integer.valueOf(request.getParameter("id"));
                    Client client = dao.getClientById(clientId);
                    request.setAttribute("client", client);
                } else if (action.equalsIgnoreCase("list")){
                    forward = LIST;
                    request.setAttribute("clients", dao.getAllClients());
                } else {
                    forward = INSERT_OR_EDIT;
                    request.setAttribute("client", dao.clearClient());
                }

                RequestDispatcher view = request.getRequestDispatcher(forward);
                view.forward(request, response);
            
            } catch (SQLException e) {
                e.printStackTrace();
                response.setStatus(501);
            }
        }
        else {
            response.setStatus(301);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        
        if (request.getSession().getAttribute("login") != null) {
            Client client = new Client();
            
            if (!(StringUtils.isNullOrEmpty(request.getParameter("nameorganization")) ||
                StringUtils.isNullOrEmpty(request.getParameter("telephone")) || 
                StringUtils.isNullOrEmpty(request.getParameter("city")) ||
                StringUtils.isNullOrEmpty(request.getParameter("minname")) ||
                StringUtils.isNullOrEmpty(request.getParameter("street")) ||
                StringUtils.isNullOrEmpty(request.getParameter("numberhouse")) ||
                StringUtils.isNullOrEmpty(request.getParameter("email"))))
            {
                try {
                    client.setNameorganization(request.getParameter("nameorganization"));
                    client.setTelephone(request.getParameter("telephone"));
                    client.setCity(request.getParameter("city"));
                    client.setMinname(request.getParameter("minname"));
                    client.setStreet(request.getParameter("street"));
                    client.setNumberhouse(request.getParameter("numberhouse"));
                    client.setEmail(request.getParameter("email"));

                    String id = request.getParameter("id");

                    if(StringUtils.isNullOrEmpty(id))
                    {
                        client = dao.addClient(client);
                    }
                    else
                    {
                        client.setId_client(Integer.valueOf(id));
                        dao.updateClient(client);
                    }
                    //dao.getClientById(client.getId_client());
                    client = dao.clearClient();

                } catch (Exception e) {
                    e.printStackTrace();
                    response.setStatus(400);
                }
            }
            else
                response.setStatus(415);
            
            request.setAttribute("client", client);
            
            RequestDispatcher view = request.getRequestDispatcher(INSERT_OR_EDIT);
            view.forward(request, response);
        }
        else {
            response.setStatus(301);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

