package controller;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Polis;
import model.User;
import repository.PolisRepository;
import repository.UserRepository;
import service.PolisService;
import service.UserService;
import jakarta.servlet.ServletContext;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;

/**
 * Servlet implementation class Register
 */
@MultipartConfig
@WebServlet("/Register")
public class Register extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try (PolisRepository polisRepository = new PolisRepository()) {
			PolisService polisService = new PolisService(polisRepository);
			List<Polis> polisList = polisService.getAllPolis();
			request.setAttribute("polisList", polisList);
			request.getRequestDispatcher("Register.jsp").forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		User user = new User();

		try (PolisRepository polisRepository = new PolisRepository();
			 UserRepository userRepository = new UserRepository()) {

			BeanUtils.populate(user, request.getParameterMap());

			String polisIdParam = request.getParameter("polisId");
			if (polisIdParam != null && !polisIdParam.isEmpty()) {
				int polisId = Integer.parseInt(polisIdParam);
				Optional<Polis> polis = polisRepository.findById(polisId);
				if (polis.isPresent()) {
					user.setPolis(polis.get());
				}
			}

			// profile picture
			String picturePath = request.getParameter("picturePath");
			user.setPicture(picturePath);
			UserService userService = new UserService(userRepository, getServletContext());
			Map<String, String> errors = userService.register(user);

			if (errors.isEmpty()) {
				request.setAttribute("user", user);
				request.getRequestDispatcher("Login.jsp").forward(request, response);
			} else {
				PolisService polisService = new PolisService(polisRepository);
				List<Polis> polisList = polisService.getAllPolis();
				request.setAttribute("polisList", polisList);

				request.setAttribute("user", user);
				request.setAttribute("errors", errors);
				request.getRequestDispatcher("Register.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "An error occurred while processing your request.\n" + e.getMessage());
			request.getRequestDispatcher("Register.jsp").forward(request, response);
		}
		
	}

}

