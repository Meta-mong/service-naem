window.addEventListener("load", function(event){
    var email = document.getElementById("email");
    var emailck = document.getElementById("emailck");
    var emailmsg = document.getElementById("emailmsg");
    var passwd = document.getElementById("passwd");
    var passwdmsg = document.getElementById("passwdmsg");
    var passwdck = document.getElementById("passwdck");
    var passwdckmsg = document.getElementById("passwdckmsg");
    var name = document.getElementById("name");
    var namemsg = document.getElementById("namemsg");
    var age = document.getElementById("age");
    var agemsg = document.getElementById("agemsg");
    var number = document.getElementById("number");
    var numberck = document.getElementById("numberck");
    var authnumber = document.getElementById("authnumber");
    var smsck = document.getElementById("smsck");
    var numbermsg = document.getElementById("numbermsg");
    var signupbtn = document.getElementById("signupbtn");

    var flag = false;
    var smsauth = false;
    var correctnumber = null;


    email.addEventListener("focusout", function(event) {
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

    passwd.addEventListener("focusout", function(event) {
        if (passwd.value.trim().length < 1) {
            passwdmsg.innerHTML = '비밀번호를 입력해주세요.<br/>';
        }else{
            var passwdRegExp =  /^.*(?=^.{8,12}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
            if(!passwdRegExp.test(passwd.value.trim())){
                passwdmsg.innerHTML = '영문,숫자,특수문자가 각각 하나 이상 들어가는 8~12자 사이의 비밀번호를 입력해주세요.<br/>';
            } else {
                passwdmsg.innerText = "";
            }
        }
    });

    passwdck.addEventListener("focusout", function(event) {
        if (passwdck.value.trim().length < 1) {
            passwdckmsg.innerHTML = "비밀번호를 확인해주세요.<br/>";
        }else{
            if(passwdck.value===passwd.value){
                passwdckmsg.innerText = "";
            }else{
                passwdckmsg.innerHTML = "비밀번호가 같지 않습니다. 다시 확인해주세요.";
            }
        }
    });


    name.addEventListener("focusout", function(event) {
        if(name.value.trim().length<1){
            namemsg.innerHTML = "이름은 필수 입력입니다.";
        }else{
            namemsg.innerText = "";
        }
    });

    age.addEventListener("focusout", function(event) {
        if(age.value.trim().length<1){
            agemsg.innerHTML = "생년월일은 필수 입력입니다.";
        }else{
            agemsg.innerText = "";
        }
    });

    number.addEventListener("focusout", function(event) {
        if(number.value.trim().length<1){
            numbermsg.innerHTML = "전화번호는 필수 입력입니다.";
        }else{
            numbermsg.innerText = "";
        }
    });

    emailck.addEventListener("click", function (event) {
        if (email.value.trim().length < 1) {
            emailmsg.innerHTML = 'email은 필수 입력입니다.<br/>';
            return;
        } else {
            var emailRegExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
            if (!emailRegExp.test(email.value.trim())) {
                emailmsg.innerHTML = '잘못된 이메일 형식입니다.<br/>';
                return;
            } else {
                $.ajax({
                    url: "/sign/emailcheck",
                    type: "POST",
                    //contentType: "application/json; charset=utf-8",
                    data: {"email":email.value},
                    dataType: "text",
                    success: function (data, statusText) {
                        if(data=="true") { //중복
                            emailmsg.innerHTML = '이미 사용 중인 이메일입니다.<br/>';
                        }else{
                            emailmsg.innerHTML = '사용 가능한 이메일입니다.<br/>';
                        }
                    },
                    error: function (data, statusText) {
                        alert("에러 발생");
                    }
                });
            }
        }
    });

    numberck.addEventListener("click", function(){
        if(number.value.trim().length<1){
            numbermsg.innerHTML = "전화번호는 필수 입력입니다.";
            return;
        }else{
            numbermsg.innerText = "";
        }

        $.ajax({
            type: "POST",
            url: "/sign/numbercheck",
            data: {"number":number.value},
            dataType: "json",
            success: function (data, statusText) {
                var temp = JSON.stringify(data);
                var map = JSON.parse(temp);
                //alert(JSON.stringify(map.result));
                if(map.result == "-1") { //중복 & 전송 실패
                    smsauth = false;
                    numbermsg.innerHTML = map.message;
                }else{
                    smsauth = true;
                    numbermsg.innerText = map.message;
                    correctnumber = map.random;
                    $("#number").prop("type", "hidden");
                    $("#numberck").prop("type", "hidden");
                    $("#authnumber").prop("type", "text");
                    $("#smsck").prop("type", "button")
                }
            },
            error: function (data, statusText) {
                alert("에러 발생");
                var temp = JSON.stringify(data);
                var map = JSON.parse(temp);
            }
        });
    });

    smsck.addEventListener("click", function (){
        if(authnumber.value==correctnumber){
            numbermsg.innerHTML = "인증되었습니다.</br>";
            smsauth = true;
        }else{
            numbermsg.innerHTML = "인증에 실패하였습니다.</br>";
            smsauth = false;
        }
    });


    signupbtn.addEventListener("click", function (){
        emailcheck();
        passwdcheck();
        passwddoublecheck();
        namecheck();
        agecheck();
        numbercheck();
        if(flag==true && smsauth==true){
            $.ajax({
                type: "POST",
                url: "/sign/signup",
                //contentType: "application/json; charset=utf-8",
                data: $("#signup").serialize(),
                dataType: "json",
                success: function (data, statusText) {
                    var temp = JSON.stringify(data);
                    var map = JSON.parse(temp);
                    if(map.result == 1) { //회원가입 성공
                        alert(map.msg);
                        location.replace("/");
                    }else{
                        alert(map.msg);
                    }
                },
                error: function (data, statusText) {
                    alert("에러 발생");
                    var temp = JSON.stringify(data);
                    var map = JSON.parse(temp);
                }
            });
        }else{
            alert("입력란을 모두 채워주세요.");
            return;
        }
    });


    //함수 정의
    function emailcheck(){
        if (email.value.trim().length < 1) {
            $("#emailmsg").innerHTML = 'email은 필수 입력입니다.<br/>';
            flag = false
        } else {
            var emailRegExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
            if (!emailRegExp.test(email.value.trim())) {
                $("#emailmsg").innerHTML = '잘못된 이메일 형식입니다.<br/>';
                flag = false
            }else{
                $("#emailmsg").innerText = "";
                flag=true
            }
        }
    }

    function passwdcheck(){
        if (passwd.value.trim().length < 1) {
            $("#passwdmsg").innerHTML = '비밀번호를 입력해주세요.<br/>';
            flag = false
        }else{
            var passwdRegExp = /^.*(?=^.{8,12}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
            if(!passwdRegExp.test(passwd.value.trim())){
                $("#passwdmsg").innerHTML = '영문,숫자,특수문자가 각각 하나 이상 들어가는 8~12자 사이의 비밀번호를 입력해주세요.<br/>';
                flag = false
            } else {
                $("#passwdmsg").innerText = "";
                flag=true
            }
        }
    }

    function passwddoublecheck(){
        if (passwdck.value.trim().length < 1) {
            $("#passwdckmsg").innerHTML = "비밀번호를 확인해주세요.<br/>";
            flag = false
        }else{
            if(passwdck.value===passwd.value){
                $("#passwdckmsg").innerText = "";
                flag=true
            }else{
                $("#passwdckmsg").innerHTML = "비밀번호가 같지 않습니다. 다시 확인해주세요.";
                flag = false
            }
        }
    }

    function namecheck(){
        if($("#name").val().trim().length<1){
            $("#namemsg").innerHTML = "이름은 필수 입력입니다.";
            flag = false
        }else{
            $("#namemsg").innerText = "";
            flag = true
        }
    }

    function agecheck(){
        if($("#age").val().trim().length<1){
            $("#agemsg").innerHTML = "생년월일은 필수 입력입니다.";
            flag = false
        }else{
            $("#agemsg").innerText = "";
            flag = true
        }
    }

    function numbercheck(){
        if($("#number").val().trim().length<1){
            $("#numbermsg").innerHTML = "전화번호는 필수 입력입니다.";
            flag = false
        }else{
            $("#numbermsg").innerText = "";
            flag = true
        }
    }

});