<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">

    <form action="/auth/loginProc" method="POST">

        <div class="form-group">
            <label for="username">아이디 : </label>
            <input type="text"  name="username" class="form-control" placeholder="Enter username" id="username">
        </div>

        <div class="form-group">
            <label for="password">비밀번호 : </label>
            <input type="text" name="password" class="form-control" placeholder="Enter password" id="password">
        </div>

        <button id="btn-login" class="btn btn-primary">로그인하기</button>
        <a href="https://kauth.kakao.com/oauth/authorize
                    ?client_id=a04b05cda83548cdeb03c6c32bcd4c40
                    &redirect_uri=http://localhost:8000/auth/kakao/callback
                    &response_type=code">
            <img height="38px" src="/image/button.png">
        </a>

    </form>

</div>

<%@ include file="../layout/footer.jsp"%>