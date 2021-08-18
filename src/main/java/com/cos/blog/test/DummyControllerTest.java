package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Supplier;


@RestController
public class DummyControllerTest {
    //http://localhost:8080/blog

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/dummy/join2")
    public String join2(User user){
        user.setRole(RoleType.USER);

        userRepository.save(user);
        return"회원가입 완료";
    }

    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id){
        try{
            userRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            return "해당 id는 DB에 없는 값 입니다.";
        }
        return "삭제 완료";
    }

    @PutMapping("/dummy/user/{id}")
    @Transactional
    public String updateUser(@PathVariable int id, @RequestBody User requestUser){

        User user = userRepository.findById(id).orElseThrow(null);
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());

        return "업데이트 완료";
    }

    @GetMapping("/dummy/user")
    public List<User> list(){
        return userRepository.findAll();
    }

    @GetMapping("/dummy/user/page")
    public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        List<User> users = userRepository.findAll(pageable).getContent();
        return users;
    }

    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) {

        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 사용자가 없습니다.");
            }
        });
        return user;
    }

}
