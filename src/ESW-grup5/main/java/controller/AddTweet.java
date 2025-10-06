package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import repository.UserRepository;
import service.TweetService;
import model.Tweet;
import model.User;
import repository.TweetRepository;

import java.io.IOException;
import java.sql.Timestamp;

import org.apache.commons.beanutils.BeanUtils;
import service.UserService;

/**
 * Servlet implementation class AddTweet
 */
@WebServlet("/AddTweet")
public class AddTweet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddTweet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                try (TweetRepository tweetRepository = new TweetRepository()) {
                    Tweet tweet = new Tweet();
                    BeanUtils.populate(tweet, request.getParameterMap());
                    tweet.setUid(user.getId());
                    tweet.setUsername(user.getUsername());
                    tweet.setPostDateTime(new Timestamp(System.currentTimeMillis()));
                    TweetService tweetService = new TweetService(tweetRepository);
                    tweetService.add(tweet);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
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
