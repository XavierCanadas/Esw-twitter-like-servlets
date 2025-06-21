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
import util.Common;

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

            UserService userService = new UserService(userRepository);

            HttpSession session = request.getSession();
            String username = request.getParameter("username");
            User currentUser = (User) session.getAttribute("user"); // change to enable admins to edit any user



            if (currentUser == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            // If is not an admin, only allow editing of the current user
            /*
            if (!currentUser.isAdmin() && (username == null || !username.equals(currentUser.getUsername()))) {
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Operation not allowed");
                return;
             */

            // get the user to edit based on the username parameter. If the parameter is equal to the current user's
            // username, then is not necessary get the user from the database, just use the current user
            User userToEdit = currentUser;

            if (username != null && !username.isEmpty() && !username.equals(currentUser.getUsername())) {
                userToEdit = userService.findByUsername(username);
                if (userToEdit == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
                    return;
                }
            }
            request.setAttribute("user", userToEdit);
            request.setAttribute("editing", true);

            // load polis
            PolisService polisService = new PolisService(polisRepository);
            List<Polis> polisList = polisService.getAllPolis();
            request.setAttribute("polisList", polisList);

            request.getRequestDispatcher("Register.jsp").forward(request, response);

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

            Integer userId = Integer.valueOf(request.getParameter("userId"));

            // If is not an admin, only allow editing of the current user
            /*
            if (!currentUser.isAdmin() && (!userId.equals(currentUser.getId()))) {
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Operation not allowed");
                return;
            }
            */

            User userToEdit = null;

            userToEdit = userService.getUserById(userId);
            if (userToEdit == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
                return;
            }

            String currentUsername = userToEdit.getUsername();
            BeanUtils.populate(userToEdit, request.getParameterMap());

            String polisIdParam = request.getParameter("polisId");
            if (polisIdParam != null && !polisIdParam.isEmpty()) {
                int polisId = Integer.parseInt(polisIdParam);
                Optional<Polis> polis = polisRepository.findById(polisId);
                if (polis.isPresent()) {
                    userToEdit.setPolis(polis.get());
                }
            }

            Map<String, String> errors = userService.update(userToEdit, currentUsername);

            if (errors.isEmpty()) {
                userToEdit.setPicture(Common.setPictureUrl(userToEdit.getPicture(), request));

                // Update the session user
                if (userId.equals(currentUser.getId())) {
                    session.setAttribute("user", userToEdit);
                }
                request.setAttribute("user", userToEdit);
                request.getRequestDispatcher("Profile").forward(request, response);
            } else {
                // Load polis for the form
                PolisService polisService = new PolisService(polisRepository);
                List<Polis> polisList = polisService.getAllPolis();
                request.setAttribute("polisList", polisList);

                request.setAttribute("user", userToEdit);
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
