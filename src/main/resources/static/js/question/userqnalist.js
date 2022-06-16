window.addEventListener("load", function(){

//    $("select[name=dropdown]").change(function(){
//      console.log($(this).val());
//      console.log($("select[name=dropdown] option:selected").text());
//    });

    $("#dropdown").change(function(){
        alert($("#dropdown").val());
        location.href="/question/qlist/"+$("#dropdown").val();
    });

});


