const likeService = (() => {

    // 좋아요한 일기 목록 조회
    const getLikedDiaries = async (memberId, callback, page = 1, size = 10) => {
        try {
            const response = await fetch(`/api/diaries/liked/${memberId}?page=${page}&size=${size}`);
            const result = await response.json();

            if (response.ok) {
                console.log("좋아요한 일기 목록 조회 성공");
                setTimeout(() => callback(result), 300);
            } else {
                const errorText = await response.text();
                console.error("Error:", response.status, errorText || "Fetch Error");
            }
            return result;
        } catch (error) {
            console.error("getLikedDiaries Error:", error);
        }
    };

    // 좋아요한 일기 총 개수 조회
    const getLikedDiaryCount = async (memberId, callback) => {
        try {
            const response = await fetch(`/api/diaries/liked/${memberId}/count`);
            const result = await response.json();

            if (response.ok) {
                console.log("좋아요한 일기 개수 조회 성공");
                if (callback) callback(result);
            } else {
                const errorText = await response.text();
                console.error("Error:", response.status, errorText || "Fetch Error");
            }
            return result;
        } catch (error) {
            console.error("getLikedDiaryCount Error:", error);
        }
    };

    return {
        getLikedDiaries: getLikedDiaries,
        getLikedDiaryCount: getLikedDiaryCount
    };

})();
