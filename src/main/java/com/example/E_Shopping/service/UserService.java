package com.example.E_Shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.E_Shopping.model.ResponseObject;
import com.example.E_Shopping.model.Users;
import com.example.E_Shopping.repository.UserRepository;
import java.util.*;

@Service
public class UserService implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Autowired
    UserRepository userRepository;

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    // public ResponseObject saveUser(Users user) {
    //     Users savedUser = userRepository.save(user);
    //     return new ResponseObject("ok", "Them nguoi dung thanh cong", savedUser);
    // }

    public ResponseObject getUserById(Long id) {
        Optional<Users> foundUser = userRepository.findById(id);
        if (foundUser.isPresent()) {
            return new ResponseObject("ok", "Tim nguoi dung thanh cong", foundUser.get());
        }
        return new ResponseObject("Failed", "Khong tim thay nguoi dung", "");
    }

    public ResponseObject deleteUserById(Long id) {
        boolean exist = userRepository.existsById(id);
        if (exist) {
            userRepository.deleteById(id);
            return new ResponseObject("ok", "Xoa nguoi dung thanh cong", getUserById(id));
        }

        return new ResponseObject("Failed", "Khong tim duoc nguoi dung id = " + id + " de xoa ", "");
    }

    public ResponseObject updateUserById(Users newUser, Long id) {
        if (existById(id)) {
            Users updatedUser = userRepository.findById(id).map(
                    user -> {
                        user.setUsername(newUser.getUsername());
                        user.setPassword(newUser.getPassword());
                        user.setEmail(newUser.getEmail());
                        return userRepository.save(user);
                    }).orElseGet(() -> {
                        return userRepository.save(newUser);
                    });

            return new ResponseObject("ok", "Sua thong tin nguoi dung thanh cong", updatedUser);
        } else {
            return new ResponseObject("Failed", "Sua khong thanh cong", "");
        }

    }

    public boolean existById(Long id) {
        return this.userRepository.existsById(id);
    }

    public ResponseObject loginUser(String username, String password) {
        Optional<Users> user = userRepository.findByUsername(username);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return new ResponseObject("ok", "Dang nhap thanh cong", user.get());
        } else {
            return new ResponseObject("Failed", "Dang nhap that bai", "");

        }

    }
    public ResponseObject registerUser(Users user){
        Optional<Users> foundUser = userRepository.findByUsername(user.getUsername());
        Optional<Users> foundEmail = userRepository.findByEmail(user.getEmail());
        if(foundUser.isPresent() || foundEmail.isPresent()){
            return new ResponseObject("Failed", "Dang ky that bai", "");
        }
        
        return new ResponseObject("ok", "Dang ky thanh cong", userRepository.save(user));

        
    }

}
