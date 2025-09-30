document.addEventListener("DOMContentLoaded", async () => {
    const replyFeed = document.querySelector("#reply-diary-list");
    const tagNames = document.querySelectorAll(".tag-name");
    const profileWrap = document.querySelector(".profile-wrap");

    const memberId = 1; // 로그인 사용자 (임시)
    const PROFILE_EDIT_URL = "/mypage/mypage/modify";

    let page = 1;
    let checkScroll = true;
    let replyCriteria = null;

    const navButtons = document.querySelectorAll(".nav-button");
    const currentPath = window.location.pathname;

    navButtons.forEach((btn) => {
        btn.classList.remove("active"); // 혹시 있을지 모르는 active 초기화
        if (btn.getAttribute("href") === currentPath) {
            btn.classList.add("active");
        }
    });

    if (!replyFeed) {
        console.error("댓글 단 다이어리 컨테이너(#reply-diary-list)를 찾을 수 없습니다.");
        return;
    }

    // 프로필 링크
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

    // 댓글 다이어리 불러오기
    const showList = async (page = 1) => {
        const loading = document.getElementById("loading");
        if (loading) loading.style.display = "block";

        const result = await replyService.getReplyDiaries(memberId, page, 8);
        const diaries = result.replyDiaryDTOs || [];

        if (page === 1) {
            replyLayout.renderReplyList(replyFeed, diaries);
        } else {
            replyLayout.appendReplyList(replyFeed, diaries);
        }

        setTimeout(() => { if (loading) loading.style.display = "none"; }, 1000);

        return result.criteria;
    };

    // 초기 로드
    replyCriteria = await showList(page);

    // 댓글 개수 갱신
    const replyCount = await replyService.getReplyDiaryCount(memberId);
    if (tagNames.length > 0) tagNames[0].textContent = `댓글(${replyCount})`;

    // 좋아요 개수 갱신 (목록 X, 개수만)
    const likeCount = await likeService.getLikedDiaryCount(memberId);
    if (tagNames.length > 1) tagNames[1].textContent = `좋아요(${likeCount})`;

    // 무한스크롤
    window.addEventListener("scroll", async () => {
        const scrollTop = window.scrollY;
        const windowHeight = window.innerHeight;
        const documentHeight = document.documentElement.scrollHeight;

        if (scrollTop + windowHeight >= documentHeight - 2) {
            if (checkScroll && replyCriteria?.hasMore) {
                replyCriteria = await showList(++page);
                checkScroll = false;
                setTimeout(() => { if (replyCriteria?.hasMore) checkScroll = true; }, 1100);
            }
        }
    });
});
