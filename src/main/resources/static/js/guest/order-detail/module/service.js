// ===================== Order Service =====================
const orderService = (() => {

    // 주문 상세 조회
    const getOrderDetail = (guestOrderNumber) => {
        return fetch(`/api/guest/order-detail/${guestOrderNumber}`)
            .then(res => res.json())
            .catch(err => {
                console.error("주문 상세 조회 실패:", err);
                return null;
            });
    };

    // 주문 취소
    const cancelOrder = (guestOrderNumber) => {
        fetch(`/api/guest/order/${guestOrderNumber}/cancel`, { method: "POST" })
            .then(res => {
                if (res.ok) {
                    orderLayout.updateOrderStatus("주문 취소");
                    orderLayout.showToast("주문이 취소되었습니다.");
                }
            })
            .catch(err => {
                console.error(err);
                orderLayout.showToast("주문 취소 중 오류 발생");
            });
    };

    // 수령 완료
    const completeReceive = (guestOrderNumber) => {
        fetch(`/api/guest/order/${guestOrderNumber}/receive`, { method: "POST" })
            .then(res => {
                if (res.ok) {
                    orderLayout.updateOrderStatus("수령 완료");
                    orderLayout.showToast("상품을 수령하였습니다.");
                }
            })
            .catch(err => {
                console.error(err);
                orderLayout.showToast("수령 처리 중 오류 발생");
            });
    };

    // 별점 주기 → 케미지수 업데이트
    const submitReview = (guestOrderNumber, score) => {
        fetch(`/api/guest/order/${guestOrderNumber}/review?score=${score}`, {
            method: "POST"
        })
            .then(res => {
                if (res.ok) {
                    orderLayout.showToast("별점이 반영되었습니다.");
                }
            })
            .catch(err => {
                console.error(err);
                orderLayout.showToast("별점 반영 중 오류 발생");
            });
    };

    return {
        getOrderDetail : getOrderDetail,
        cancelOrder : cancelOrder,
        completeReceive : completeReceive,
        submitReview : submitReview
    };

})();
