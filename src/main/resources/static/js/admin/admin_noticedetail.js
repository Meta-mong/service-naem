window.addEventListener("load", function(){
    $("#btn-save").on("click", function(){
       location.href = "/question/userqnaupdate/"+$("#ques_id").text();
    });


//QnA 상세 정보 삭제
//    $("#userqnadetaildelete-btn").on("click", function(){
//
//         $.ajax({
//            type: 'GET',
//            url: "/question/userqnadelete/"+$("#ques_id").text(),
//            success : function (data){
//               if(data.result==true){
//                    alert("QnA 삭제 완료");
//                    location.replace("/question/qlist");
//               }else{
//                    alert("QnA 삭제 실패")
//               }
//            },
//            error : function (data){
//               alert("QnA 수정 실패. 다시 시도해주세요.");
//            }
//       })
//    });
 }};
