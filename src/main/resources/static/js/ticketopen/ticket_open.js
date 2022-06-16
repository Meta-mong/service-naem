window.addEventListener("load", function (){
    $("#genre").change(function (){
       location.href="/ticketopen/"+$("#genre").val();
    });
});