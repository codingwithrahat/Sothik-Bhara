package com.turtlesltd.sothikbhara.user;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean register(User user){
        if(userRepository.existsByEmail(user.getEmail())){
            return false;
        }
        userRepository.save(user);
        return true;
    }

    public User authenticate(String email, String pass){
        User user = userRepository.findByEmail(email);
        if(user == null || !user.getPassword().equals(pass)){
            return null;
        }
        return user;
    }

    public User getUser(Long user_id){
        return userRepository.findById(user_id).orElse(null);
    }

}
