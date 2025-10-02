// ===================== Order Event =====================
document.addEventListener("DOMContentLoaded", () => {
    const container = document.getElementById("order-detail");

    const guestOrderNumber = "2025093022321855389"; // TODO: 토큰에서 가져오기

    // 주문 상세 조회
    orderService.getOrderDetail(guestOrderNumber)
        .then(order => {
            console.log("주문 상세:", order);
            if (!order) {
                container.innerHTML = "<p>주문 내역이 없습니다.</p>";
                return;
            }

            // UI 렌더링
            orderLayout.renderOrderDetail(container, order);

            // 버튼들
            const cancelBtn = container.querySelector(".cancel-btn");
            const paymentBtn = container.querySelector(".payment-btn");
            const receiveBtn = container.querySelector(".receive-btn");
            const reviewBtn = container.querySelector(".review-btn");

            // === 주문 취소 ===
            if (cancelBtn && cancelBtn.classList.contains("active")) {
                cancelBtn.addEventListener("click", () => {
                    orderService.cancelOrder(guestOrderNumber)
                        .then(() => {
                            orderLayout.updateOrderStatus("주문 취소");
                            orderLayout.showToast("주문이 취소되었습니다.", true);
                        })
                        .catch(() => orderLayout.showToast("주문 취소 실패. 다시 시도해주세요."));
                });
            }

            // === 결제하기 ===
            if (paymentBtn && paymentBtn.classList.contains("active")) {
                paymentBtn.addEventListener("click", () => {
                    orderService.payOrder(guestOrderNumber)
                        .then(() => {
                            orderLayout.updateOrderStatus("결제 완료");
                            orderLayout.showToast("결제가 완료되었습니다.", true);
                        })
                        .catch(() => orderLayout.showToast("결제 실패. 다시 시도해주세요."));
                });
            }


            // === 수령 완료 ===
            if (receiveBtn && receiveBtn.classList.contains("active")) {
                receiveBtn.addEventListener("click", () => {
                    orderService.completeReceive(guestOrderNumber)
                        .then(() => {
                            orderLayout.updateOrderStatus("수령 완료");
                            orderLayout.showToast("수령 확인이 완료되었습니다.", true);
                        })
                        .catch(() => orderLayout.showToast("수령 처리 실패. 다시 시도해주세요."));
                });
            }

            // === 리뷰 작성 ===
            if (reviewBtn && reviewBtn.classList.contains("active")) {
                reviewBtn.addEventListener("click", () => {
                    const modal = document.querySelector(".modal-wrapper");
                    const stars = modal.querySelectorAll("label.star");
                    const submitBtn = modal.querySelector(".button-submit");
                    const closeBtn = modal.querySelector(".close-button");

                    let selectedScore = 0;
                    stars.forEach(s => s.classList.remove("full"));
                    modal.style.display = "flex";

                    // 별점 선택
                    stars.forEach((star) => {
                        star.onclick = () => {
                            selectedScore = parseInt(star.dataset.point, 10);
                            stars.forEach((s, i) => {
                                s.classList.toggle("full", selectedScore >= i + 1);
                            });
                        };
                    });

                    // 별점 제출
                    submitBtn.onclick = (e) => {
                        e.preventDefault();
                        if (selectedScore >= 1 && selectedScore <= 5) {
                            orderService.submitReview(order.sellerId, order.purchaseId, selectedScore)  //
                                .then(() => {
                                    modal.style.display = "none";
                                    orderLayout.showToast("별점이 정상적으로 등록되었습니다.", true);
                                    if (reviewBtn) reviewBtn.remove();
                                })
                                .catch(() => orderLayout.showToast("리뷰 등록 실패. 다시 시도해주세요."));
                        } else {
                            orderLayout.showToast("별점을 선택해주세요.");
                        }
                    };

                    // 닫기 버튼
                    closeBtn.onclick = () => {
                        modal.style.display = "none";
                    };
                });
            }
        });
});
