function ranking(value){
    var genre = value;

    $.ajax({
        url:"/ranking/main/"+genre,
        type : "get",
        dataType: 'json',
        data:'',
        success: function(data){
        console.log(data);
        $('.ranking-div-main').empty();

        $('.ranking-div-main').append(" <div class='ranking-div-main-plus'><a href='#'><i class='fa-solid fa-plus'></i></a></div>")
        $('.ranking-div-main').append(" <div><div class='ranking1'><svg height='300' width='400'> <polygon points='0,0 40,0 40,40 20,30 0,40' style='fill:rgb(255, 98, 67);stroke:rgb(255, 98, 67);stroke-width:1' />Sorry, your browser does not support inline SVG.<text x='15' y='22' font-family='Verdana' font-size='20' fill='white'>1</text></svg></div><img src='/concert/readImg/"
                                            +data.content[0].phamplet+"' alt=''> <span>"+data.content[0].title+"</span></div>")

        for(i=1; i<data.content.length; i++){
            $('.ranking-div-main').append(" <div><div class='ranking1'><svg height='300' width='400'> <polygon points='0,0 40,0 40,40 20,30 0,40' style='fill:rgb(137, 137, 137);stroke:rgb(137, 137, 137);stroke-width:1' />Sorry, your browser does not support inline SVG.<text x='15' y='22' font-family='Verdana' font-size='20' fill='white'>"
                                            +(i+1)+"</text></svg></div><img src='/concert/readImg/"+data.content[i].phamplet+"' alt=''> <span>"+data.content[i].title+"</span></div>")
        }

        },
        error: function(data){
        alert("실패");
        }
    });
}