async function postajax(uid){ //movie detail + movie info

    let response = {
        url : `/movie/deatilsearch/${uid}`,
        type : "get",
        contentType :'application/json'
    };

    let response_info = {
        url : `/movie/infosearch/${uid}`,
        type : "get",
        contentType :'application/json'
    };

    let response1 = await $.ajax(response);

    let response2 = await $.ajax(response_info);

    nowpage_movieid = response1.movieunique;

    commentlist();

    movie_image();

    movie_actors();


    $('#rank').html(response1.rank);
    $('#genre').html(response1.genre);
    $('#country').html(response1.country);
    $('#movie_startday').html(response2.movie_startday);
    $('#running_time').html(response1.running_time);
    $('#old').html(response1.old);
    $('#audience').html(response1.audience);
    $('#movie_title').html(response2.movie_name);
    $('#movie_poster').attr("src",response2.movie_imagehref);
    $('#sub_info').html(response2.movie_info);
    $('#big_img').attr("href",response2.movie_imagehref);
    $('#ticketbtn').attr("onclick","window.open('https://movie.daum.net//reservation?movieId="+uid+"')");

}

async function movie_image() { //movie image

    let response = {

        url: `/movie/imagesearch/${nowpage_movieid}`,
        type: 'GET',
        contentType: 'application/json'
    };

    let response1 = await $.ajax(response);


    $('#image1').html("");
    $('#image2').html("");


    let keys = Object.keys(response1);
    for (let i = 2; i < keys.length; i++) {

        if (response1[keys[i]] === null) {

            break;

        } else {

            if(i%2===0){
                $('#image1').append(
                    `<div id="movieimage">
                        <img id="movieimage_content" target="_blank" onclick="window.open('${response1[keys[i]]}')" src="${response1[keys[i]]}" />
                     </div>`
                );
            }else if(i%2===1){
                $('#image2').append(
                    `<div id="movieimage">
                        <img id="movieimage_content" target="_blank" onclick="window.open('${response1[keys[i]]}')" src="${response1[keys[i]]}" />
                     </div>`
                );
            }


        }
    }

}


async function movie_actors(){ // movie_actor

    let response = {
        url : `/movie/actorsearch/${nowpage_movieid}`,
        type : 'GET',
        contentType : 'application/json'
    };

    let response1 = await $.ajax(response);

    $('#actor1').html("");
    $('#actor2').html("");

    for(let i = 0 ; i<=response1.length;i++){
        if(i%2 === 0){
            $('#actor1').append(
                `<div id="person">
                        <img id="person_img" src='${response1[i].actor_img}'/>
                        <a id="person_name">${response1[i].actor_name}</a>
                        <a id="person_job">${response1[i].actor_role}</a> 
                    </div>`
            );
        }else if(i%2 === 1){
            $('#actor2').append(
                `<div id="person">
                    <img id="person_img" src="${response1[i].actor_img}"/>
                    <a id="person_name">${response1[i].actor_name}</a>
                    <a id="person_job">${response1[i].actor_role}</a></div>`
            );
        }
    }
}


