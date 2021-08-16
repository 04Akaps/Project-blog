package com.cos.blog.test;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


// Spring이 blog 패키지 이하의 파일을 모두 스캔해서 메모리에 보관하는 것이 아니다
//  특정 어노테이션이 붙은 클래스 파일들을 new해서(ioc) 컨테이너에 관리해 준다.
@Controller
public class ConController {

    @GetMapping("/temp")
    public String hello(){
        return "/test";
    }

}
