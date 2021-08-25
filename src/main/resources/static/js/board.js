let index = {

    init:function(){
        $("#btn-save").on("click", ()=>{
            this.save();
        });
        $("#btn-delete").on("click", ()=>{
            this.deleteId();
        });
        $("#btn-update").on("click", ()=>{
            this.update();
        });
    },

    save:function (){
       let data = {
           title:$("#title").val(),
           content:$("#content").val(),
       };
       $.ajax({
           type : "POST",
           url : "/api/board",
           data: JSON.stringify(data),
           contentType: "application/json; charset=utf-8",
           dataType: "json"
       }).done(function (res){
           alert("작성이 완료되었습니다.");
           location.href = "/";
       }).fail(function (err){
           alert(JSON.stringify(err));
       });
    },

    deleteId:function (){
        let id = $("#id").text();
        $.ajax({
            type : "DELETE",
            url : "/api/delete/" +id,
            dataType: "json"
        }).done(function (res){
            alert("삭제가 완료 되었습니다.");
            location.href = "/";
        }).fail(function (err){
            alert(JSON.stringify(err));
        });
    },

    update:function (){
        let id = $("#id").val();

        let data = {
            title:$("#title").val(),
            content:$("#content").val(),
        };
        $.ajax({
            type : "PUT",
            url : "/api/update/"+id,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (res){
            alert("수정이 완료되었습니다.");
            location.href = "/";
        }).fail(function (err){
            alert(JSON.stringify(err));
        });
    },
}
index.init();


