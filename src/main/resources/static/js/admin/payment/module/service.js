const paymentService = (() => {
    const getPayments = async (page = 1) => {
        const res = await fetchWithRefresh(`/api/admin/payment?page=${page}`, {
            method: 'GET',
            credentials: 'include',
        });
        if (!res.ok) return [];
        return await res.json();
    };

    const getDetail = async (paymentId) => {
        const res = await fetchWithRefresh(`/api/admin/payment/${encodeURIComponent(paymentId)}`, {
            method: 'GET',
            credentials: 'include',
        });
        if (!res.ok) throw new Error('결제 상세 로드 실패');
        return await res.json();
    };

    const processPayment = async (paymentId, action = 'cancel', body = {}) => {
        const res = await fetchWithRefresh(
            `/api/admin/payment/${encodeURIComponent(paymentId)}/${encodeURIComponent(action)}`,
            {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include',
                body: JSON.stringify(body),
            }
        );
        return res.ok;
    };

    return { getPayments, getDetail, processPayment };
})();
