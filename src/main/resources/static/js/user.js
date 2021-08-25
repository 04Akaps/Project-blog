let index = {

    init:function(){
        $("#btn-save").on("click", ()=>{
            this.save();
        });
        $("#btn-update").on("click", ()=>{
            this.update();
        });
    },

    save:function (){
       let data = {
           username:$("#username").val(),
           password:$("#password").val(),
           email:$("#email").val()
       };
       $.ajax({
           type : "POST",
           url : "/auth/joinProc",
           data: JSON.stringify(data),
           contentType: "application/json; charset=utf-8",
           dataType: "json"
       }).done(function (res){
           if(res.status == 500){
               alert("회원가입에 실패하셨습니다.");
           }else{
               alert("회원가입이 완료 되었습니다.");
               location.href = "/";
           }

       }).fail(function (err){
           alert(JSON.stringify(err));
       });
    },

    update:function (){
        let data = {
            id:$("#id").val(),
            password:$("#password").val(),
            username:$("#username").val(),
            email:$("#email").val()
        };
        $.ajax({
            type : "PUT",
            url : "/user/update",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (res){
            alert("수정이 완료 되었습니다.");
            location.href = "/";
        }).fail(function (err){
            alert(JSON.stringify(err));
        });
    },

}
index.init();


