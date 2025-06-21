package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import org.apache.commons.beanutils.BeanUtils;

import model.Polis;
import model.Tweet;
import model.User;
import repository.PolisRepository;
import repository.TweetRepository;
import repository.UserRepository;
import service.TweetService;
import service.PolisService;
import service.UserService;

/**
 * Servlet implementation class DiscoverNotLogged
 */
@WebServlet("/Users")
public class Users extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Users() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(jakarta.servlet.http.HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserRepository userRepo = new UserRepository();
		UserService userService = new UserService(userRepo);
		List<User> users = userService.getMostPopularUsers();
        HttpSession session = request.getSession(false);
        User user = null;
        boolean isForEdit = false;
        if (session != null) {
            user = (User) session.getAttribute("user");
            users = userService.getAllUsers();
            isForEdit = userService.isAdmin(user.getUsername());
        }
		request.setAttribute("users", users);
		request.setAttribute("isForEdit", isForEdit);
		
		request.getRequestDispatcher("Users.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(jakarta.servlet.http.HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		doGet(request, response);
	}

}