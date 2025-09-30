// ===================== Member Service =====================
const memberService = (() => {
    const getProfile = async (memberId) => {
        try {
            const response = await fetch(`/api/member/${memberId}`);
            if (!response.ok) throw new Error(`Error: ${response.status}`);
            return await response.json();
        } catch (error) {
            console.error("getProfile Error:", error);
            return null;
        }
    };

    return { getProfile };
})();


// ===================== Reply Service =====================
const replyService = (() => {
    // 내가 댓글 단 일기 목록 조회
    const getReplyDiaries = async (memberId, page = 1, size = 10) => {
        try {
            const response = await fetch(`/api/diaries/replies/${memberId}?page=${page}&size=${size}`);
            if (!response.ok) throw new Error(`Error: ${response.status}`);
            return await response.json(); // { replyDiaryDTOs, criteria }
        } catch (error) {
            console.error("getReplyDiaries Error:", error);
            return { replyDiaryDTOs: [], criteria: { hasMore: false } };
        }
    };

    // 내가 댓글 단 일기 총 개수 조회
    const getReplyDiaryCount = async (memberId) => {
        try {
            const response = await fetch(`/api/diaries/replies/${memberId}/count`);
            if (!response.ok) throw new Error(`Error: ${response.status}`);
            return await response.json();
        } catch (error) {
            console.error("getReplyDiaryCount Error:", error);
            return 0;
        }
    };

    return { getReplyDiaries, getReplyDiaryCount };
})();


// ===================== Like Service =====================
const likeService = (() => {
    // 좋아요한 일기 목록 조회
    const getLikedDiaries = async (memberId, page = 1, size = 10) => {
        try {
            const response = await fetch(`/api/diaries/liked/${memberId}?page=${page}&size=${size}`);
            if (!response.ok) throw new Error(`Error: ${response.status}`);
            return await response.json(); // { likedDiaryDTOs, criteria }
        } catch (error) {
            console.error("getLikedDiaries Error:", error);
            return { likedDiaryDTOs: [], criteria: { hasMore: false } };
        }
    };

    // 좋아요한 일기 총 개수 조회
    const getLikedDiaryCount = async (memberId) => {
        try {
            const response = await fetch(`/api/diaries/liked/${memberId}/count`);
            if (!response.ok) throw new Error(`Error: ${response.status}`);
            return await response.json();
        } catch (error) {
            console.error("getLikedDiaryCount Error:", error);
            return 0;
        }
    };

    // 좋아요 취소
    const cancelLike = async (memberId, diaryId) => {
        try {
            const response = await fetch(`/api/diaries/liked/${memberId}/${diaryId}`, {
                method: "DELETE"
            });
            if (!response.ok) throw new Error(`Error: ${response.status}`);
            return await response.json();
        } catch (error) {
            console.error("cancelLike Error:", error);
            return { success: false, message: error.message };
        }
    };

    return { getLikedDiaries : getLikedDiaries,
            getLikedDiaryCount : getLikedDiaryCount,
            cancelLike : cancelLike };
})();
