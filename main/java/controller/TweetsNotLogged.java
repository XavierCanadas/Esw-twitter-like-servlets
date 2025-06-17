package controller;

import jakarta.servlet.RequestDispatcher;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servlet implementation class Tweets
 */
@WebServlet("/TweetsNotLogged")
public class TweetsNotLogged extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
	    List<Tweet> tweets = null;
        User user = null;
    	String username = request.getParameter("username");  // Ahora recibimos username por AJAX
    	if (username != null && !username.isEmpty())
    		System.out.print(username);
    	try (TweetRepository tweetRepo = new TweetRepository();
    	     UserRepository userRepo = new UserRepository();) {
    		
   		 TweetService tweetService = new TweetService(tweetRepo);
   		 UserService userService= new UserService(userRepo);
    	    if (username != null && !username.isEmpty()) {
    	        user = userService.findByName(username);
    	            // Obtenemos tweets del usuario usando su ID
    	        tweets = tweetService.getTweetsByUser(user.getId(), 0, 20);
  
    	    } else {
    	        tweets = tweetService.getLatestTweets();  // Tweets generales
    	    }
   	        request.setAttribute("user", user);  
    	    request.setAttribute("tweets", tweets);
    	    RequestDispatcher dispatcher = request.getRequestDispatcher("TweetsNotLogged.jsp");
    	    dispatcher.forward(request, response);

    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(jakarta.servlet.http.HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		doGet(request, response);
	}
}
