window.addEventListener("load", function() {
    $("#modify_btn").on("click", function(){
        $.ajax({
           url : "/user/modifyPasswd",
           type : "POST",
           data : {"email" : $("#email").val(), "passwd" : $("#passwd").val()},
           dataType : "json",
           success : function (data){
               alert(data.result);
               location.replace("/");
           },
           error : function (data){
               alert("비밀번호 수정 실패. 다시 시도해주세요.");
           }
       })
    });

    $("#resign_btn").on("click", function(){
        var passwd = prompt('현재 비밀번호를 입력해주세요', '');

        $.ajax({
            url : "/user/unregister",
            type: "POST",
            data : {"passwd" : passwd},
            dataType: "json",
            success : function (data){
                if(data.result==true){
                    alert("회원 탈퇴가 완료되었습니다.");
                    location.replace("/");
                }else {
                    alert("비밀번호가 일치하지 않습니다. 다시 시도해주세요.");
                }
            },
            error : function (data){
                alert("회원 탈퇴 실패. 다시 시도해주세요.");
            }
        });
    });
});