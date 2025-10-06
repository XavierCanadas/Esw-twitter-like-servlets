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

import org.apache.commons.beanutils.BeanUtils;

/**
 * Servlet implementation class DelTweet
 */
@WebServlet("/DelTweet")
public class DelTweet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DelTweet() {
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
					TweetService tweetService = new TweetService(tweetRepository);
					tweetService.delete(tweet.getId(),user.getId());
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
