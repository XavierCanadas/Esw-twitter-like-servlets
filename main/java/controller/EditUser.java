package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.Polis;
import model.User;
import org.apache.commons.beanutils.BeanUtils;
import repository.PolisRepository;
import repository.UserRepository;
import service.PolisService;
import service.UserService;

/**
 * Servlet implementation class User
 */
@MultipartConfig
@WebServlet("/EditUser")
public class EditUser extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUser() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try (UserRepository userRepository = new UserRepository();
             PolisRepository polisRepository = new PolisRepository()) {

            HttpSession session = request.getSession();
            User currentUser = (User) session.getAttribute("user"); // change to enable admins to edit any user

            if (currentUser == null) {
                response.sendRedirect("login.jsp");
                return;
            }
            // Add the option to edit any user if the current user is an admin

            UserService userService = new UserService(userRepository);


            Optional<User> userOptional = userService.getUserByUsername(currentUser.getUsername());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                request.setAttribute("user", user);
                request.setAttribute("editing", true);

                // load polis
                PolisService polisService = new PolisService(polisRepository);
                List<Polis> polisList = polisService.getAllPolis();
                request.setAttribute("polisList", polisList);


                request.getRequestDispatcher("Register.jsp").forward(request, response);
            } else {
                response.sendRedirect("NotFound.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (UserRepository userRepository = new UserRepository();
             PolisRepository polisRepository = new PolisRepository()) {

            UserService userService = new UserService(userRepository);

            HttpSession session = request.getSession();
            User currentUser = (User) session.getAttribute("user");

            if (currentUser == null) {
                response.sendRedirect("Login");
                return;
            }

            Optional<User> userOptional = userService.getUserById(currentUser.getId());
            if (userOptional.isEmpty()) {
                throw new ServletException("User not found");
            }

            User user = userOptional.get();
            String currentUsername = user.getUsername();
            BeanUtils.populate(user, request.getParameterMap());

            String polisIdParam = request.getParameter("polisId");
            if (polisIdParam != null && !polisIdParam.isEmpty()) {
                int polisId = Integer.parseInt(polisIdParam);
                Optional<Polis> polis = polisRepository.findById(polisId);
                if (polis.isPresent()) {
                    user.setPolis(polis.get());
                }
            }

            Map<String, String> errors = userService.update(user, currentUsername);

            if (errors.isEmpty()) {
                userService.setPictureUrl(user, request);

                // Update the session user
                session.setAttribute("user", user);
                request.setAttribute("user", user);
                request.getRequestDispatcher("Profile").forward(request, response);
            } else {
                // Load polis for the form
                PolisService polisService = new PolisService(polisRepository);
                List<Polis> polisList = polisService.getAllPolis();
                request.setAttribute("polisList", polisList);

                request.setAttribute("user", user);
                request.setAttribute("errors", errors);
                request.setAttribute("editing", true);
                request.getRequestDispatcher("Register.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while processing your request.\n" + e.getMessage());
            request.getRequestDispatcher("Profile.jsp").forward(request, response);
        }
    }
}
