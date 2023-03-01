// (1) 회원정보 수정
function update(userId) {
    let data = $("#profileUpdate").serialize();

    $.ajax({
        type: "put",
        url: `/api/user/${userId}`,
        data: data,
        contentType: "application/x-www-form-urlencoded; charset=utf-8", // key=value 형태
        dataType: "json"
    }).done(response => {
        console.log("수정 성공 : " + response);
    }).fail(error => {
        console.log("수정 실패 : " + error);
    });
}