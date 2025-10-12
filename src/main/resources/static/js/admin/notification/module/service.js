const noticeService = (() => {
    const getList = async (callback, page = 1) => {
        try {
            const res = await fetch(`/api/admin/notices?page=${encodeURIComponent(page)}`, {
                headers: { "Accept": "application/json" },
                credentials: "same-origin",
            });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            const data = await res.json();
            console.log("[noticeService]", data);
            callback?.(data);
        } catch (err) {
            console.error("공지 목록 로드 실패:", err);
        }
    };
    return { getList };
})();
