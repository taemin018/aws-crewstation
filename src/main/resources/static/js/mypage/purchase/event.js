document.addEventListener("DOMContentLoaded", () => {
    // 1. 구매 상태 개수 조회
    const purchases = document.querySelectorAll(".purchase-list-wrapper");
    const requestNumber = document.querySelector(".request-number");
    const waitingNumber = document.querySelector(".watting-number");
    const paymentOkNumber = document.querySelector(".payment-ok");
    const receiveNumber = document.querySelector(".receive-number");

    let request = 0;
    let paymentOk = 0;
    let waiting = 0;
    let receive = 0;

    purchases.forEach((purchase) => {
        const title = purchase.querySelector(".title-status");
        if (!title) return;

        const text = title.innerText.trim();

        switch (text) {
            case "수락 요청":
                request++;
                break;
            case "결제 완료":
                paymentOk++;
                break;
            case "결제 대기":
                waiting++;
                break;
            case "수령 완료":
                receive++;
                break;
        }
    });

    if (requestNumber) requestNumber.innerText = request;
    if (waitingNumber) waitingNumber.innerText = waiting;
    if (paymentOkNumber) paymentOkNumber.innerText = paymentOk;
    if (receiveNumber) receiveNumber.innerText = receive;

    // 버튼 동작

    document.querySelectorAll(".purchase-cancel").forEach((btn) => {
        btn.addEventListener("click", () => {
            if (confirm("정말 주문을 취소하시겠습니까?")) {
                // TODO: 주문취소 API 호출
                console.log("주문취소 요청");
            }
        });
    });

    document.querySelectorAll(".receive-button").forEach((btn) => {
        btn.addEventListener("click", () => {
            if (confirm("제품을 수령하셨나요?")) {
                // TODO: 수령 완료 API 호출
                console.log("수령 완료 요청");
            }
        });
    });

    // 4. 별점 남기기
    const stars = document.querySelectorAll("label.star");
    let currentStar = 0;

    stars.forEach((star) => {
        star.addEventListener("click", () => {
            const point = parseInt(star.dataset.point);
            currentStar = point;

            stars.forEach((s, i) => {
                s.parentElement.classList.toggle("full", i < point);
            });

            // hidden input 값 세팅 (백엔드 전송용)
            const hiddenInput = document.querySelector("#star");
            if (hiddenInput) hiddenInput.value = currentStar;
        });
    });

    // 5. 모달 열기 / 닫기
    const modal = document.querySelector(".modal-wrapper");
    const closeButton = document.querySelector(".close-button");

    document.querySelectorAll(".star-button").forEach((btn) => {
        btn.addEventListener("click", () => {
            if (modal) modal.style.display = "flex";
        });
    });

    if (closeButton) {
        closeButton.addEventListener("click", () => {
            if (modal) modal.style.display = "none";
        });
    }
});
