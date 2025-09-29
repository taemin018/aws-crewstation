document.addEventListener("DOMContentLoaded", async () => {
    const diaryFeed = document.querySelector("#liked-diary-list");
    const tagNames = document.querySelectorAll(".tag-name");
    const profileWrap = document.querySelector(".profile-wrap");

    const memberId = 1; // 로그인 사용자 (임시)
    const PROFILE_EDIT_URL = "/mypage/mypage/modify";

    let page = 1;
    let checkScroll = true; // ✅ 스크롤 가능 여부 플래그
    let diariesCriteria = null;

    if (!diaryFeed) {
        console.error("좋아요 일기 컨테이너(#liked-diary-list)를 찾을 수 없습니다.");
        return;
    }

    // ===== 프로필 링크 고정 =====
    if (profileWrap) {
        const a = profileWrap.querySelector("a");
        if (a) a.href = PROFILE_EDIT_URL;
    }

    // ===== 프로필 불러오기 =====
    try {
        const member = await memberService.getProfile(memberId);
        if (member && profileWrap) {
            const imgEl = profileWrap.querySelector(".profile-img");
            const nameEl = profileWrap.querySelector(".profile-name");

            if (imgEl) {
                imgEl.src = member.profileImage || "/images/crew-station-icon-profile.png";
            }
            if (nameEl) {
                nameEl.textContent = member.memberName || "";
            }
        }
    } catch (e) {
        console.error("프로필 불러오기 실패:", e);
    }

    // ===== 함수: 일기 불러오기 =====
    const showList = async (page = 1) => {
        const loading = document.getElementById("loading");
        if (loading) loading.style.display = "block";

        const diaries = await likeService.getLikedDiaries(memberId, (data) => data, page, 8);

        if (page === 1) {
            likeLayout.renderDiaryList(diaryFeed, diaries);
        } else {
            likeLayout.appendDiaryList(diaryFeed, diaries);
        }

        setTimeout(() => {
            if (loading) loading.style.display = "none";
        }, 1000);

        return {
            list: diaries,
            criteria: {
                hasMore: diaries && diaries.length > 0
            }
        };
    };

    // ===== 초기 로드 =====
    diariesCriteria = await showList(page);

    // ===== 좋아요 개수 갱신 =====
    likeService.getLikedDiaryCount(memberId, (count) => {
        const safe = Number.isFinite(count) ? count : 0;
        if (tagNames.length > 1) {
            tagNames[1].textContent = `좋아요(${safe})`;
        }
    });

    // ===== 스크롤 이벤트 (무한스크롤) =====
    window.addEventListener("scroll", async () => {
        const scrollTop = window.scrollY;
        const windowHeight = window.innerHeight;
        const documentHeight = document.documentElement.scrollHeight;

        if (scrollTop + windowHeight >= documentHeight - 2) {
            if (checkScroll) {
                diariesCriteria = await showList(++page);
                checkScroll = false;
            }

            setTimeout(() => {
                if (diariesCriteria !== null && diariesCriteria.criteria.hasMore) {
                    checkScroll = true;
                }
            }, 1100);
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
                const safe = Number.isFinite(count) ? count : 0;
                if (tagNames.length > 1) {
                    tagNames[1].textContent = `좋아요(${safe})`;
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
