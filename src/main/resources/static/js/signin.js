window.addEventListener("load", function(event){
    var loginbtn = document.getElementById("loginbtn");

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

    loginbtn.addEventListener("click", function(event) {
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

        var signinfo = {email: $("#email").val(), passwd: $("#passwd").val()}
        alert(signinfo.email);
        console.log($("#login").serialize().val());
        $.ajax({
            type: "POST",
            url: "/sign/signin",
            contentType: "application/json; charset=utf-8",
            data: $("#login").serialize(),
            success: function (data) {
                alert(data);
                var map = JSON.parse(data);
                alert(map.msg);
                if (map.result == 1) {
                    $(location).attr('href', '/sign/main');
                }
            },
            error: function (data, statusText) {
                alert("에러 발생");
                var map = JSON.parse(data);
                alert(statusText);
            }
        });
    });
});