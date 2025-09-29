// ===================== Member Service =====================
const memberService = (() => {
    // 로그인한 멤버 프로필 조회
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

    return {
        getProfile : getProfile
    };
})();

// ===================== Like Service =====================
const likeService = (() => {
    // 좋아요한 일기 목록 조회
    const getLikedDiaries = async (memberId, callback, page = 1, size = 10) => {
        try {
            const response = await fetch(`/api/diaries/liked/${memberId}?page=${page}&size=${size}`);
            if (!response.ok) {
                throw new Error(`Error: ${response.status}`);
            }

            const result = await response.json();
            console.log("좋아요한 일기 목록 조회 성공");

            if (callback) {
                setTimeout(() => callback(result), 300);
            }
            return result;
        } catch (error) {
            console.error("getLikedDiaries Error:", error);
            return [];
        }
    };

    // 좋아요한 일기 총 개수 조회
    const getLikedDiaryCount = async (memberId, callback) => {
        try {
            const response = await fetch(`/api/diaries/liked/${memberId}/count`);
            if (!response.ok) {
                throw new Error(`Error: ${response.status}`);
            }

            const result = await response.json();
            const count = result.count;

            console.log("좋아요한 일기 개수 조회 성공");
            if (callback) callback(count);

            return count;
        } catch (error) {
            console.error("getLikedDiaryCount Error:", error);
            return 0;
        }
    };

    return {
        getLikedDiaries : getLikedDiaries,
        getLikedDiaryCount : getLikedDiaryCount
    };
})();
