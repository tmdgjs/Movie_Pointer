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