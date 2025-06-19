package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Tweet;
import model.User;
import repository.TweetRepository;
import repository.UserRepository;
import service.TweetService;
import service.UserService;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class Tweets
 */
@WebServlet("/Tweets")
public class Tweets extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Tweets() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Tweet> tweets = null;

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User currentUser = (User) session.getAttribute("user");
        User userTweets = null;

        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // get the userid to get the tweets
        String username = request.getParameter("username");

        if (username == null || username.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Username parameter is required.");
            return;
        }

        try (UserRepository userRepository = new UserRepository()) {
            UserService userService = new UserService(userRepository);
            userTweets = userService.findByUsername(username);
            userService.setPictureUrl(userTweets, request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (userTweets == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
            return;
        }

        try(TweetRepository tweetRepository = new TweetRepository()) {
            TweetService tweetService = new TweetService(tweetRepository);

            tweets = tweetService.getTweetsByUser(userTweets.getId(),0,10); // TODO: add pagination
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("tweets",tweets);
        request.setAttribute("user", userTweets);
        request.getRequestDispatcher("Tweets.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
