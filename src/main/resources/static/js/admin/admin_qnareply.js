window.addEventListener("load", function(){
  var id = $("#ques_id").text();
  $("#update_btn").on("click", function(){
    $.ajax({
        url : "/admin/qnareply/"+id,
        type : "POST",
        data : {"admincontent" : $("#admincontent").val()},
        dataType : "json",
        success : function (data){
           if(data.result==true){
                alert("답글 등록 완료");
                location.replace("/admin/aqlist");
           }else{
                alert("답글 등록 실패")
           }
        },
        error : function (data){
           alert("답글 등록 실패. 다시 시도해주세요.");
        }
    });
  });

});