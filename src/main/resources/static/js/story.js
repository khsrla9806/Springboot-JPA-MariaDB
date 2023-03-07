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

                    <button>
                        <i class="fas fa-heart active" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>
                    </button>
                </div>

                <span class="like"><b id="storyLikeCount-${image.id}">3 </b>likes</span>

                <div class="sl__item__contents__content">
                    <p>${image.caption}</p>
                </div>

                <div id="storyCommentList-1">

                    <div class="sl__item__contents__comment" id="storyCommentItem-1"">
                        <p>
                            <b>Lovely :</b> 부럽습니다.
                        </p>

                        <button>
                            <i class="fas fa-times"></i>
                        </button>

                    </div>

                </div>

                <div class="sl__item__input">
                    <input type="text" placeholder="댓글 달기..." id="storyCommentInput-1" />
                    <button type="button" onClick="addComment()">게시</button>
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
	if (likeIcon.hasClass("far")) {
		likeIcon.addClass("fas");
		likeIcon.addClass("active");
		likeIcon.removeClass("far");
	} else {
		likeIcon.removeClass("fas");
		likeIcon.removeClass("active");
		likeIcon.addClass("far");
	}
}

// (4) 댓글쓰기
function addComment() {

	let commentInput = $("#storyCommentInput-1");
	let commentList = $("#storyCommentList-1");

	let data = {
		content: commentInput.val()
	}

	if (data.content === "") {
		alert("댓글을 작성해주세요!");
		return;
	}

	let content = `
			  <div class="sl__item__contents__comment" id="storyCommentItem-2""> 
			    <p>
			      <b>GilDong :</b>
			      댓글 샘플입니다.
			    </p>
			    <button><i class="fas fa-times"></i></button>
			  </div>
	`;
	commentList.prepend(content);
	commentInput.val("");
}

// (5) 댓글 삭제
function deleteComment() {

}







