$(function () {



    mainpage_imageinit();
    extracted(1);
    moviechart(1);
    mainpage_button();



});

function movie_detail_on(){
    $('#main_information').css("display","none");
    $('#movie_information').css("display","block");
    $('#movie_comment_wrap').css("display","block");
    $('#input_comment').css("display","flex");
}

function movie_detail_off(){
    $('#main_information').css("display","flex");
    $('#movie_information').css("display","none");
    $('#movie_comment_wrap').css("display","none");
    $('#input_comment').css("display","none");
}

let nowpage_id ;

let nowpage_movieid;

let my_id , my_account;

let now_user_id;

let post_count = 0;






function new_users(){

    $('#new_user').show(100);

}

function cancel_users(){

    $('#new_user').hide(100);

}

function login(){

    $('#loginbox').show(100);

}

function cancel_login(){

    $('#loginbox').hide(100);

}


function currDate(date){

    var currDay = 24 * 60 * 60 * 1000;
    let date1 = new Date();
    let utcdate1 = Date.UTC(date1.getFullYear(), date1.getMonth(), date1.getDate());
    let date2 = new Date(Date.parse(date));
    let utcdate2 = Date.UTC(date2.getFullYear(), date2.getMonth(), date2.getDate());
    return Math.floor((utcdate1-utcdate2) / currDay);

}

let initialize = async () =>{

    try{
        let response_info = await $.ajax({
            url:'/movie',
            contentType :'application/json',
            type :'get'
        });

        let response_detail = await $.ajax({
            url:'/moviedetail',
            contentType :'application/json',
            type :'get'
        });

        let response_actor = await $.ajax({
            url:'/moviea',
            contentType :'application/json',
            type :'get'
        });

        let response_image = await $.ajax({
            url:'/moviei',
            contentType : 'application/json',
            type:'get'
        })


    }catch(e){
        console.log(JSON.stringify(e));
    }

};

