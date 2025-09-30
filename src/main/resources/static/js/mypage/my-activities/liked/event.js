document.addEventListener("DOMContentLoaded", async () => {
    const diaryFeed = document.querySelector("#liked-diary-list");
    const tagNames = document.querySelectorAll(".tag-name");
    const profileWrap = document.querySelector(".profile-wrap");

    const memberId = 1; // 로그인 사용자 (임시)
    const PROFILE_EDIT_URL = "/mypage/mypage/modify";

    let page = 1;
    let checkScroll = true;
    let diariesCriteria = null;

    const navButtons = document.querySelectorAll(".nav-button");
    const currentPath = window.location.pathname;

    navButtons.forEach((btn) => {
        btn.classList.remove("active"); // 혹시 있을지 모르는 active 초기화
        if (btn.getAttribute("href") === currentPath) {
            btn.classList.add("active");
        }
    });

    if (!diaryFeed) {
        console.error("좋아요 일기 컨테이너(#liked-diary-list)를 찾을 수 없습니다.");
        return;
    }

    // 프로필 링크 고정
    if (profileWrap) {
        const a = profileWrap.querySelector("a");
        if (a) a.href = PROFILE_EDIT_URL;
    }

    // 프로필 불러오기
    try {
        const member = await memberService.getProfile(memberId);
        if (member && profileWrap) {
            const imgEl = profileWrap.querySelector(".profile-img");
            const nameEl = profileWrap.querySelector(".profile-name");

            if (imgEl) imgEl.src = member.socialImgUrl || "/images/crew-station-icon-profile.png";
            if (nameEl) nameEl.textContent = member.memberName || "";
        }
    } catch (e) {
        console.error("프로필 불러오기 실패:", e);
    }

    // 함수: 좋아요 일기 불러오기
    const showList = async (page = 1) => {
        const loading = document.getElementById("loading");
        if (loading) loading.style.display = "block";

        const result = await likeService.getLikedDiaries(memberId, page, 8);
        const diaries = result.likedDiaryDTOs || [];

        if (page === 1) {
            likeLayout.renderDiaryList(diaryFeed, diaries);
        } else {
            likeLayout.appendDiaryList(diaryFeed, diaries);
        }

        setTimeout(() => {
            if (loading) loading.style.display = "none";
        }, 1000);

        return result.criteria;
    };

    // 초기 로드
    diariesCriteria = await showList(page);

    // === 댓글 개수 갱신 ===
    const replyCount = await replyService.getReplyDiaryCount(memberId);
    if (tagNames.length > 0) {
        tagNames[0].textContent = `댓글(${replyCount})`;
    }

    // === 좋아요 개수 갱신 ===
    const likeCount = await likeService.getLikedDiaryCount(memberId);
    if (tagNames.length > 1) {
        tagNames[1].textContent = `좋아요(${likeCount})`;
    }

    // 스크롤 이벤트 (무한스크롤)
    window.addEventListener("scroll", async () => {
        const scrollTop = window.scrollY;
        const windowHeight = window.innerHeight;
        const documentHeight = document.documentElement.scrollHeight;

        if (scrollTop + windowHeight >= documentHeight - 2) {
            if (checkScroll && diariesCriteria?.hasMore) {
                diariesCriteria = await showList(++page);
                checkScroll = false;

                setTimeout(() => {
                    if (diariesCriteria?.hasMore) {
                        checkScroll = true;
                    }
                }, 1100);
            }
        }
    });

    // 이벤트 위임 - 좋아요 & 댓글 버튼
    diaryFeed.addEventListener("click", async (e) => {
        const likeBtn = e.target.closest(".like-btn");
        const commentBtn = e.target.closest(".comment-btn");

        if (likeBtn) {
            const diaryId = likeBtn.dataset.id;
            if (!confirm("좋아요를 취소하시겠습니까?")) return;

            console.log(`좋아요 취소: ID ${diaryId}`);

            // 서버에 좋아요 취소 요청
            const result = await likeService.cancelLike(memberId, diaryId);

            if (result.success) {
                const card = likeBtn.closest(".card-feed-item-wrap");
                if (card) card.remove();

                // 뒤에 남은 데이터 있으면 한 장 더 불러오기
                if (diariesCriteria?.hasMore) {
                    diariesCriteria = await showList(++page);
                }

                // 좋아요 개수 갱신
                const count = await likeService.getLikedDiaryCount(memberId);
                if (tagNames.length > 1) {
                    tagNames[1].textContent = `좋아요(${count})`;
                }

            } else {
                alert("좋아요 취소 실패: " + result.message);
            }
        }
    });
});
