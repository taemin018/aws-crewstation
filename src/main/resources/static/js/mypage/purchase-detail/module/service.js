// ===================== Order Service =====================
const orderService = (() => {

    // 주문 상세 조회
    const getOrderDetail = async (postId) => {
        try {
            const res = await fetch(`/api/mypage/purchase-detail/${postId}`);
            if (!res.ok) throw new Error("주문 상세 조회 실패");
            return await res.json();
        } catch (err) {
            console.error("주문 상세 조회 실패:", err);
            return null;
        }
    };

    // 주문 취소
    const cancelOrder = async (purchaseId) => {
        try {
            const res = await fetch(`/api/mypage/purchase-detail/${purchaseId}/status?paymentPhase=REFUND`, {
                method: "PUT"
            });
            if (!res.ok) throw new Error("주문 취소 실패");
            return await res.text();
        } catch (err) {
            console.error("주문 취소 실패:", err);
            throw err;
        }
    };

    // 결제하기 (결제 상태만 업데이트)
    const payOrder = async (purchaseId) => {
        try {
            const res = await fetch(`/api/mypage/purchase-detail/${purchaseId}/status?paymentPhase=SUCCESS`, {
                method: "PUT"
            });
            if (!res.ok) throw new Error("결제 상태 변경 실패");
            return await res.text();
        } catch (err) {
            console.error("결제 상태 변경 실패:", err);
            throw err;
        }
    };

    // 결제 완료 전송 (Bootpay done 시 서버 통보)
    const completePayment = async (paymentData) => {
        try {
            const res = await fetch(`/api/payment/complete`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(paymentData)
            });

            const text = await res.text();
            if (!res.ok) throw new Error("결제 완료 처리 실패: " + text);

            console.log("결제 완료 서버 응답:", text);
            return text;
        } catch (err) {
            console.error("결제 완료 전송 실패:", err);
            throw err;
        }
    };

    // 수령 완료
    const completeReceive = async (purchaseId) => {
        try {
            const res = await fetch(`/api/mypage/purchase-detail/${purchaseId}/status?paymentPhase=RECEIVED`, {
                method: "PUT"
            });
            if (!res.ok) throw new Error("수령 완료 실패");
            return await res.text();
        } catch (err) {
            console.error("수령 완료 처리 실패:", err);
            throw err;
        }
    };

    // 별점 주기 → 케미지수 업데이트 + 주문 상태 변경
    const submitReview = async (sellerId, purchaseId, score) => {
        try {
            const res = await fetch(`/api/mypage/purchase-detail/${purchaseId}/rating`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    sellerId: sellerId,
                    purchaseId: purchaseId,
                    rating: score
                })
            });

            if (!res.ok) throw new Error("리뷰 등록 실패");
            return await res.json();
        } catch (err) {
            console.error("리뷰 등록 실패:", err);
            throw err;
        }
    };

    // 외부 노출
    return {
        getOrderDetail : getOrderDetail,
        cancelOrder : cancelOrder,
        payOrder : payOrder,
        completePayment : completePayment,
        completeReceive : completeReceive,
        submitReview : submitReview
    };

})();
