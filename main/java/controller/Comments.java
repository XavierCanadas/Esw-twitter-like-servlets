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
import java.sql.Timestamp;
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

        String lastTweetNumberString = request.getParameter("lastTweetNumber");
        int lastTweetNumber = 10; // Default value

        if (lastTweetNumberString != null && !lastTweetNumberString.isEmpty()) {
            try {
                lastTweetNumber = Integer.parseInt(lastTweetNumberString) + 10;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        try (TweetRepository tweetRepository = new TweetRepository()) {
            TweetService tweetService = new TweetService(tweetRepository);
            comments = tweetService.getCommentsByTweetId(parentId, currentUser.getId(), 0, lastTweetNumber, request);

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
        // Add a new comment to a tweet
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

        String content = request.getParameter("content");
        String parentIdString = request.getParameter("parent");
        if (content == null || content.isEmpty() || parentIdString == null || parentIdString.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Content and Tweet ID parameters are required.");
            return;
        }
        Integer parentId = Integer.valueOf(parentIdString);
        Timestamp postDateTime = new Timestamp(System.currentTimeMillis());

        Tweet comment = new Tweet();
        comment.setUid(currentUser.getId());
        comment.setContent(content);
        comment.setPostDateTime(postDateTime);
        comment.setParentId(parentId);

        try (TweetRepository tweetRepository = new TweetRepository()) {
            TweetService tweetService = new TweetService(tweetRepository);
            tweetService.addComment(comment);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while adding the comment.");
            return;
        }
        // no need to redirect, the html page will handle the display of the new comment
    }
}
