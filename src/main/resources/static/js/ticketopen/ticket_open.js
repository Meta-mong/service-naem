window.addEventListener("load", function (){
   $("#search_btn").on("click", function (){
      if($("#genre").val()=="TOTAL" && $("#title").val().trim().length<=0) return;

      $.ajax({
         url : "/ticketopen/searchoption",
         type : "POST",
         data : {"genre": $("#genre").val(), "title": $("#title").val()},
         dataType : "json",
         success : function (data){
            if(data.result==true) alert("성공");
         }, error : function (data){
            alert("실패");
         }
      });
   });
});