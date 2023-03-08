/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 좋아요, 안좋아요
	(4) 댓글쓰기
	(5) 댓글삭제
 */

// 스토리 페이징을 위한 변수
let page = 0;

// (1) 스토리 로드하기
function storyLoad() {
    $.ajax({
        url: `/api/image?page=${page}`,
        dataType: "json"
    }).done(response => {
        response.data.forEach((image) => {
            let storyItem = getStoryItem(image);
            $("#storyList").append(storyItem);
        });
    }).fail(error => {
        console.log(error);
    });
}

storyLoad();

function getStoryItem(image) {
    let item = `
        <div class="story-list__item">
            <div class="sl__item__header">
                <div>
                    <img class="profile-image" src="/upload/${image.user.profileImageUrl}"
                        onerror="this.src='/images/person.jpeg'" />
                </div>
                <div>${image.user.username}</div>
            </div>

            <div class="sl__item__img">
                <img src="/upload/${image.photoImageUrl}" />
            </div>

            <div class="sl__item__contents">
                <div class="sl__item__contents__icon">

                    <button>`;

						if (image.likeState) {
							item += `<i class="fas fa-heart active" id="storyLikeIcon-${image.id}" onClick="toggleLike(${image.id})"></i>`;
						} else {
							item += `<i class="far fa-heart" id="storyLikeIcon-${image.id}" onClick="toggleLike(${image.id})"></i>`;
						}

			item += `</button>
                </div>

                <span class="like"><b id="storyLikeCount-${image.id}">${image.likeCount} </b>likes</span>

                <div class="sl__item__contents__content">
                    <p>${image.caption}</p>
                </div>

                <div id="storyCommentList-${image.id}">

                    <div class="sl__item__contents__comment" id="storyCommentItem-${image.id}"">
                        <p>
                            <b>Lovely :</b> 부럽습니다.
                        </p>

                        <button>
                            <i class="fas fa-times"></i>
                        </button>

                    </div>

                </div>

                <div class="sl__item__input">
                    <input type="text" placeholder="댓글 달기..." id="storyCommentInput-${image.id}" />
                    <button type="button" onClick="addComment(${image.id})">게시</button>
                </div>

            </div>
        </div>`;

    return item;
}

// (2) 스토리 스크롤 페이징하기 => 아래 코드는 window에서 scroll이 실행되면 작동된다.
$(window).scroll(() => {
    let scrollNum = $(window).scrollTop() - ($(document).height() - $(window).height());
    if (scrollNum > -1 && scrollNum < 1) {
        page++; // 한번 작동 시에 페이지를 증가시켜줌
        storyLoad(); // 다음 페이지를 가져옴
    }
});


// (3) 좋아요, 안좋아요
function toggleLike(imageId) {
	let likeIcon = $(`#storyLikeIcon-${imageId}`);

	if (likeIcon.hasClass("far")) { // 좋아요를 하지 않은 상태
		$.ajax({
			type: "post",
			url: `/api/image/${imageId}/likes`,
			dataType: "json"
		}).done(response => {
			let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
			let likeCount = Number(likeCountStr) + 1;
			$(`#storyLikeCount-${imageId}`).text(likeCount);

			likeIcon.addClass("fas");
			likeIcon.addClass("active");
			likeIcon.removeClass("far");
		}).fail(error => {
			console.log("오류", error);
		});
	} else { // 좋아요를 한 상태
		$.ajax({
			type: "delete",
			url: `/api/image/${imageId}/likes`,
			dataType: "json"
		}).done(response => {
			let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
			let likeCount = Number(likeCountStr) - 1;
			$(`#storyLikeCount-${imageId}`).text(likeCount);

			likeIcon.removeClass("fas");
			likeIcon.removeClass("active");
			likeIcon.addClass("far");
		}).fail(error => {
			console.log("오류", error);
		});
	}
}

// (4) 댓글쓰기
function addComment(imageId) {

	let commentInput = $(`#storyCommentInput-${imageId}`);
	let commentList = $(`#storyCommentList-${imageId}`);

	let data = {
		content: commentInput.val(),
		imageId: imageId
	}

	if (data.content === "") {
		alert("댓글을 작성해주세요!");
		return;
	}

	$.ajax({
		type: "post",
		url: "/api/comment",
		data: JSON.stringify(data),
		contentType: "application/json; charset=utf-8",
		dataType: "json"
	}).done(response => {
		console.log("댓글쓰기 성공", response);
	}).fail(error => {
		console.log("댓글쓰기 실패", error);
	});
}

// (5) 댓글 삭제
function deleteComment() {

}







