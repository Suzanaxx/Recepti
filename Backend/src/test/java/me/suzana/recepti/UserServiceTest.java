package me.suzana.recepti;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService();
        userService.userRepository = userRepository;
    }

    @Test
    void testRegisterUserSuccess() {
        User newUser = new User();
        newUser.setUsername("testuser");
        newUser.setEmail("test@example.com");
        newUser.setPassword("password");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User savedUser = userService.registerUser("testuser", "test@example.com", "password");
        assertNotNull(savedUser);
        assertEquals("testuser", savedUser.getUsername());
    }

    @Test
    void testLoginUserSuccess() {
        User existingUser = new User();
        existingUser.setUsername("testuser");
        existingUser.setPassword("password");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(existingUser));

        Optional<User> loggedIn = userService.loginUser("testuser", "password");
        assertTrue(loggedIn.isPresent());
        assertEquals("testuser", loggedIn.get().getUsername());
    }
}
