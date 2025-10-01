// ===================== Order Event =====================
document.addEventListener("DOMContentLoaded", () => {
    const container = document.getElementById("order-detail");

    const guestOrderNumber = "2025093022321855389"; // 나중에 토큰에서 꺼내와야함

    // 상세 조회
    orderService.getOrderDetail(guestOrderNumber)
        .then(order => {
            if (!order) {
                container.innerHTML = "<p>주문 내역이 없습니다.</p>";
                return;
            }

            orderLayout.renderOrderDetail(container, order);

            // 버튼 이벤트 연결
            const cancelBtn = container.querySelector(".cancel-btn");
            const receiveBtn = container.querySelector(".receive-btn");
            const reviewBtn = container.querySelector(".review-btn");

            if (cancelBtn) {
                cancelBtn.addEventListener("click", () => orderService.cancelOrder(guestOrderNumber));
            }

            if (receiveBtn) {
                receiveBtn.addEventListener("click", () => orderService.completeReceive(guestOrderNumber));
            }

            if (reviewBtn) {
                reviewBtn.addEventListener("click", () => {
                    // 모달 열기
                    const modal = document.querySelector(".modal-wrapper");
                    modal.style.display = "flex";

                    // 별점 선택
                    const stars = modal.querySelectorAll("label.star");
                    let selectedScore = 0;

                    stars.forEach((star) => {
                        star.addEventListener("click", () => {
                            selectedScore = parseInt(star.dataset.point, 10);

                            stars.forEach((s, i) => {
                                if (selectedScore >= i + 1) {
                                    s.classList.add("full");
                                } else {
                                    s.classList.remove("full");
                                }
                            });
                        });
                    });

                    // 제출 버튼
                    const submitBtn = modal.querySelector(".button-submit");
                    submitBtn.addEventListener("click", (e) => {
                        e.preventDefault();
                        if (selectedScore >= 1 && selectedScore <= 5) {
                            orderService.submitReview(guestOrderNumber, selectedScore);
                            modal.style.display = "none";
                        } else {
                            orderLayout.showToast("별점을 선택해주세요.");
                        }
                    });

                    // 닫기 버튼
                    const closeBtn = modal.querySelector(".close-button");
                    closeBtn.addEventListener("click", () => {
                        modal.style.display = "none";
                    });
                });
            }
        });
});
