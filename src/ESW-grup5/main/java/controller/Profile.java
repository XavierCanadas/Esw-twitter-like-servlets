package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.User;
import repository.TweetRepository;
import repository.UserRepository;
import service.TweetService;
import service.UserService;
import util.Common;

/**
 * Servlet implementation class User
 */
@WebServlet("/Profile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Profile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);

		// get username from request parameter
		String username = request.getParameter("username");
		User currentUser = (User) session.getAttribute("user");

		if (currentUser == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		if (username == null || username.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Username parameter is required.");
			return;
		}

		try (UserRepository userRepository = new UserRepository();
			 TweetRepository tweetRepository = new TweetRepository()) {
			UserService userService = new UserService(userRepository);
			TweetService tweetService = new TweetService(tweetRepository);

			User user = userService.findByUsername(username);
			if (user == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
				return;
			}

			// TODO: if the user is an admin or the user itself, allow editing
			boolean enableEdit = (userService.isAdmin(currentUser.getUsername()) ||  currentUser.getUsername().equals(username));
			request.setAttribute("enableEdit", enableEdit);
			//de momento, un user no puede borrarse porque se debería hacer logout y cerrar la sesion
			boolean enableDelete = (userService.isAdmin(currentUser.getUsername()) /*||  currentUser.getUsername().equals(username)*/);
			request.setAttribute("enableDelete", enableDelete);
			
			// Check if the current user is following this profile user
			boolean isFollowing = userService.isFollowing(currentUser.getId(), user.getId());
			request.setAttribute("isFollowing", isFollowing);

			// Enable follow/unfollow buttons only if viewing someone else's profile
			boolean enableFollowButtons = !currentUser.getUsername().equals(username);
			request.setAttribute("enableFollowButtons", enableFollowButtons);

			// Añadimos la cantidad de seguidores
	        int followerCount = userService.getFollowerCount(user.getId());
	        request.setAttribute("followerCount", followerCount);
	        
	     // Añadimos la cantidad de seguidores
	        int followingCount = userService.getFollowingCount(user.getId());
	        request.setAttribute("followingCount", followingCount);
			
			user.setPicture(Common.setPictureUrl(user.getPicture(), request));
			request.setAttribute("user", user);
			request.getRequestDispatcher("Profile.jsp").forward(request, response);


		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error accessing user data.");
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
