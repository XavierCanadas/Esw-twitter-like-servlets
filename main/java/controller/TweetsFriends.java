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
@WebServlet("/TweetsFriends")
public class TweetsFriends extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TweetsFriends() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Tweet> tweets = null;
        User user = null;
        HttpSession session = request.getSession(false);

        String lastTweetNumberString = request.getParameter("lastTweetNumber");
        int lastTweetNumber = 10; // Default value

        if (lastTweetNumberString != null && !lastTweetNumberString.isEmpty()) {
            try {
                lastTweetNumber = Integer.parseInt(lastTweetNumberString) + 10;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (session != null) {
            user = (User) session.getAttribute("user");
            if (user != null) {
                try(TweetRepository tweetRepository = new TweetRepository()) {
                    TweetService tweetService = new TweetService(tweetRepository);
                    tweets = tweetService.getFollowingTweets(user.getId(),0, lastTweetNumber);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        request.setAttribute("tweets",tweets);
        request.setAttribute("user",user);
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