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

function pick(){
    for(let i = 0; i < 4; i++){
        if(i === 0){
         var genre = 'CONCERT';
        }else if(i === 1){
            genre = 'MUSICAL_DRAMA';
        }else if(i === 2){
            genre = 'CLASSIC';
        }else{
            genre = 'EXHIBITION';
        }

        let k = i;

        $.ajax({
            url:"/concert/pick/"+genre,
            type : "get",
            dataType: 'json',
            data:'',
            success: function(data){
            console.log(data);

            for(j=0; j<data.content.length; j++){
             $('.card'+k).empty();
             $('.card'+k).append("<img src='/concert/readImg/"+data.content[j].phamplet+"' alt=''><span>"+data.content[j].title+"</span> <span>"
                                        +data.content[j].concertDate+"</span> <span>"+data.content[j].address+"</span>");
                k = k+4;
            }


            },
            error: function(data){
            alert("실패");
            }
        });
    }

}