/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Contr;

import Dao.SupplierDao;
import Model.Supplier;
import com.mysql.jdbc.StringUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SupplierContr extends HttpServlet {
    private static final long serialVersionUID = 102831973239L;
    private static String INSERT_OR_EDIT = "/AddSupplier.jsp";
    private static String LIST = "/ListSupplier.jsp";
    private SupplierDao dao;
    
    public SupplierContr() {
        super();
        dao = new SupplierDao();
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
                    Integer supplierId = Integer.valueOf(request.getParameter("id"));
                    dao.deleteSupplier(supplierId);
                    forward = LIST;
                    request.setAttribute("suppliers", dao.getAllSupplier());    
                } else if (action.equalsIgnoreCase("edit")){
                    forward = INSERT_OR_EDIT;
                    Integer supplierId = Integer.valueOf(request.getParameter("id"));
                    Supplier supplier = dao.getSupplierById(supplierId);
                    request.setAttribute("supplier", supplier);
                } else if (action.equalsIgnoreCase("list")){
                    forward = LIST;
                    request.setAttribute("suppliers", dao.getAllSupplier());
                } else {
                    forward = INSERT_OR_EDIT;
                    request.setAttribute("supplier", dao.clearSupplier());
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
            Supplier supplier = new Supplier();
            
            if (!(StringUtils.isNullOrEmpty(request.getParameter("name")) ||
                StringUtils.isNullOrEmpty(request.getParameter("telephone")) || 
                StringUtils.isNullOrEmpty(request.getParameter("city")) ||
                StringUtils.isNullOrEmpty(request.getParameter("minname")) ||
                StringUtils.isNullOrEmpty(request.getParameter("street")) ||
                StringUtils.isNullOrEmpty(request.getParameter("numberhouse")) ||
                StringUtils.isNullOrEmpty(request.getParameter("email"))))
            {
                try {
                    supplier.setName(request.getParameter("name"));
                    supplier.setTelephone(request.getParameter("telephone"));
                    supplier.setCity(request.getParameter("city"));
                    supplier.setMinname(request.getParameter("minname"));
                    supplier.setStreet(request.getParameter("street"));
                    supplier.setNumberhouse(request.getParameter("numberhouse"));
                    supplier.setEmail(request.getParameter("email"));

                    String id = request.getParameter("id");

                    if(StringUtils.isNullOrEmpty(id))
                    {
                        supplier = dao.addSupplier(supplier);
                    }
                    else
                    {
                        supplier.setId_supplier(Integer.valueOf(id));
                        dao.updateSupplier(supplier);
                    }
                    //dao.getSupplierById(supplier.getId_supplier());
                    supplier = dao.clearSupplier();

                } catch (Exception e) {
                    e.printStackTrace();
                    response.setStatus(400);
                }
            }
            else
                response.setStatus(415);
            
            request.setAttribute("supplier", supplier);
            
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

