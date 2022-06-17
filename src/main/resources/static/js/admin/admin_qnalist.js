window.addEventListener("load", function(){
<<<<<<< HEAD
    $("#dropdown").on("change", function(){
        location.href="/admin/aqlist/"+$("#dropdown").val();
    });

    classifyCheck($("#selectedclassify").text());
});

function classifyCheck(data){
    if(data != ""){
        $("#dropdown").val(data).prop("selected", true);
    }
}

=======
    $("#dropdown").change(function(){
        location.href="/admin/aqlist/"+$("#dropdown").val();
    });

});

>>>>>>> develop

