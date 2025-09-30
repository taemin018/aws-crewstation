document.addEventListener("DOMContentLoaded", async () => {
    const profileWrap = document.querySelector(".profile-wrap");
    const tagNames = document.querySelectorAll(".tag-name");
    const memberId = 1; // 로그인 사용자 (임시)
    const PROFILE_EDIT_URL = "/mypage/mypage/modify";

    // 공통 상태값
    let replyPage = 1;
    let likePage = 1;
    let replyCriteria = null;
    let likeCriteria = null;
    let checkScroll = true;
    let activeTab = "reply"; // 기본값: 댓글 탭

    // ===================== 프로필 처리 =====================
    if (profileWrap) {
        const a = profileWrap.querySelector("a");
        if (a) a.href = PROFILE_EDIT_URL;

        try {
            const member = await memberService.getProfile(memberId);
            if (member) {
                const imgEl = profileWrap.querySelector(".profile-img");
                const nameEl = profileWrap.querySelector(".profile-name");
                if (imgEl) imgEl.src = member.socialImgUrl || "/images/crew-station-icon-profile.png";
                if (nameEl) nameEl.textContent = member.memberName || "";
            }
        } catch (e) {
            console.error("프로필 불러오기 실패:", e);
        }
    }

    // ===================== 댓글 불러오기 =====================
    async function loadReplyList(page = 1) {
        const replyFeed = document.querySelector("#reply-diary-list");
        if (!replyFeed) return;

        const loading = document.getElementById("loading");
        if (loading) loading.style.display = "block";

        const result = await replyService.getReplyDiaries(memberId, page, 8);
        const diaries = result.replyDiaryDTOs || [];

        if (page === 1) {
            replyLayout.renderReplyList(replyFeed, diaries);
        } else {
            replyLayout.appendReplyList(replyFeed, diaries);
        }

        // 댓글 개수 갱신
        const replyCount = await replyService.getReplyDiaryCount(memberId);
        if (tagNames.length > 0) tagNames[0].textContent = `댓글(${replyCount})`;

        setTimeout(() => { if (loading) loading.style.display = "none"; }, 1000);

        return result.criteria;
    }

    // ===================== 좋아요 불러오기 =====================
    async function loadLikedList(page = 1) {
        const likedFeed = document.querySelector("#liked-diary-list");
        if (!likedFeed) return;

        const loading = document.getElementById("loading");
        if (loading) loading.style.display = "block";

        const result = await likeService.getLikedDiaries(memberId, page, 8);
        const diaries = result.likedDiaryDTOs || [];

        if (page === 1) {
            likeLayout.renderDiaryList(likedFeed, diaries);
        } else {
            likeLayout.appendDiaryList(likedFeed, diaries);
        }

        // 좋아요 개수 갱신
        const likeCount = await likeService.getLikedDiaryCount(memberId);
        if (tagNames.length > 1) tagNames[1].textContent = `좋아요(${likeCount})`;

        setTimeout(() => { if (loading) loading.style.display = "none"; }, 1000);

        return result.criteria;
    }

    // ===================== 탭 전환 =====================
    document.querySelectorAll('.nav-button').forEach(btn => {
        btn.addEventListener('click', async function() {
            // 버튼 active 전환
            document.querySelectorAll('.nav-button').forEach(b => b.classList.remove('active'));
            this.classList.add('active');

            // 섹션 active 전환
            document.querySelectorAll('.tab-section').forEach(sec => sec.classList.remove('active'));
            const targetSection = document.getElementById(this.dataset.target);
            targetSection.classList.add('active');

            // 컨테이너 초기화 + 데이터 로드
            if (this.dataset.target === "reply-section") {
                document.querySelector("#liked-diary-list").innerHTML = "";
                activeTab = "reply";
                replyPage = 1;
                replyCriteria = await loadReplyList(replyPage);
            } else if (this.dataset.target === "liked-section") {
                document.querySelector("#reply-diary-list").innerHTML = "";
                activeTab = "liked";
                likePage = 1;
                likeCriteria = await loadLikedList(likePage);
            }
        });
    });

    // ===================== 무한 스크롤 =====================
    window.addEventListener("scroll", async () => {
        const scrollTop = window.scrollY;
        const windowHeight = window.innerHeight;
        const documentHeight = document.documentElement.scrollHeight;

        if (scrollTop + windowHeight >= documentHeight - 2) {
            if (!checkScroll) return;

            if (activeTab === "reply" && replyCriteria?.hasMore) {
                replyCriteria = await loadReplyList(++replyPage);
            } else if (activeTab === "liked" && likeCriteria?.hasMore) {
                likeCriteria = await loadLikedList(++likePage);
            }

            checkScroll = false;
            setTimeout(() => { checkScroll = true; }, 1100);
        }
    });

    // ===================== 좋아요 취소 이벤트 =====================
    document.querySelector("#liked-diary-list")?.addEventListener("click", async (e) => {
        const likeBtn = e.target.closest(".like-btn");
        if (likeBtn) {
            const diaryId = likeBtn.dataset.id;
            if (!confirm("좋아요를 취소하시겠습니까?")) return;

            const result = await likeService.cancelLike(memberId, diaryId);
            if (result.success) {
                const card = likeBtn.closest(".card-feed-item-wrap");
                if (card) card.remove();

                // 뒤에 남은 데이터 있으면 한 장 더 불러오기
                if (likeCriteria?.hasMore) {
                    likeCriteria = await loadLikedList(++likePage);
                }

                // 좋아요 개수 갱신
                const count = await likeService.getLikedDiaryCount(memberId);
                if (tagNames.length > 1) tagNames[1].textContent = `좋아요(${count})`;
            } else {
                alert("좋아요 취소 실패: " + result.message);
            }
        }
    });

    // ===================== 초기 로딩 =====================
    replyCriteria = await loadReplyList(replyPage);
    const likeCount = await likeService.getLikedDiaryCount(memberId);
    if (tagNames.length > 1) tagNames[1].textContent = `좋아요(${likeCount})`;
});
