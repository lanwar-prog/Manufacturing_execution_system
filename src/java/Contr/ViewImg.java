/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Contr;

import Dao.ImageDao;
import Model.Task;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewImg extends HttpServlet {
    
    private static final long serialVersionUID = 102831973239L;
    private ImageDao dao;

    public ViewImg() {
        super();
        dao = new ImageDao();
    }
        
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewImg</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewImg at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String forward="";
        String id = request.getParameter("id");
        
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        

        if (request.getSession().getAttribute("login") != null) {
            try {
                Integer imageId = Integer.valueOf(id);
                forward = "ViewImage.jsp";
                Blob image = dao.getImageById(imageId);
                byte[] imgData = image.getBytes(1, (int)image.length());
                response.setContentType("image/jpeg");
                response.getOutputStream().write(imgData);
                response.getOutputStream().flush();
                response.getOutputStream().close();
                
                RequestDispatcher view = request.getRequestDispatcher(forward);
                view.forward(request, response);
            } catch (SQLException ex) {
                
            }
        }
        else {
            response.setStatus(301);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
