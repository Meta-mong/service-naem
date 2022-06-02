window.addEventListener("load", function(event){
    var email = document.getElementById("email");
    var passwd = $("#passwd").val();
    var passwdck = $("#passwdck").val();
    var number = $("#number").val();

    var flag = false;
    var authnumber = "";
    var smsauth = false;


    email.addEventListener("focusout", function(event) {
        if (email.value.trim().length < 1) {
            $("#emailmsg").innerHTML = 'email은 필수 입력입니다.<br/>';
        } else {
            var emailRegExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
            if (!emailRegExp.test(email.value.trim())) {
                $("#emailmsg").innerHTML = '잘못된 이메일 형식입니다.<br/>';
            }else{
                $("#emailmsg").innerText = "";
            }
        }
    });

    $("#passwd").focusout = () => {
        if (passwd.value.trim().length < 1) {
            $("#passwdmsg").innerHTML = '비밀번호를 입력해주세요.<br/>';
        }else{
            var passwdRegExp = "^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$";
            if(!passwdRegExp.test(passwd.value.trim())){
                $("#passwdmsg").innerHTML = '영문,숫자,특수문자가 각각 하나 이상 들어가는 8~12자 사이의 비밀번호를 입력해주세요.<br/>';
            } else {
                $("#passwdmsg").innerText = "";
            }
        }
    }

    $("#passwdck").focusout = () => {
        if (passwd.value.trim().length < 1) {
            $("#passwdckmsg").innerHTML = "비밀번호를 확인해주세요.<br/>";
        }else{
            if(passwdck===passwd){
                $("#passwdckmsg").innerText = "";
            }else{
                $("#passwdckmsg").innerHTML = "비밀번호가 같지 않습니다. 다시 확인해주세요.";
            }
        }
    }

    $("#name").focusout = () => {
        if($("#name").val().trim().length<1){
            $("#namemsg").innerHTML = "이름은 필수 입력입니다.";
        }else{
            $("#namemsg").innerText = "";
        }
    }

    $("#age").focusout = () => {
        if($("#age").val().trim().length<1){
            $("#agemsg").innerHTML = "생년월일은 필수 입력입니다.";
        }else{
            $("#agemsg").innerText = "";
        }
    }

    $("#number").focusout = () => {
        if($("#number").val().trim().length<1){
            $("#numbermsg").innerHTML = "전화번호는 필수 입력입니다.";
        }else{
            $("#numbermsg").innerText = "";
        }
    }

    /*
    function emailcheck(){
        if (email.trim().length < 1) {
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
        if (passwd.trim().length < 1) {
            $("#passwdmsg").innerHTML = '비밀번호를 입력해주세요.<br/>';
            flag = false
        }else{
            var passwdRegExp = "^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$";
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
        if (passwdck.trim().length < 1) {
            $("#passwdckmsg").innerHTML = "비밀번호를 확인해주세요.<br/>";
            flag = false
        }else{
            if(passwdck===passwd){
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

    $("#email").focusout = emailcheck();
    $("#passwd").focusout = passwdcheck();
    $("#passwdck").focusout = passwddoublecheck();
    $("#name").focusout = namecheck();
    $("#age").focusout = agecheck();
    $("#number").focusout = numbercheck();

    $("#emailck").click = () => {
        emailcheck();
        if(flag==false) return;

        $.ajax({
            type: "POST",
            url: "/sign/emailcheck",
            //contentType: "application/json; charset=utf-8",
            data: {"email":email},
            dataType: "json",
            success: function (data, statusText) {
                var temp = JSON.stringify(data);
                var map = JSON.parse(temp);
                alert(map.result);
                if(map.result == true) { //중복
                    flag = false;
                    $("#emailmsg").innerHTML = '이미 사용 중인 이메일입니다.<br/>';
                }else{
                    flag = true;
                    $("#emailmsg").innerHTML = '사용 가능한 이메일입니다.<br/>';
                }
            },
            error: function (data, statusText) {
                alert("에러 발생");
                var temp = JSON.stringify(data);
                var map = JSON.parse(temp);
            }
        })
    }

    $("#numberck").click = () => {
        numbercheck();
        if(flag==false) return;

        $.ajax({
            type: "POST",
            url: "/sign/numbercheck",
            //contentType: "application/json; charset=utf-8",
            data: {"number":number},
            dataType: "json",
            success: function (data, statusText) {
                var temp = JSON.stringify(data);
                var map = JSON.parse(temp);
                alert(map.result);
                if(map.result == true) { //중복
                    flag = false;
                    $("#numbermsg").innerHTML = map.message + '<br/>';
                }else{
                    flag = true;
                    $("#numbermsg").innerText = map.message + '<br/>';
                    authnumber = map.random;
                }
            },
            error: function (data, statusText) {
                alert("에러 발생");
                var temp = JSON.stringify(data);
                var map = JSON.parse(temp);
            }
        });
    }

    $("#smsck").click = () => {
        if($("#authnumber").val()==authnumber){
            smsauth = true;
            $("#numbermsg").innerHTML = "인증되었습니다.</br>";
        }else{
            smsauth = false;
            $("#numbermsg").innerHTML = "인증에 실패하였습니다.</br>";
        }
    }

    $("#signupbtn").click = (event) => {
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
        }
    }
*/
});