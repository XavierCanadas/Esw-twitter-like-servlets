package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import repository.UserRepository;
import service.UserService;

/**
 * Servlet implementation class deleteUser
 */
@WebServlet("/DeleteUser")
public class DeleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public DeleteUser() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Check if the session user is an admin
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in.");
			return;
		}

		User currentUser = (User) session.getAttribute("user");

		// Proceed with user deletion
		try (UserRepository userRepository = new UserRepository()) {
			UserService userService = new UserService(userRepository);

			// check if current user is admin
			if (!userService.isAdmin(currentUser.getUsername()) && !currentUser.getUsername().equals(request.getParameter("username"))) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "Only admins can delete users.");
				return;
			}

			String username = request.getParameter("username");

			try {
				userService.deleteUserFromUsername(username);
			} catch (Exception e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting user.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error accessing user repository.");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