let firstpostinit = async () => {

    try{
        postajax(nowpage_id);
    }
    catch(e){
        console.log(e)
    }

};

    async function extracted(id) {

        for(let i = 1 ; i< 11 ; i++){
            id = i;

            let response = {
                url:`http://localhost:8080/movies/${id}`,
                contentType :'application/json',
                type :'get'
            }

            let response1 = await $.ajax(response);

            $('#top10').append(

                `<a onclick="postajax(${response1.movieunique}),movie_detail_on()"><li>${response1.movie_name}</li></a>`

            );


        }


    }

    async function addcomment(){

        console.log(now_user_id);
            if(now_user_id === undefined){
                alert("로그인이 필요합니다.");
                login();
                return;
            }

            try{

                if($('#comment_input').val() === ""){
                    alert("댓글을 입력해주세요");
                    return;
                }

                let datas = {
                    userid  : now_user_id,
                    movieunique  : nowpage_movieid,
                    comment  : $('#comment_input').val()
                };

                let response ={
                    url : '/commentadd',
                    contentType : 'application/json',
                    type : 'post',
                    data:JSON.stringify(datas)
                };

                let response1 = await $.ajax(response);

                commentlist();

                $('#comment_input').val(" ");


            }
            catch(e){
                console.log(e);
            }

    }

    async function commentlist(){
        let response = {
            url : `/comment/${nowpage_movieid}`,
            type : 'get'
        }

        let response1 = await $.ajax(response);

        $('#movie_comment').html("");

        for(let i = 0 ; i<response1.length;i++){
            $("#movie_comment").append(
                `<div id="movie_comment_lst">
                <a id="movie_comment_userid">${response1[i].userid}</a>
    
                <li id="movie_comment_content">${response1[i].comment}</li>
            </div>`
            );
        }

    }



    async function fetchPostList(){ // show list
        try{

            let response = {
                url:'http://localhost:8080/post',
                contentType :'application/json',
                type :'get'
            };

            let response1 = await $.ajax(response);

            $('#post_list').html('');

            if(response1.data.length != 0){

                nowpage_id = response1.data[response1.data.length-1].id;
                firstpostinit(nowpage_id);


            }
            else{
                $('#title').html("아직 포스트가 없습니다.");
                $('#account').html("작성자");
                $('#time').html("시간");

                $('#desc').html("포스트를 입력해주세요.");
            }


            post_count = 0;

            for(let i =response1.data.length-1;i>=0;i--){
                let post = response1.data[i];
                //console.log(post);
                extracted(post);

                if(post.userAccount === my_account){
                    post_count++;
                }

            }
            $('#u_postnumber').html(post_count + "개");
        }
        catch (e) {
            $('#post_list').html(JSON.stringify(e));
        }
    }


    async function postajax(uid){

        let response = {
            url : `/movied/${uid}`,
            type : "get",
            contentType :'application/json'
        }

        let response_info = {
            url : `/movie/${uid}`,
            type : "get",
            contentType :'application/json'
        }

        let response1 = await $.ajax(response);

        let response2 = await $.ajax(response_info);

        console.log(response1);
        console.log(response2);

        nowpage_movieid = response1.movieunique;



        commentlist();



        movie_image();

        actors();



        $('#mv_info1').attr("style","visibility:visible");

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

async function movie_image() {

    let response = {

        url: `/moviei/${nowpage_movieid}`,
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

function goHome(){
        $('#mv_info1').attr("style","visibility:hidden");
    }

    async function moviechart(id){

        let responsecount = {
            url : `/movie/count`,
            type : "get",
            contentType :'application/json'
        }


            for(let i = (id*10)-9; i <(id*10)+1 ; i++){
                try{
                    let response = {
                        url : `/movies/${i}`,
                        type : "get",
                        contentType :'application/json'
                    }

                    let response1= await $.ajax(response);

                    let imageid = ((i+10)%10);

                    if(imageid % 10 === 0 ){
                        imageid = 10;
                    }

                    $(`#main_poster_box_${imageid} > img`).attr("src",`${response1.movie_imagehref}`);
                    $(`#main_poster_box_${imageid} > img`).attr("onclick",`postajax(${response1.movieunique}),movie_detail_on()`);
                    $(`#main_poster_box_${imageid} > a`).html(`${response1.movie_name}`);
                    $(`#main_poster_box_${imageid} > img`).css(`display`,`flex`);
                    $(`#main_poster_box_${imageid} > a`).css(`display`,`flex`);

                }
                catch(e){

                    let imageid = ((i+10)%10);

                    if(imageid % 10 === 0 ){
                        imageid = 10;
                    }

                    $(`#main_poster_box_${imageid} > img`).css(`display`,`none`);
                    $(`#main_poster_box_${imageid} > a`).css(`display`,`none`);


                }
            }





    }

    function mainpage_imageinit(){
        for(let i = 1 ; i<11 ; i++){
            $('#now_in_theaters').append(
                `<div id="main_poster_box_${i}" class="main_poster_box">

                    <img class="main_poster"/>

                    <a class="main_poster_name"></a>

                </div>`
            );
        }


    }

    async function mainpage_button(){
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

    function move_page(button){
        moviechart($(button).text());
    }

    async function signup(){
        let datas = {
            userid  : $('#n_user_id').val(),
            password  : $('#n_user_pw').val(),
            name  : $('#n_user_name').val()

        };

        let response = {

            url : '/signup',
            type : 'POST',
            contentType : 'application/json',
            data:JSON.stringify(datas)
        };

        let response1 = await $.ajax(response);

        alert(response1.name+"님 가입을 환영합니다.")

        cancel_users();


    }





    async function userlogin(){

        let input = {
            id : $('#login_id_txt').val(),
            pw : $('#login_pw_txt').val()
        }

        if($('#login_id_txt').val() === "" || $('#login_pw_txt').val()=== ""){
            alert("모두 입력해주세요");
            return;
        }

        try{
            let response = {
                url : `/login/${input.id}/${input.pw}`,
                type : 'get'
            }

            let response1 = await $.ajax(response);

            now_user_id = response1.userid;

            console.log(now_user_id);
            $('#sign_up_btn').html(now_user_id+"님");
            $('#login_btn').html("로그아웃");
            console.log($('#sign_up_btn'));
            alert(now_user_id + "님 환영합니다.")
            $('#login_id_txt').val("");
            $('#login_pw_txt').val("")
            usertext_check();
            cancel_login();
        }catch(e){
            console.log(e);

            alert("아이디 또는 비밀번호가 틀립니다.");
        }


    }

    function usertext_check(){
        if($('#sign_up_btn').innerHTML !=="회원가입"){
            $('#sign_up_btn').attr("onclick",null);
            $('#login_btn').attr("onclick","window.location.reload();");
        }
    }

    async function actors(){

        let response = {
            url : `/moviea/${nowpage_movieid}`,
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



    async function page_prev(id){

        let line = $(`#line${id}`);

        let prevlineid = line.parents("ul").next().children(1).children(1).attr("id");

        //console.log(id);

        if(line.parents("ul").next().text() === "") {
            alert("마지막 게시글입니다.");
            return;
        }else{
            prevlineid =  prevlineid.substring(4);
        }

        let response = await $.ajax({

            url : `/post/${prevlineid}`,
            type : 'GET',
            contentType :'application/json'

        });

        nowpage_id = prevlineid;


        $('#title').html(response.data.title);
        $('#account').html(response.data.userAccount);
        $('#time').html(response.data.updated);

        $('#desc').html(response.data.content);



    }

    async function page_next(id){

        let line = $(`#line${id}`);

        let nextlineid = line.parents("ul").prev().children(1).children(1).attr("id");

        //console.log(id);

        if(line.parents("ul").prev().text() === "") {
            alert("첫 게시글입니다.");
            return;
        }else{
            nextlineid =  nextlineid.substring(4);
        }
        
        let response = await $.ajax({

            url : `/post/${nextlineid}`,
            type : 'GET',
            contentType :'application/json'

        });

        nowpage_id = nextlineid;

        $('#title').html(response.data.title);
        $('#account').html(response.data.userAccount);
        $('#time').html(response.data.updated);

        $('#desc').html(response.data.content);


    }

    let title = null;
    let post =  null;

    async function post_edit(button){

        if($(button).text() === "수정"){
            title = $('#title').text();
            post = $('#desc').text();

           // console.log(title);

            $('#title').html(`<input id='edit_id' value='${title}'/>`);
            $("#desc").html(`<textarea style='resize: none;' id='edit_desc' cols='100' rows='40'>${post}</textarea>`)

            $(button).text("저장");
            $(button).next().text("취소");

        }
        else if($(button).text() === "저장"){

            let data = {
                title : $('#edit_id').val(),
                content : $('#edit_desc').val()
            }

            let response = await $.ajax({

                type : 'put',
                url : `/postu/${nowpage_id}`,
                contentType : 'application/json',
                data:JSON.stringify(data)

            });

            $('#title').html(response.data.title);
            $('#desc').html(response.data.content);

            fetchPostList();

            $(button).text("수정");
            $(button).next().text("삭제");


        }




    }




