package com.uniquedeveloper.registration;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet(name = "RegistrationServlet", value = "/register")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = null;
        try {
            String userName = request.getParameter("name");
            String userPassword = request.getParameter("pass");
            String email = request.getParameter("email");
            String contact = request.getParameter("contact");


            Class.forName("com.mysql.cj.jdbc.Driver");
            com.mysql.cj.jdbc.Driver s;

            String INSERT_USERS_SQL = "insert into new_table(username, user_password, user_email, user_mobile) values(?,?,?,?)";
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/newwebregistration?useSSL=false", "root", "12345")) {
                PreparedStatement stmt = con.prepareStatement(INSERT_USERS_SQL);
                stmt.setString(1, userName);
                stmt.setString(2, userPassword);
                stmt.setString(3, email);
                stmt.setString(4, contact);

                int rowCount = stmt.executeUpdate();
                dispatcher = request.getRequestDispatcher("registration.jsp");
                if (rowCount > 0) {
                    request.setAttribute("status", "success");
                } else {
                    request.setAttribute("status", "failed");
                }
                dispatcher.forward(request, response);//registrasiya hissesini yeniden bize qaytarir(bizi sehifede saxlayir)
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
