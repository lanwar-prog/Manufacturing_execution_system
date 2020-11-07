/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Contr;

import Dao.ClientDao;
import Dao.DetailDao;
import Dao.OrderDao;
import Model.Order;
import com.mysql.jdbc.StringUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class OrderContr extends HttpServlet{
    
    private static final long serialVersionUID = 102831973239L;
    private static String INSERT_OR_EDIT = "/AddOrder.jsp";
    private static String LIST = "/ListOrder.jsp";
    private static String LIST2 = "/ListOrder2.jsp";
    private static String LIST3 = "/ListOrder3.jsp";
    private OrderDao dao;
    private ClientDao cl_dao;
    private DetailDao d_dao;
    
    public OrderContr() {
        super();
        dao = new OrderDao();
        cl_dao = new ClientDao();
        d_dao = new DetailDao();
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
                    Integer orderId = Integer.valueOf(request.getParameter("id"));
                    dao.deleteOrder(orderId);
                    forward = LIST;
                    request.setAttribute("orders", dao.getAllOrders(null));    
                } else if (action.equalsIgnoreCase("edit")){
                    forward = INSERT_OR_EDIT;
                    Integer orderId = Integer.valueOf(request.getParameter("id"));
                    Order order = dao.getOrderById(orderId);
                    request.setAttribute("order", order);
                } else if (action.equalsIgnoreCase("list")){
                    forward = LIST;
                    request.setAttribute("orders", dao.getAllOrders(null));
                } else if (action.equalsIgnoreCase("list2")){
                    forward = LIST2;
                    request.setAttribute("orders", dao.getAllOrders(0));
                } else if (action.equalsIgnoreCase("list3")){
                    forward = LIST3;
                    request.setAttribute("orders", dao.getAllOrders(1));
                } else {
                    forward = INSERT_OR_EDIT;
                    request.setAttribute("order", dao.clearOrder());
                }

                request.setAttribute("clients", cl_dao.getAllClients());
                request.setAttribute("details", d_dao.getAllDetails());
                request.setAttribute("citys", cl_dao.getCitys());
                
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
            Order order = new Order();
            
            if (!(StringUtils.isNullOrEmpty(request.getParameter("id_client")) ||
                StringUtils.isNullOrEmpty(request.getParameter("date")) || 
                StringUtils.isNullOrEmpty(request.getParameter("state")) ||
                StringUtils.isNullOrEmpty(request.getParameter("numberdetails")) ||
                StringUtils.isNullOrEmpty(request.getParameter("dateexecution"))))
            {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    order.setId_client(Integer.valueOf(request.getParameter("id_client")));
                    Date dt = df.parse(request.getParameter("date")); 
                    order.setDate(dt);
                    order.setUrgency(Integer.valueOf(request.getParameter("urgency")));
                    order.setState(Integer.valueOf(request.getParameter("state")));
                    order.setNumberdetails(Integer.valueOf(request.getParameter("numberdetails")));
                    order.setId_detail(Integer.valueOf(request.getParameter("id_detail")));
                    dt = df.parse(request.getParameter("dateexecution")); 
                    order.setDateexecution(dt);

                    String id = request.getParameter("id");

                    if(StringUtils.isNullOrEmpty(id))
                    {
                        order = dao.addOrder(order);
                    }
                    else
                    {
                        order.setId_order(Integer.valueOf(id));
                        dao.updateOrder(order);
                    }
                    //dao.getDetailById(detail.getId_detail());
                    order = dao.clearOrder();

                } catch (Exception e) {
                    e.printStackTrace();
                    response.setStatus(400);
                }
            }
            else
                response.setStatus(415);
            
            request.setAttribute("order", order);
            request.setAttribute("clients", cl_dao.getAllClients());
            request.setAttribute("details", d_dao.getAllDetails());
            request.setAttribute("citys", cl_dao.getCitys());
            
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
