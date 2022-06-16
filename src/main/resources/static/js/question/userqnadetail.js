window.addEventListener("load", function(){
    $("#btn-save").on("click", function(){
       location.href = "/question/userqnaupdate/"+$("#ques_id").text();
    });


 }};
