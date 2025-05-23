package service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Part;
import model.Polis;
import model.User;
import repository.UserRepository;

public class UserService {
	
	private UserRepository userRepository;
	private ServletContext servletContext;

	public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserService(UserRepository userRepository, ServletContext servletContext) {
        this.userRepository = userRepository;
        this.servletContext = servletContext;
    }

    private static final String UPLOAD_DIRECTORY = "uploads";
	private static final String PASSWORD_REGEX =
	        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,}$";

    public void savePicture(User user, Part filePart) throws IOException {

        if (filePart == null || filePart.getSize() == 0) {
            user.setPicture("default.png");
            return;
        }

        // Use a fixed relative path from the project root directory
        String projectRoot = System.getProperty("user.dir");
        String uploadPath = projectRoot + File.separator + "images" + File.separator + UPLOAD_DIRECTORY;

        uploadPath = servletContext.getRealPath("");

        // Ensure directory exists
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // Use mkdirs() to create parent directories if needed
        }

        String originalName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String extension = "";
        String repositoryName = "";
        int dotIndex = originalName.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = originalName.substring(dotIndex);
            repositoryName = user.getUsername() + extension;
        }
        File file = new File(uploadPath + File.separator + repositoryName);
        try (InputStream fileContent = filePart.getInputStream()) {
            Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            user.setPicture(repositoryName);
        }
    }
	
    public Map<String, String> validate(User user) {
        Map<String, String> errors = new HashMap<>();

        String name = user.getUsername();
        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "Username cannot be empty.");
        } else if (name.length() < 3 || name.length() > 15) {
            errors.put("name", "Username must be between 3 and 15 characters.");
        } else if (userRepository.existsByUsername(name)) {
            errors.put("name", "Username already exists.");
        }

        String password = user.getPassword();
        if (password == null || !password.matches(PASSWORD_REGEX)) {
            errors.put("password", "Minimum 8 characters, including uppercase, number, and special character: *[!@#$%^&*]");
        }

        // Gender validation
        String gender = user.getGender();
        if (gender == null || gender.trim().isEmpty()) {
            errors.put("gender", "Please select a gender");
        }

        // Birthdate validation


        String birthdateStr = user.getBirthdateString();
        Date birthdate = null;

        if (birthdateStr == null || birthdateStr.trim().isEmpty()) {
            errors.put("birthdate", "Please enter a valid date");
        } else {
            try {
                // Parse the string to a Date object using SimpleDateFormat
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dateFormat.setLenient(false);
                birthdate = dateFormat.parse(birthdateStr);

                // Check if birthdate is in the future
                Date today = new Date();
                if (birthdate.after(today)) {
                    errors.put("birthdate", "Birth date cannot be in the future");
                } else {
                    // Calculate age
                    Calendar todayCal = Calendar.getInstance();
                    Calendar birthdateCal = Calendar.getInstance();
                    todayCal.setTime(today);
                    birthdateCal.setTime(birthdate);

                    int age = todayCal.get(Calendar.YEAR) - birthdateCal.get(Calendar.YEAR);

                    // Adjust age if birthday hasn't occurred yet this year
                    if (todayCal.get(Calendar.MONTH) < birthdateCal.get(Calendar.MONTH) ||
                            (todayCal.get(Calendar.MONTH) == birthdateCal.get(Calendar.MONTH) &&
                                    todayCal.get(Calendar.DAY_OF_MONTH) < birthdateCal.get(Calendar.DAY_OF_MONTH))) {
                        age--;
                    }

                    if (age < 18) {
                        errors.put("birthdate", "You must be at least 18 years old");
                    }
                }
            } catch (ParseException e) {
                errors.put("birthdate", "Invalid date format. Please use YYYY-MM-DD");
            }
        }

        String email = user.getEmail();
        if (email != null && !email.isEmpty()) {
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            if (!email.matches(emailRegex)) {
                errors.put("email", "Please enter a valid email address");
            }
        }

        // Polis validation
        // todo: check the polis in the database also.
        Polis polis = user.getPolis();
        if (polis == null || polis.getId() <= 0) {
            errors.put("polis", "Please select a polis");
        }

        return errors;
    }

    public Map<String, String> register(User user, Part filePart) throws IOException {
        Map<String, String> errors = validate(user);
        if (errors.isEmpty()) {
            /*
            try {
                savePicture(user,filePart);
            } catch (IOException e) {
                errors.put("picture", "Error saving the picture: " + e.getMessage());
            }
            */

            userRepository.save(user);
        }
        return errors;
    }

    public Map<String, String> login(User user) throws IOException {
        Map<String, String> errors = new HashMap<>();
        if (!userRepository.checkLogin(user)) {
            errors.put("password","The combination of name and password does not match in our dataabase");
        }
        return errors;
    }
    
    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}

