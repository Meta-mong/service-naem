window.addEventListener("load", function(){
    $("#dropdown").change(function(){
        location.href="/admin/aqlist/"+$("#dropdown").val();
    });

});


