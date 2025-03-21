package com.example.E_Shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.E_Shopping.auth.JwtUtils;
import com.example.E_Shopping.model.ResponseObject;
import com.example.E_Shopping.model.Roles;
import com.example.E_Shopping.model.UserAndRole;
import com.example.E_Shopping.model.Users;
import com.example.E_Shopping.repository.RoleRepository;
import com.example.E_Shopping.repository.UserAndRoleRepository;
import com.example.E_Shopping.repository.UserRepository;

import co.elastic.clients.elasticsearch.security.User;

import java.util.*;
import java.util.stream.Collectors;

import javax.xml.crypto.Data;

@Service
public class UserService implements WebMvcConfigurer {


    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserAndRoleRepository userAndRoleRepository;

    @Autowired
    private UserAndRoleService userAndRoleService;
    
    @Autowired
    private RoleRepository roleRepository;
    // Tiêm BCryptPasswordEncoder
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    
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
                        // user.setUsername(newUser.getUsername());
                        // user.setPassword(passwordEncoder.encode(newUser.getPassword()));
                        // user.setEmail(newUser.getEmail());
                        // user.setBirthDay(newUser.getBirthDay());
                        user.setGender(newUser.getGender());
                        user.setName(newUser.getName());
                        user.setNumber(newUser.getNumber());
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

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {

            List<UserAndRole> userRoles = userAndRoleRepository.findByUsersUserId(user.get().getUserId());
            Optional<Users> foundUser = userRepository.findByUsername(username);
            Date bithDay = foundUser.get().getBirthDay();
            String email = foundUser.get().getEmail();
            String number = foundUser.get().getNumber();
            int gender = foundUser.get().getGender();
            Date createdAt = foundUser.get().getCreatedAt();
            Date updatedAt = foundUser.get().getUpdatedAt();
            String name = foundUser.get().getName();
            Set<String> roles = userRoles.stream()
                                         .map(ur -> ur.getRoles().getRoleName()) // Lấy role từ UserAndRole
                                         .collect(Collectors.toSet());
            
            System.out.println("Danh sách roles của user: " + roles);

            String token = jwtUtils.generateToken(user.get().getUserId(), username,email, roles,bithDay,number,gender,createdAt,updatedAt, name);

            return new ResponseObject("ok", "Đăng nhập thành công", token);
        } else {
            return new ResponseObject("Failed", "Đăng nhập thất bại", "");
        }
    }
    public ResponseObject registerUser(Users user) {
        Optional<Users> foundUser = userRepository.findByUsername(user.getUsername());
        Optional<Users> foundEmail = userRepository.findByEmail(user.getEmail());

        if (foundUser.isPresent() || foundEmail.isPresent()) {
            return new ResponseObject("failed", "Đăng ký thất bại, tài khoản hoặc email đã tồn tại", "");
        }

        // Mã hóa mật khẩu trước khi lưu
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Roles userRole = roleRepository.findByRoleName("USER");
        

        Users savedUser = userRepository.save(user);

        UserAndRole userAndRole = new UserAndRole();
        userAndRole.setRoles(userRole);
        userAndRole.setUsers(savedUser);

        userAndRoleService.saveUserAndRoleService(userAndRole);
        return new ResponseObject("ok", "Đăng ký thành công", savedUser);
    }

}
