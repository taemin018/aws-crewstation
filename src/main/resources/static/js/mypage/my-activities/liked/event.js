document.addEventListener("DOMContentLoaded", () => {
    const diaryFeed = document.querySelector(".diary-card-feed");
    const tagNames = document.querySelectorAll(".tag-name");
    const memberId = 1; // TODO: 로그인 사용자 ID
    const page = 1;
    const size = 10;

    // 좋아요 일기 목록 로드
    likeService.getLikedDiaries(memberId, (diaries) => {
        likeLayout.renderDiaryList(diaryFeed, diaries);
    }, page, size);

    // 좋아요 총 개수 갱신 → 두 번째 tag-name (좋아요)
    likeService.getLikedDiaryCount(memberId, (count) => {
        if (tagNames.length > 1) {
            tagNames[1].textContent = `좋아요(${count})`;
        }
    });

    // 이벤트 위임 - 좋아요 버튼 (클릭 시 카드 제거 = 좋아요 취소)
    diaryFeed.addEventListener("click", (e) => {
        const likeBtn = e.target.closest(".like-btn");
        if (likeBtn) {
            const diaryId = likeBtn.dataset.id;
            console.log(`좋아요 취소: 일기 ID ${diaryId}`);

            // 카드 제거
            const card = likeBtn.closest(".card-feed-item-wrap");
            if (card) card.remove();

            // 개수 업데이트
            likeService.getLikedDiaryCount(memberId, (count) => {
                if (tagNames.length > 1) {
                    tagNames[1].textContent = `좋아요(${count})`;
                }
            });
        }
    });

    // 이벤트 위임 - 댓글 버튼
    diaryFeed.addEventListener("click", (e) => {
        const commentBtn = e.target.closest(".comment-btn");
        if (commentBtn) {
            const diaryId = commentBtn.dataset.id;
            console.log(`댓글 버튼 클릭됨: ${diaryId}`);
            alert(`댓글 버튼 클릭됨: 일기 ID ${diaryId}`);
        }
    });
});
