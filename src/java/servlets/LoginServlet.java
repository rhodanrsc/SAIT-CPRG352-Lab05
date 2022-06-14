package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.AccountService;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        String username = (String) request.getAttribute("user_in");
        
        session.setAttribute("sessionAttribute", username);
        
        if (request.getParameter("logout") != null) {
            session.invalidate();
            request.setAttribute("message", "Successfully logged out.");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request,response);
            return;
            
        } else if (session.getAttribute("username") != null ) {
            response.sendRedirect("home");
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request,response);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String pass = request.getParameter("password");
        
        AccountService acc = new AccountService();
        User newUser = acc.login(username, pass);
        HttpSession usernameSession = request.getSession();
        
        if (username == null || pass == null || pass.equals("") || username.equals("")) {
            
            request.setAttribute("message", "Valid username and password required.");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request,response);
            return;
        } else if (newUser == null) {
                request.setAttribute("message", "Incorrect username or password.");
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request,response);
                return; 
            } else {
                usernameSession.setAttribute("username", username);
                response.sendRedirect("home");
            
        } 
    }
}
