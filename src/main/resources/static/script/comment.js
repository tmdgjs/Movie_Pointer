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
            url : '/comment/add',
            contentType : 'application/json',
            type : 'post',
            data:JSON.stringify(datas)
        };

        let response1 = await $.ajax(response);

        commentlist();

        $('#comment_input').val(" ");
        $('#comment_input').attr("placeholder","Type a message...");


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
