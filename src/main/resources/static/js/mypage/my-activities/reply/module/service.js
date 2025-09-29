// ===================== Member Service =====================
const memberService = (() => {
    const getProfile = async (memberId) => {
        try {
            const response = await fetch(`/api/member/${memberId}`);
            if (!response.ok) {
                throw new Error(`Error: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error("getProfile Error:", error);
            return null;
        }
    };

    return { getProfile : getProfile };
})();

// ===================== Reply Service =====================
const replyService = (() => {
    // 내가 댓글 단 다이어리 목록 조회
    const getReplyDiaries = async (memberId, page = 1, size = 10) => {
        try {
            const response = await fetch(`/api/diaries/replies/${memberId}?page=${page}&size=${size}`);
            if (!response.ok) {
                throw new Error(`Error: ${response.status}`);
            }
            const result = await response.json();
            console.log("댓글 단 다이어리 목록 조회 성공", result);
            return result; // { replyDiaryDTOs, criteria }
        } catch (error) {
            console.error("getReplyDiaries Error:", error);
            return { replyDiaryDTOs: [], criteria: { hasMore: false } };
        }
    };

    // 내가 댓글 단 다이어리 총 개수 조회
    const getReplyDiaryCount = async (memberId) => {
        try {
            const response = await fetch(`/api/diaries/replies/${memberId}/count`);
            if (!response.ok) {
                throw new Error(`Error: ${response.status}`);
            }
            const count = await response.json();
            console.log("댓글 단 다이어리 개수 조회 성공", count);
            return count;
        } catch (error) {
            console.error("getReplyDiaryCount Error:", error);
            return 0;
        }
    };

    return { getReplyDiaries, getReplyDiaryCount };
})();
