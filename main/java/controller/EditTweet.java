package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Tweet;
import model.User;
import org.apache.commons.beanutils.BeanUtils;
import repository.TweetRepository;
import repository.UserRepository;
import service.TweetService;
import service.UserService;

import java.io.IOException;

/**
 * Servlet implementation class DelTweet
 */
@WebServlet("/EditTweet")
public class EditTweet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditTweet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		User currentUser = (User) session.getAttribute("user");
		if (currentUser == null) {
			response.sendRedirect("login.jsp");
			return;
		}


		// Get the tweet from the request
		Tweet tweet = new Tweet();

		try {
			BeanUtils.populate(tweet, request.getParameterMap());

			// check if the user id is the same as the tweet's user id or if it's an admin
			if (tweet.getId() == null) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "the tweet must be valid.");
				return;
			}
			try (UserRepository userRepository = new UserRepository()) {
				UserService userService = new UserService(userRepository);
				// Check if the user exists
				if (!userService.isAdmin(currentUser.getUsername()) && tweet.getUid() != currentUser.getId()) {
					response.sendError(HttpServletResponse.SC_FORBIDDEN, "Only admins can edit users.");
					return;
				}
			}

			try (TweetRepository tweetRepository = new TweetRepository()) {
				TweetService tweetService = new TweetService(tweetRepository);
				tweetService.update(tweet);
				response.setStatus(HttpServletResponse.SC_OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal error while updating the tweet.");
			return;
		}
	}

}
