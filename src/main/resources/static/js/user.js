let index = {

    init:function(){
        $("#btn-save").on("click", ()=>{
            this.save();
        });
    },

    save:function (){
       let data = {
           username:$("#username").val(),
           password:$("#password").val(),
           email:$("#email").val()
       };

       // ajax는 default가 비동기 호출
       $.ajax({
           // 회원가입 수행을 요청
           type : "POST",
           url : "/blog/api/user",
           data: JSON.stringify(data), //java가 인식할수 있게 Json으로 전달해 준다.
           contentType: "application/json; charset=utf-8",  //보내는 데이터의 타입
           dataType: "json"  // 받는 데이터의 타입(굳이 없어도 자동으로 json으로 바꿔준다)
       }).done(function (res){
           // 정상이면 실행
           alert("회원가입이 완료 되었습니다.");
           location.href = "/blog";
       }).fail(function (err){
           // 비정상이면 실행
           alert(JSON.stringify(err));
       });
    }
}

index.init();


