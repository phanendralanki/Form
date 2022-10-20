package com.practice;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;             //for servlet
import javax.servlet.http.*;        //for http servlet

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RegisterServlet extends HttpServlet {
    
    //form is comming as post so doPost()
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
    {
        
        //2 objects -request,response = with req                                                                                                   uest we can fetch(take the request)the data
        //with response object we can generate dynamic response(gives the result)- we can print html  
        
        //to give the output(response)
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<h2>welcome to Register servlet</h2>");
        
        //to fetch the data 
        String name = request.getParameter("name");  //the name field in form 
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String gender = request.getParameter("gender");
        

             out.println("<h2>Name: "+name +"</h2>");
             out.println("<h2>Password: "+password+"</h2>");
             out.println("<h2>Email: "+email+"</h2>");
             out.println("<h2>Gender: "+gender+"</h2>");
         
            //3.servlet dispatcher 
          
            RequestDispatcher dispatcher = null;

            //with the help of jdbc we can store the data 
             //jdbc 
             Connection con =null;
             try{
                 Class.forName("com.mysql.cj.jdbc.Driver");
                 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/registerdb","root","user");
                 PreparedStatement pst = con.prepareStatement("insert into details(name,password,email) values(?,?,?) ");
                 pst.setString(1,name);
                 pst.setString(2,password);
                 pst.setString(3,email);
                 
                 int rowCount = pst.executeUpdate();
                 dispatcher = request.getRequestDispatcher("RegisterServlet.java");
                       if(rowCount>0){
                           //3.RequestDispatcher
                           request.setAttribute("status","success");
                       }
                       else
                       {
                           request.setAttribute("status", "failed");
                       }
                       dispatcher.forward(request, response);
             }catch(Exception e){
                 e.printStackTrace();
             }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
                     
    }


    }

