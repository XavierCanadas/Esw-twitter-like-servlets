package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import repository.UserRepository;
import service.UserService;
import util.Common;

import java.io.IOException;
import java.util.List;


/**
 * Servlet implementation class GetFollowed
 */
@WebServlet("/Followed")
public class Followed extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Followed() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<User> users = null;
		
		HttpSession session = request.getSession(false);
		
		if (session != null) {
			User user = (User) session.getAttribute("user");
			if (user!= null) {
				try (UserRepository userRepository = new UserRepository()) {
					UserService userService = new UserService(userRepository);
					users = userService.getFollowedUsers(user.getId(),0,4);

					for (User currentUser : users) {
						currentUser.setPicture(Common.setPictureUrl(currentUser.getPicture(), request));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		request.setAttribute("users",users);
		request.getRequestDispatcher("Followed.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
