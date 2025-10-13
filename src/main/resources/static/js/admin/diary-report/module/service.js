const reportService = (() => {

    // 다이어리 신고 목록
    const getReports = async (page = 1) => {
        const res = await fetch(`/api/admin/diaries?page=${page}`);
        if (!res.ok) {
            console.error("신고 목록 로드 실패");
            return [];
        }
        return await res.json();
    };

    // 다이어리 신고 상세
    const getReportDetail = async (reportId) => {
        const res = await fetch(`/api/admin/diary/${reportId}`);
        if (!res.ok) {
            console.error("신고 상세 조회 실패");
            return null;
        }
        return await res.json();
    };

    return { getReports : getReports, getReportDetail : getReportDetail };
})();
