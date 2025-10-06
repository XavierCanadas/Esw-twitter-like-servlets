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
import util.Common;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class DelTweet
 */
@WebServlet("/SearchTweets")
public class SearchTweets extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchTweets() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

		request.getRequestDispatcher("Search.jsp").forward(request, response);

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

		String searchQuery = request.getParameter("query");
		if (searchQuery == null || searchQuery.trim().isEmpty()) {
			request.setAttribute("tweets", null);
			return;
		}

		// get the tweets by search query
		try (TweetRepository tweetRepo = new TweetRepository();
			 UserRepository userRepo = new UserRepository()) {

			TweetService tweetService = new TweetService(tweetRepo);

			// Get tweets by search query
			List<Tweet> tweets = tweetService.searchTweetsByWord(searchQuery, currentUser.getId(), 0, 20, request);



			request.setAttribute("tweets",tweets);
			request.getRequestDispatcher("Tweets.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request.");
		}
	}

}
