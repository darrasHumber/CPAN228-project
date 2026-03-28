package com.amalcoders.educonnect.services;


import com.amalcoders.educonnect.models.Role;
import com.amalcoders.educonnect.models.User;
import com.amalcoders.educonnect.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ── Required by Spring Security ───────────
    // Called automatically on login
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found: " + username));
    }

    // ── Register a new user ───────────────────
    public User register(User user) {
        // Encode password before saving — never store plain text
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Default role is STUDENT unless set otherwise
        if (user.getRole() == null) {
            user.setRole(Role.STUDENT);
        }

        return userRepository.save(user);
    }

    // ── Check for duplicate username ──────────
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    // ── Check for duplicate email ─────────────
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    // ── Find by ID ────────────────────────────
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }

    // ── Count all users ───────────────────────
    public long count() {
        return userRepository.count();
    }
}

