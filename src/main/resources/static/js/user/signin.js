window.addEventListener("load", function(event){


    var signinbtn = document.getElementById("signinbtn");

    var email = document.getElementById("email");
    var emailmsg = document.getElementById("emailmsg");
    var passwd = document.getElementById("passwd");
    var passwdmsg = document.getElementById("passwdmsg");

    email.addEventListener("focusout", function (event) {
        if (email.value.trim().length < 1) {
            emailmsg.innerHTML = 'email은 필수 입력입니다.<br/>';
        } else {
            var emailRegExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
            if (!emailRegExp.test(email.value.trim())) {
                emailmsg.innerHTML = '잘못된 이메일 형식입니다.<br/>';
            }else{
                emailmsg.innerText = "";
            }
        }
    });

    passwd.addEventListener("focusout", function () {
        if (passwd.value.trim().length < 1) {
            passwdmsg.innerHTML = '비밀번호를 입력해주세요.<br/>';
        }else{
            passwdmsg.innerText = "";
        }
    });

    signinbtn.addEventListener("click", function(event) {
        var flag = true;

        if (email.value.trim().length < 1) {
            emailmsg.innerHTML = 'email은 필수 입력입니다.<br/>';
            flag=false;
        } else {
            var emailRegExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
            if (!emailRegExp.test(email.value.trim())) {
                emailmsg.innerHTML = '잘못된 이메일 형식입니다.<br/>';
                flag=false;
            }
        }

        if (passwd.value.trim().length < 1) {
            passwdmsg.innerHTML = '비밀번호를 입력해주세요.<br/>';
            flag=false;
        }

        if (flag == false) {
            return;
        }

        var formdata = $("#login").serialize();

        $.ajax({
            type: "POST",
            url: "/sign/signin",
            //contentType: "application/json; charset=utf-8",
            data: formdata,
            dataType: "json",
            success: function (data, statusText) {
                var temp = JSON.stringify(data);
                var map = JSON.parse(temp);
                alert(map.msg);
                if (map.result == 1) {
                    //$(location).attr('href', 'redirect:/');
                    location.replace("/");
                }
            },
            error: function (data, statusText) {
                alert("에러 발생");
                var temp = JSON.stringify(data);
                var map = JSON.parse(temp);
            }
        });
    });


    //Kakao.init('d9d2bdcf0867cf97434d1dd0d86fbd02');
    Kakao.init($("#rest-api").text());
    Kakao.isInitialized();

    $("#kakao").on("click", function(){
        Kakao.Auth.authorize({
            redirectUri : $("#redirect-uri").text()
        });
    });


});