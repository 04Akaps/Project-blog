package com.cos.blog.controller;


import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.config.auth.PrincipalDetailService;
import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.BoardService;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Controller
public class WebController {
    //http://localhost:8000

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${cos.key}")
    private String cosKey;

    @GetMapping({"", "/"})
    public String index(
            Model model,
            @PageableDefault(size = 3,sort = "id",direction = Sort.Direction.DESC) Pageable pageable
    ){
        model.addAttribute("boards", boardService.글목록(pageable));
        return "index";
    }

    @GetMapping("/board/{id}")
    public String findById(@PathVariable int id, Model model){
        model.addAttribute("board",  boardService.글상세보기(id));

        return "board/detail";
    }

    @GetMapping("/board/updateForm/{id}")
    public String updateForm(@PathVariable int id, Model model){
        model.addAttribute("board", boardService.글상세보기(id));
        return "board/updateForm";
    }



    @GetMapping("/auth/joinform")
    public String joinForm(){
        return "user/joinform";
    }

    @GetMapping("/auth/loginform")
    public String loginForm(){
        return "user/loginform";
    }

    @GetMapping("/board/saveform")
    public String saveForm(){
        return "board/saveform";
    }

    @GetMapping("/user/updateform")
    public String updateform(){
        return "/user/updateform";
    }

    @GetMapping("auth/kakao/callback")
    public String callBack(String code){

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "a04b05cda83548cdeb03c6c32bcd4c40");
        params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
        params.add("code ", code);

        HttpEntity<MultiValueMap<String, String>> TokenRequest =
                new HttpEntity<>(params, headers);

        ResponseEntity<String> response = rt.exchange(
          "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                TokenRequest,
                String.class
        );
        // =================== 토큰을 이용해 정보를 가져오는 코드 =====================
        // Gson, Json Simpel, ObjectMapper 등등 사용 가능
        // json데이터를 자바 오브젝트로 처리하기 위해서 자바 오브젝트로 바꾼 코드이다.

        // 토큰을 생성 하는 코드
        ObjectMapper obMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;
        try {
            oAuthToken = obMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // 토큰을 이용해서 카카오 서버에 있는 사용자의 값을 가져오는 코드
        RestTemplate rt2 = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer "+oAuthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
                new HttpEntity<>(headers2);

        // Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class
        );

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // User의 오브젝트 : username, password, email
        // 카카오 에서 가져오는 값으로 User의 오브젝트를 자동으로 만들어주는 로직을 구현해야한다.
        System.out.println("카카오 아이디(번호) : "+kakaoProfile.getId());
        System.out.println("카카오 이메일 : "+kakaoProfile.getKakao_account().getEmail());

        System.out.println("블로그서버 유저네임 : "+kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
        System.out.println("블로그서버 이메일 : "+kakaoProfile.getKakao_account().getEmail());
        System.out.println("블로그서버 패스워드 : "+cosKey);

        // 카카오에서 받아오는 값으로 바로 회원가입을 진행하는 코드
        User kakaoUser = User.builder()
                .username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
                .password(cosKey)
                .email(kakaoProfile.getKakao_account().getEmail())
                .oauth("kakao")
                .build();
        // password를 암호화 해야하는데 Service로직에서 구현을 해놓았기 떄문에 값만 입력해 주면 된다.

        // 가입이 이미 되어있는지 안되어 있는지 확인을 해야한다.
        User originUser = userService.회원찾기(kakaoUser.getUsername());

        if(originUser == null) {
            System.out.println("기존 회원이 아닙니다.");
            userService.회원가입(kakaoUser);
        }else{
            // 로그인 처리에서 사용했던 세션을 유지시키는 코드를 추가해 준다.
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        return "redirect:/";
    }

}
