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

    @Autowired
    private UserRepository userRepository;

    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id){
        try{
            userRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            return "해당 id는 DB에 없는 값 입니다.";
        }
        return id + " 가 삭제 되었습니다. ";
    }

    // update를 해야하기 떄문에 Put으로 받게 된다.
    @PutMapping("/dummy/user/{id}")
     @Transactional  // 트렌젝셔널을 사용하면 save를 따로 하지 않아도 된다 == 더티 체킹
    public String updateUser(@PathVariable int id, @RequestBody User requestUser){

        User user = userRepository.findById(id).orElseThrow(null);
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());
        // userRepository.save(user);
        return "업데이트 완료";
    }

    @GetMapping("/dummy/user")
    public List<User> list(){
        return userRepository.findAll();
    }

    @GetMapping("/dummy/user/page")
    public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
    //  1번
       //  Page<User> users = userRepository.findAll(pageable);
     // 2번
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


    //http://localhost:8080/blog/dummy/join (요청)
    // http의 body에 아래 변수3개의 데이터를 가지고 요청
    @PostMapping("/dummy/join2")
    public String join2(User user){
        System.out.println("username : " + user.getUsername());
        System.out.println("password : " + user.getPassword());
        System.out.println("email : " + user.getEmail());
        user.setRole(RoleType.USER);

        userRepository.save(user);
        return"회원가입 완료";
    }

}
