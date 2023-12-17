package com.spring.demoparkapi.service;

import com.spring.demoparkapi.entity.User;
import com.spring.demoparkapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found."));
    }

    @Transactional
    public User updatePassword(Long id, String currentPassword, String updatedPassword, String updatedPasswordConfirm) {
        if(!updatedPassword.equals(updatedPasswordConfirm)) {
            throw new RuntimeException("Updated password does not match the password confirmation");
        }

        User user = getById(id);
        if(!user.getPassword().equals(currentPassword)) {
            throw new RuntimeException("The current password is not correct");
        }

        user.setPassword(updatedPassword);
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
