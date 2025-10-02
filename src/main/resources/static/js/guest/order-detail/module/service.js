// ===================== Order Service =====================
const orderService = (() => {

    // 주문 상세 조회
    const getOrderDetail = async (guestOrderNumber) => {
        try {
            const res = await fetch(`/api/guest/order-detail/${guestOrderNumber}`);
            if (!res.ok) throw new Error("주문 상세 조회 실패");
            return await res.json();
        } catch (err) {
            console.error("주문 상세 조회 실패:", err);
            return null;
        }
    };

    // 주문 취소
    const cancelOrder = async (guestOrderNumber) => {
        const res = await fetch(`/api/guest/order/${guestOrderNumber}/status?paymentPhase=REFUND`, { method: "PUT" });
        if (!res.ok) throw new Error("주문 취소 실패");
        return await res.text();
    };

    // 수령 완료
    const completeReceive = async (guestOrderNumber) => {
        const res = await fetch(`/api/guest/order/${guestOrderNumber}/status?paymentPhase=RECEIVED`, { method: "PUT" });
        if (!res.ok) throw new Error("수령 완료 실패");
        return await res.text();
    };

    // 별점 주기 -> 케미지수 업데이트
    const submitReview = async (guestOrderNumber, score) => {
        const res = await fetch(`/api/guest/order/${guestOrderNumber}/status?paymentPhase=REVIEWED&score=${score}`, {
            method: "PUT"
        });
        if (!res.ok) throw new Error("리뷰 등록 실패");
        return await res.text();
    };

    return {
        getOrderDetail : getOrderDetail,
        cancelOrder : cancelOrder,
        completeReceive : completeReceive,
        submitReview : submitReview
    };

})();
