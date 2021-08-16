package com.cos.blog.test;

import com.cos.blog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import repository.UserRepository;

@RestController
public class DummyControllerTest {

    @Autowired
    private UserRepository userRepository;

    //http://localhost:8080/blog/dummy/join (요청)
    // http의 body에 아래 변수3개의 데이터를 가지고 요청
    @PostMapping("/dummy/join")
    public String join(String username, String password, String email){
        System.out.println("username" + username);
        System.out.println("password" + password);
        System.out.println("email" + email);
        return"회원가입 완료";
    }

    @PostMapping("/dummy/join2")
    public String join2(User user){
        System.out.println("username" + user.getUsername());
        System.out.println("password" + user.getPassword());
        System.out.println("email" + user.getEmail());

        userRepository.save(user);
        return"회원가입 완료";
    }

}
