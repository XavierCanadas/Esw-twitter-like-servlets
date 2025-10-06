package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.TweetService;
import service.UserService;
import model.Tweet;
import model.User;
import repository.TweetRepository;
import repository.UserRepository;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

/**
 * Servlet implementation class AddTweet
 */
@WebServlet("/Navegation")
public class Navegation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Navegation() {
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
			
			}
		
		request.getRequestDispatcher("Navegation.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
