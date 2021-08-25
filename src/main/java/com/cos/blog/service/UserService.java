package com.cos.blog.service;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public User 회원찾기(String username){
        return userRepository.findByUsername(username).orElseGet(()->{
            return new User();
        });
    }

    @Transactional
    public void 회원가입(User user){
        String encPassword = encoder.encode(user.getPassword());

        user.setPassword(encPassword);
        user.setRole(RoleType.USER);
    }

    @Transactional
    public void 회원수정(User user){
       User user1 = userRepository.findById(user.getId()).orElseThrow(()->{
           return new IllegalArgumentException("글 찾기 실패 : 해당 아이디가 없습니다.");
       });

       if(user1.getOauth() == null || user1.getOauth().equals("")){
           String encPassword = encoder.encode( user.getPassword());
           user1.setPassword(encPassword);
           user1.setEmail(user.getEmail());
       }
    }

}
