const memberService = (() => {
    const getMembers = async (callback, page = 1, keyword = "") => {
        try {
            const res = await fetch(`/api/admin/members`, {
                method: "POST",
                credentials: "same-origin",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ page, keyword }),
            });
            if (!res.ok) throw new Error("회원 목록 조회 실패");
            const data = await res.json();
            callback && callback(data);
            return data;
        } catch (e) {
            console.error(e);
        }
    };

    const getDetailMember = async (callback, memberId) => {
        try {
            const res = await fetch(`/api/admin/members/${memberId}`, {
                credentials: "same-origin",
            });
            if (!res.ok) throw new Error("회원 상세 조회 실패");
            const data = await res.json();
            callback && callback(data);
            return data;
        } catch (e) {
            console.error(e);
        }
    };

    return { getMembers, getDetailMember };
})();
