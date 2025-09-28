document.addEventListener("DOMContentLoaded", () => {
    const diaryFeed = document.querySelector("#liked-diary-list");
    const tagNames = document.querySelectorAll(".tag-name");
    const memberId = 1;

    let page = 1;
    let size = 12;
    let isLoading = false;
    let hasMore = true;

    if (!diaryFeed) {
        console.error("좋아요 일기 컨테이너(#liked-diary-list)를 찾을 수 없습니다.");
        return;
    }

    // ===== 초기 로드 =====
    loadDiaries();

    // ===== 좋아요 개수 갱신 =====
    likeService.getLikedDiaryCount(memberId, (count) => {
        if (tagNames.length > 1) {
            tagNames[1].textContent = `좋아요(${count})`;
        }
    });

    // ===== 함수: 일기 불러오기 =====
    async function loadDiaries() {
        if (isLoading || !hasMore) return;
        isLoading = true;

        const diaries = await likeService.getLikedDiaries(memberId, (data) => data, page, size);

        if (page === 1) {
            likeLayout.renderDiaryList(diaryFeed, diaries);
            size = 10; // 두 번째 페이지부터는 10개씩 로드
        } else {
            likeLayout.appendDiaryList(diaryFeed, diaries);
        }

        if (!diaries || diaries.length < size) {
            hasMore = false; // 데이터 없음
        } else {
            page++;
        }

        isLoading = false;
    }

    // ===== 스크롤 이벤트 (무한스크롤) =====
    window.addEventListener("scroll", () => {
        const nearBottom = window.scrollY + window.innerHeight >= document.body.offsetHeight - 200;
        if (nearBottom) {
            loadDiaries();
        }
    });

    // ===== 이벤트 위임 - 좋아요 & 댓글 =====
    diaryFeed.addEventListener("click", (e) => {
        const likeBtn = e.target.closest(".like-btn");
        const commentBtn = e.target.closest(".comment-btn");

        // 좋아요 버튼 클릭
        if (likeBtn) {
            const diaryId = likeBtn.dataset.id;

            if (!confirm("좋아요를 취소하시겠습니까?")) {
                return;
            }

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

        // 댓글 버튼 클릭
        if (commentBtn) {
            const diaryId = commentBtn.dataset.id;
            console.log(`댓글 버튼 클릭됨: ${diaryId}`);
            alert(`댓글 버튼 클릭됨: 일기 ID ${diaryId}`);
        }
    });
});
