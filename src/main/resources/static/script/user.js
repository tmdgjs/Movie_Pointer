
async function signup(){ //회원가입
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


async function userlogin(){ //로그인

    let input = {
        id : $('#login_id_txt').val(),
        pw : $('#login_pw_txt').val()
    };

    if($('#login_id_txt').val() === "" || $('#login_pw_txt').val()=== ""){
        alert("모두 입력해주세요");
        return;
    }

    try{
        let response = {
            url : `/login/${input.id}/${input.pw}`,
            type : 'get'
        };

        let response1 = await $.ajax(response);

        now_user_id = response1.userid;


        $('#sign_up_btn').html(now_user_id+"님");
        $('#login_btn').html("로그아웃");

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

function usertext_check(){ //로그인 후 기존 텍스트 변경
    if($('#sign_up_btn').innerHTML !=="회원가입"){
        $('#sign_up_btn').attr("onclick",null);
        $('#login_btn').attr("onclick","window.location.reload();");
    }
}