window.addEventListener("load", function(event){
    var phone_number_flag = false;
    var auth_number_flag = false;
    var email_flag = false;
    var phone_number_flag2 = false;
    var correct_number = "";

    //email 찾기
    $("#phone_number1").focusout(function() {
        if( $("#phone_number1").val().trim().length<1){
            $("#phone_number1_msg").html('휴대전화번호를 입력해주세요.<br/>');
            phone_number_flag = false;
        }else{
            $("#phone_number1_msg").html('');
            phone_number_flag = true;
        }
    });

    $("#auth_number").focusout(function (){
       if($("#auth_number").val().trim().length<1){
           $("#auth_number_msg").html("인증번호를 입력해주세요.<br/>");
           auth_number_flag = false;
       }else{
           $("#auth_number_msg").html("");
           auth_number_flag = true;
       }
    });

    $("#send_auth").on("click", function(){
        if(phone_number_flag==false){
            $("#phone_number1_msg").html('휴대전화번호를 입력해주세요.<br/>');
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

    $("#verify_auth").on("click", function(){
        if(auth_number_flag==false){
            $("#auth_number_msg").html("인증번호를 입력해주세요.<br/>");
            return;
        }else{
            //추후 back에서 비교하도록 수정
            if($("#auth_number").val()==correct_number){
                $.ajax({
                    type : "POST",
                    url : "/sign/findemail",
                    data : {"phone_number": $("#phone_number1").val()},
                    dataType: "json",
                    success: function (data){
                        var temp = JSON.stringify(data);
                        var map = JSON.parse(temp);
                        $("#member_email").html("회원가입시 사용한 이메일은<br/>"+map.email+" 입니다.");
                    },error : function (data){
                        alert("다시 시도해주세요.");
                    }
                });
            }else{
                $("#auth_number_msg").html("인증번호가 일치하지 않습니다.<br/>");
            }
        }
    });

    //비밀번호 찾기
    $("#email").focusout(function (){
        if($("#email").val().trim().length<1){
            $("#email_msg").html("이메일을 입력해주세요.<br/>");
            email_flag = false;
        }else{
            $("#email_msg").html("");
            email_flag = true;
        }
    });

    $("#phone_number2").focusout(function (){
        if($("#phone_number2").val().trim().length<1){
            $("#phone_number2_msg").html("휴대전화번호를 입력해주세요.<br/>");
            phone_number_flag2 = false;
        }else{
            $("#phone_number2_msg").html("");
            phone_number_flag2 = true;
        }
    });

    $("#passwd_btn").on("click", function (){
        if(email_flag==false){
            $("#email_msg").html("이메일을 입력해주세요.<br/>");
            return;
        }else if(phone_number_flag2==false){
            $("#phone_number2_msg").html("휴대전화번호를 입력해주세요.<br/>");
            return;
        }else{
            $.ajax({
                type:"POST",
                url:"/sign/findpasswd",
                data : {"email": $("#email").val(), "phone_number":$("#phone_number2").val()},
                dataType: "json",
                success: function (data){
                    var temp = JSON.stringify(data);
                    var map = JSON.parse(temp);
                    if(map.result==1){
                        alert(map.msg);
                        location.replace("/signin");
                    }else {
                        alert(map.msg);
                        location.replace("/findaccount");
                    }
                },error : function (data){
                    alert("다시 시도해주세요.");
                }
            });
        }
    });

});