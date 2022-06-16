window.addEventListener("load",function(){
    $("#update").on("click", function(){
        $.ajax({
            url:"/admin/noticeadd",
            type:"POST",
            data:{"title":$("#title").val(), "classify":$("#classify option:selected").val(), "content":$("#content").val()},
            dataType:"json",
            success: function(data){
                if(data.result = true){
                    alert("등록 성공");
                    location.replace("/admin/anlist");
                }else{
                    alert("등록 실패");
                    location.replace("/admin/noticeadd");
                }
            },
            error: function(){
                alert("에러")
            }
        });
    });
});