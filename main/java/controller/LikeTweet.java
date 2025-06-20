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
import repository.LikeTweetRepository;
import repository.TweetRepository;
import service.LikeTweetService;
import service.TweetService;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class LikeTweet
 */
@WebServlet("/LikeTweet")
public class LikeTweet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LikeTweet() {
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

        // set like tweet
        try (LikeTweetRepository likeTweetRepository = new LikeTweetRepository()) {
            Tweet tweet = new Tweet();
            BeanUtils.populate(tweet, request.getParameterMap());
            LikeTweetService likeTweetService = new LikeTweetService(likeTweetRepository);
            likeTweetService.likeTweet(tweet.getId(), currentUser.getId());

        } catch (Exception e) {
            e.printStackTrace();
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
