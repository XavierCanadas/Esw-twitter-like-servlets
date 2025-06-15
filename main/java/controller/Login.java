package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import repository.UserRepository;
import service.UserService;
import model.User;

import java.io.IOException;
import java.util.Map;



import org.apache.commons.beanutils.BeanUtils;

/**
 * Servlet implementation class Login
 */

@MultipartConfig
@WebServlet("/Login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(false);

        if (session!=null && session.getAttribute("user")!=null) {
        	request.getRequestDispatcher("Timeline.jsp").forward(request, response);
        } else {
        	request.getRequestDispatcher("Login.jsp").forward(request, response);
        }

        // Use System.out for immediate console output
        System.out.println("===== DEBUG: Login servlet accessed =====");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();

        try {
            BeanUtils.populate(user, request.getParameterMap());

            try (UserRepository userRepository = new UserRepository()) {
                UserService userService = new UserService(userRepository);

                //Call actions
                Map<String, String> errors = userService.login(user);
                if (errors.isEmpty()) {
                    HttpSession session = request.getSession();

                    userService.setPictureUrl(user, request);

                    session.setAttribute("user", user);

                    // Check if it's an AJAX request
                    String acceptHeader = request.getHeader("Accept");
                    String xRequestedWith = request.getHeader("X-Requested-With");
                    boolean isAjax = (xRequestedWith != null && xRequestedWith.equals("XMLHttpRequest"))
                                    || (acceptHeader != null && acceptHeader.contains("application/json"));

                    if (isAjax) {
                        // For AJAX requests, load the welcome page content
                        request.getRequestDispatcher("Timeline.jsp").forward(request, response);
                    } else {
                        // For direct browser requests
                        response.sendRedirect("Timeline.jsp");
                    }
                } else {
                    request.setAttribute("user", user);
                    request.setAttribute("errors", errors);
                    request.getRequestDispatcher("Login.jsp").forward(request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
    }
}
