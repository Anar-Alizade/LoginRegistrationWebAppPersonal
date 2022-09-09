package com.uniquedeveloper.registration;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "Login", value = "/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userEmail = request.getParameter("username");
        String userPasword = request.getParameter("password");

        HttpSession session = request.getSession();
        RequestDispatcher dispatcher = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/newwebregistration?useSSL=false", "root", "12345");
            PreparedStatement stmt = con.prepareStatement("select * from new_table where user_email = ? and user_password = ?");
            stmt.setString(1, userEmail);
            stmt.setString(2, userPasword);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {//burda deyilirki eger yuxaridaki email ve paswordu odeyen data varsa gir bu moterzeye
                session.setAttribute("name", rs.getString("username"));
                dispatcher = request.getRequestDispatcher("index.jsp");
                //parolu daxil etmemishem ama parol xetasi var
            } else {
                request.setAttribute("status", "failed");
                dispatcher = request.getRequestDispatcher("login.jsp");
            }
            dispatcher.forward(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
//session.setAttribute() yazan zaman elimizdeki deyeri capa vermek uchun oturduk index.jsp -ye
//request.setAttribute() yazan zaman "success" ve ya "failed" mesajini gonderdik login.jsp -ye