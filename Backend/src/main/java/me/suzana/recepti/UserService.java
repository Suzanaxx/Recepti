package me.suzana.recepti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public User registerUser(String username, String email, String password) {
        // Check if username exists
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Check if email exists
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // Create a new user
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password); // Hash the password

        // Save and return the user
        return userRepository.save(user);
    }


    public Optional<User> loginUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && password.equals(user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }
}
