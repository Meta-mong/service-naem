window.addEventListener("load", function(event){
    var phone_number_flag = false;
    var correct_number = "";

    $("#phone_number1").focusout(function() {
        if( $("#phone_number1").val().trim().length<1){
            $("#phone_number1_msg").html('휴대전화번호를 입력해주세요.<br/>');
            phone_number_flag = false;
        }else{
            $("#phone_number1_msg").html('');
            phone_number_flag = true;
        }
    });

    $("#send_auth").on("click", function(){
        if(phone_number_flag==false){
            alert("휴대전화번호를 입력해주세요.");
            return;
        }else{
            $.ajax({
                type: "POST",
                url : "/sign/sendauth",
                data : {"phone_number" :  $("#phone_number1").val()},
                dataType : "json",
                success: function(data){
                    var temp = JSON.stringify(data);
                    var map = JSON.parse(temp);
                    if(map.result == false){
                        alert("존재하지 않는 계정입니다.");
                        $("#phone_number1").prop("value", "");
                    }else{
                        correct_number = map.random;
                        $("#phone_number1_msg").html('인증번호를 확인해주세요<br/>');
                        alert("인증번호가 전송되었습니다.");
                    }
                },
                error: function (data) {
                    alert("다시 시도해주세요.");
                }
            })
        }
    });

    /*
    $(document).on("click", "button[name='add']", function () {
        $("body").append("<button name='add'>+</button>");
    });
    */
});