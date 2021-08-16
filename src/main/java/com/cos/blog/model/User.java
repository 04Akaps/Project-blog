package com.cos.blog.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 30)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 100)
    private String email;


    @Enumerated(EnumType.STRING)    //DB에는 RoleType가 없기 떄문에 String이라고 표기를 해주어야 한다.
    private RoleType role;
    //    직접 값을 넣어야할떄에는 user.setRole(RoleType.USER) 같이 넣어주면 된다.

   @CreationTimestamp
    private Timestamp createDate;

}
