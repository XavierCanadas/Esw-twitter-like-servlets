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
import service.TweetService;

import java.io.IOException;
import java.util.List;


/**
 * Servlet implementation class Tweets
 */
@WebServlet("/Comments")
public class Comments extends HttpServlet {

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Comments() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Tweet> comments = null;

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

        // get the tweet id to get the comments
        String parentIdString = request.getParameter("parentId");
        if (parentIdString == null || parentIdString.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tweet ID parameter is required.");
            return;
        }

        // get the comments
        Integer parentId = Integer.valueOf(parentIdString);

        try (TweetRepository tweetRepository = new TweetRepository()) {
            TweetService tweetService = new TweetService(tweetRepository);
            comments = tweetService.getCommentsByTweetId(parentId, currentUser.getId(), 0, 10); // TODO: pagination

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving comments.");
            return;
        }

        // send
        request.setAttribute("tweets", comments);
        request.setAttribute("user", currentUser);
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
