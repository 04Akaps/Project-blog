package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;

public interface UserRepository extends JpaRepository<User, Integer>{

    Optional<User> findByUsername(String username);


}

//   User findByUsernameAndPassword(String username, String password);

//    @Query(value = "SELECT * FROM user WHERE username = ?1 AND password = ?2" ,nativeQuery = true)
//    User login(String username, String password);