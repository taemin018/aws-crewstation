const noticeService = (() => {

    // 공지 목록 조회
    const getList = async (callback, page = 1) => {
        try {
            const res = await fetch(`/api/admin/notices?page=${page}`);
            const data = await res.json();

            if (res.ok) {
                console.log("공지 목록 불러오기 성공");
                callback?.(data);
            } else {
                console.error("공지 목록 실패:", data);
            }
        } catch (err) {
            console.error("서버 통신 오류:", err);
        }
    };

    // 공지 작성
    const save = async (title, content) => {
        try {
            const res = await fetch("/api/admin/notices", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ title, content }),
            });

            if (res.ok) {
                console.log("공지 작성 완료");
                return true;
            } else {
                const text = await res.text();
                console.error("공지 작성 실패:", text);
                return false;
            }
        } catch (err) {
            console.error("서버 오류:", err);
            return false;
        }
    };

    return { getList, save };
})();
