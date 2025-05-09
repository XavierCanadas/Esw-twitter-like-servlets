package service;

import java.util.*;

import model.User;
import repository.UserRepository;

public class UserService {
	
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	
	private static final String PASSWORD_REGEX = 
	        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,}$";
	
    public Map<String, String> validate(User user) {
        Map<String, String> errors = new HashMap<>();

        String name = user.getUsername();
        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "Username cannot be empty.");
        } else if (name.length() < 5 || name.length() > 20) {
            errors.put("name", "Username must be between 5 and 20 characters.");
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
        Date birthdate = user.getBirthdate();
        if (birthdate == null) {
            errors.put("birthdate", "Please enter a valid date");
        } else {
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
        }

        // Polis validation
        // todo: check the polis in the database also.
        String polis = user.getPolis();
        if (polis == null || polis.trim().isEmpty()) {
            errors.put("polis", "Please select a polis");
        }

        return errors;
    }

    public Map<String, String> register(User user) {
        Map<String, String> errors = validate(user);
        if (errors.isEmpty()) {
            userRepository.save(user);
        }
        return errors;
    }
    
    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}