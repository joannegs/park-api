package com.spring.demoparkapi.service;

import com.spring.demoparkapi.entity.User;
import com.spring.demoparkapi.exception.PasswordInvalidException;
import com.spring.demoparkapi.exception.EntityNotFoundException;
import com.spring.demoparkapi.exception.UsernameUniqueViolationException;
import com.spring.demoparkapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User save(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (org.springframework.dao.DataIntegrityViolationException exception) {
            throw new UsernameUniqueViolationException(String.format("Username '%s' is already signup", user.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("User id=%s not found", id)));
    }

    @Transactional
    public User updatePassword(Long id, String currentPassword, String updatedPassword, String updatedPasswordConfirm) {
        if(!updatedPassword.equals(updatedPasswordConfirm)) {
            throw new PasswordInvalidException("The confirmation password does not match the updated password");
        }

        User user = getById(id);
        if(!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new PasswordInvalidException("The password validation did not succeeded");
        }

        user.setPassword(passwordEncoder.encode(updatedPassword));
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(String.format("User username=%s not found", username)));
    }

    @Transactional(readOnly = true)
    public User.Role getRoleByUsername(String username) {
        return userRepository.getRoleByUsername(username);
    }
}
