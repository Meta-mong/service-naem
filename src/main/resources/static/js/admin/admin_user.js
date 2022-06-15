window.addEventListener("load", function (){
    $(".user-detail").on("click", function(){
        location.href = "/admin/userdetail/"+$(this.firstChild.nextSibling).text()+"?page="+$("#page").text();
    })
});
