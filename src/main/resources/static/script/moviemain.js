

async function movietop10() { //movietop10

    let fidresponse = {
        url :'/movie/fid',
        contentType : 'application/json',
        type:'get'
    };

    let fidresponse1 = await $.ajax(fidresponse);

    for(let i = fidresponse1.id ; i< (fidresponse1.id)+10 ; i++){
        let id = i;


        let response = {
            url:`http://localhost:8080/movie/${id}`,
            contentType :'application/json',
            type :'get'
        };

        let response1 = await $.ajax(response);

        $('#top10').append(

            `<a onclick="postajax(${response1.movieunique}),movie_detail_on()"><li>${response1.movie_name}</li></a>`

        );
    }
}

async function moviechart(id){ //무비차트

    let fidresponse = {
        url :'/movie/fid',
        contentType : 'application/json',
        type:'get'
    };

    let countresponse = {
        url : `/movie/count`,
        type : "get",
        contentType :'application/json'
    };

    let fidresponse1 = await $.ajax(fidresponse);

    let countresponse1 = await $.ajax(countresponse);

    let movielist = new Array();
    let cnt = 0;
    let movieId = fidresponse1.id;
    let pageId = id;
    let startMovieId = movieId+(10*(pageId-1));
    for(let i = startMovieId; i < startMovieId + 10 ; i++){
        let response = {
            url : `/movie/${i}`,
            type : "get",
            contentType :'application/json'
        };
        let response1= await $.ajax(response);
        movielist[cnt] = response1;

        cnt++;
    }
    for(let i=0; i<10; i++){
        if(movielist[i]) {
            $(`#main_poster_box_${i} > img`).attr("src",`${movielist[i].movie_imagehref}`);
            $(`#main_poster_box_${i} > img`).attr("onclick",`postajax(${movielist[i].movieunique}),movie_detail_on()`);
            $(`#main_poster_box_${i} > a`).html(`${movielist[i].movie_name}`);
            $(`#main_poster_box_${i} > img`).css(`display`,`flex`);
            $(`#main_poster_box_${i} > a`).css(`display`,`flex`);
            $(`#main_poster_box_${i} > a`).attr("title",`${movielist[i].movie_name}`);
        }else {
            $(`#main_poster_box_${i} > img`).css(`display`,`none`);
            $(`#main_poster_box_${i} > a`).css(`display`,`none`);
        }
    }
}

function mainpage_imageinit(){ //default 이미지 세팅
    for(let i = 0 ; i<10 ; i++){
        $('#now_in_theaters').append(
            `<div id="main_poster_box_${i}" class="main_poster_box">

                    <img class="main_poster"/>

                    <a class="main_poster_name"></a>

                </div>`
        );
    }

}

async function mainpage_button(){ //페이지 버튼
    let response = {
        url : `/movie/count`,
        type : "get",
        contentType :'application/json'
    }

    let response1 = await $.ajax(response);

    let btnmaxnum = (response1 / 10) + 1;

    if(response1 % 10 === 0){
        btnmaxnum = btnmaxnum - 1;
    }

    for(let i = 1 ; i <= btnmaxnum ; i++){
        $('#main_poster_page').append(

            `<button class="main_poster_page_btn" onclick="move_page(this)">${i}</button>`


        );
    };
}

function move_page(button){ //버튼 이동
    moviechart($(button).text());
}


