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
@WebServlet("/TweetView")
public class TweetView extends HttpServlet {

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TweetView() {
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
        String tweetIdString = request.getParameter("tweetId");
        if (tweetIdString == null || tweetIdString.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tweet ID parameter is required.");
            return;
        }

        Integer tweetId = Integer.valueOf(tweetIdString);

        // get tweet info
        try (TweetRepository tweetRepository = new TweetRepository()) {
            TweetService tweetService = new TweetService(tweetRepository);
            Tweet tweet = tweetService.getTweetById(tweetId, currentUser.getId(), request);
            if (tweet == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Tweet not found.");
                return;
            }
            request.setAttribute("tweet", tweet);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving tweet.");
            return;
        }

        // get and set editing tweet
        String isEditing = request.getParameter("isEditing");
        if (isEditing != null && isEditing.equals("true")) {
            request.setAttribute("isEditing", true);
        } else {
            request.setAttribute("isEditing", false);
        }

        // send
        request.setAttribute("user", currentUser);
        request.getRequestDispatcher("TweetView.jsp").forward(request, response);

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}
