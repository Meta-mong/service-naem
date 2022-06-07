window.addEventListener("load", function(event){
    var phone_number_flag = false;

    $("#phone_number1").focusout(function() {
        if( $("#phone_number1").val().trim().length<1){
            $("#phone_number1_msg").innerHTML = '휴대전화번호를 입력해주세요.<br/>';
            phone_number_flag = false;
        }else{
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
                    if(map.result == "false"){

                    }
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