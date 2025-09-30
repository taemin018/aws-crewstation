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

    return { getProfile : getProfile };
})();

// ===================== Reply Service =====================
const replyService = (() => {
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

    return { getReplyDiaries : getReplyDiaries,
            getReplyDiaryCount : getReplyDiaryCount };
})();

// ===================== Like Service =====================
const likeService = (() => {
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

    return { getLikedDiaryCount : getLikedDiaryCount };
})();
