// (1) 회원정보 수정
function update(userId, event) {
    event.preventDefault(); // form태그의 action경로를 비활성화 시킨다. (form 태그 안에 버튼이 있기 때문에 필요한 옵션이다.)
    let data = $("#profileUpdate").serialize();

    $.ajax({
        type: "put",
        url: `/api/user/${userId}`,
        data: data,
        contentType: "application/x-www-form-urlencoded; charset=utf-8", // key=value 형태
        dataType: "json"
    }).done(response => {
        alert("회원정보가 정상적으로 수정되었습니다.");
        location.href = `/user/${userId}`;
    }).fail(error => {
        if (error.data === null) {
            alert(error.responseJSON.message);
        } else {
            alert("회원정보 수정에 실패하였습니다. 원인 : " + JSON.stringify((error.responseJSON.data)));
        }
    });
}