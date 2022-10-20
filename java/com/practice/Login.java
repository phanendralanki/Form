
package com.practice;

import java.sql.Connection;  //for database 
import java.sql.DriverManager;
import java.io.IOException;
import java.sql.PreparedStatement;  //for prepared statement object 
import java.sql.ResultSet;           //for resultset 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Login {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        //session for checking the condition in if block
        HttpSession session = request.getSession();
        
        //3.servlet dispatcher
        RequestDispatcher dispatcher = null; 
        //database code 
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/registerdb","root","user");
            //prepared statement object 
            PreparedStatement pst = con.prepareStatement("select *from details where email= ? and password = ? ");
            pst.setString(1, email);
            pst.setString(2, password);
            
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
              session.setAttribute("email", rs.getString("email"));
              dispatcher = request.getRequestDispatcher("index.html");
            }else{
                request.setAttribute("status","failed");
                dispatcher = request.getRequestDispatcher("index.html");
            }
            dispatcher.forward(request, response);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
