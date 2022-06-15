window.addEventListener("load", function (){
   $("#ret_btn").on("click", function (){
      location.replace("/admin/allusers");
   });

   $("#modify_btn").on("click", function (){
      $.ajax({
         url : "/admin/modifyuser",
         type : "POST",
         data : {"email": $("#email").val(), "name": $("#name").val(), "loserCnt": $("#loserCnt").val(), "cancelCnt": $("#cancelCnt").val()},
         dataType : "json",
         success : function (data){
            if(data.result==true){
               alert("회원 정보가 수정되었습니다.");
            }else{
               alert("회원 정보 수정 실패");
            }
            location.replace("/admin/allusers");
         }, error : function (data){
            alert("회원 정보 변경 실패");
         }
      })
   })
});