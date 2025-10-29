const memberService = (() => {
    const safeJson = async (res) => {
        if (res.status === 204) return null;
        const text = await res.text();
        return text ? JSON.parse(text) : null;
    };

    const getMembers = async (callback, page = 1, keyword = "", extra = {}) => {
        const res = await fetch(`/api/admin/members`, {
            method: "POST",
            credentials: "include",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json",
            },
            body: JSON.stringify({ page, keyword, ...extra }),
        });

        if (!res.ok) throw new Error(`회원 목록 조회 실패 (${res.status})`);
        const text = await res.text();
        const data = text ? JSON.parse(text) : null;
        callback && callback(data);
        return data;
    };


    const getDetailMember = async (callback, memberId) => {
        const res = await fetch(`/api/admin/members/${memberId}`, {
            method: "GET",
            credentials: "include",
            headers: { "Accept": "application/json" },
        });
        if (!res.ok) throw new Error(`회원 상세 조회 실패 (${res.status})`);
        const data = await safeJson(res);
        callback && callback(data);
        return data;
    };

    return { getMembers, getDetailMember };
})();
