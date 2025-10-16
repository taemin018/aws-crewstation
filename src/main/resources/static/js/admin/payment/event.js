window.paymentInit = async function () {
    if (window.paymentInited) return;
    window.paymentInited = true;

    const section = document.getElementById("section-payment");
    if (!section) return;

    const modal =
        document.querySelector(".payment-modal") ||
        section.querySelector(".payment-modal");


    let page = 1;
    let isLoading = false;
    let hasMore = true;

    const loadList = async (p = 1) => {
        if (isLoading) return;
        isLoading = true;
        try {
            const list = await paymentService.getPayments(p); // /api/admin/payment?page=p
            console.log(
                "[payment] list len:",
                Array.isArray(list) ? list.length : list?.content?.length || 0
            );
            paymentLayout.showPayments(list);

            const arr = Array.isArray(list) ? list : list?.content || [];
            hasMore = Array.isArray(arr) && arr.length > 0;
        } catch (e) {
            console.error("결제 목록 로드 실패:", e);
        } finally {
            isLoading = false;
        }
    };

    paymentLayout.clear();
    await loadList(page);

    //  모달 열기/닫기
    if (modal) {
        let currentRow = null;

        const open = () => {
            modal.style.display = "block";
            requestAnimationFrame(() => {
                modal.classList.add("show");
                modal.style.background = "rgba(0,0,0,0.4)";
                document.body.classList.add("modal-open");
            });
        };

        const close = () => {
            modal.classList.remove("show");
            document.body.classList.remove("modal-open");
            setTimeout(() => (modal.style.display = "none"), 100);
            delete modal.dataset.paymentId;
            if (currentRow) {
                currentRow.classList.remove("current");
                currentRow = null;
            }
        };

        // 닫기 버튼 및 바깥 영역
        modal
            .querySelectorAll(".btn-close, .close")
            .forEach((btn) => btn.addEventListener("click", close));
        modal.addEventListener("click", (e) => {
            if (e.target === modal) close();
        });
        document.addEventListener("keydown", (e) => {
            if (e.key === "Escape" && modal.classList.contains("show")) close();
        });

        //  상세보기 버튼 클릭 이벤트
        const table = section.querySelector("table");
        if (table && !table.paymentRowBound) {
            table.paymentRowBound = true;

            table.addEventListener("click", async (e) => {
                const btn = e.target.closest(".action-btn");
                if (!btn) return;

                const row = btn.closest("tr");
                const id = btn.dataset.paymentid || row?.dataset.paymentId;
                if (!id) return;


                try {
                    const detail = await paymentService.getDetail(id);
                    paymentLayout.showPaymentDetail(detail);

                    modal.dataset.paymentId = id;
                    if (currentRow) currentRow.classList.remove("current");
                    if (row) {
                        row.classList.add("current");
                        currentRow = row;
                    }

                    open();
                } catch (err) {
                    console.error("결제 상세 조회 실패:", err);
                    alert("상세 조회에 실패했습니다.");
                }
            });
        }

        // 승인 / 취소 버튼
        const approveBtn = modal.querySelector(".btn-approve");
        const cancelBtn = modal.querySelector(".btn-cancel");

        const getCurrentId = () =>
            modal.dataset.paymentId ||
            section.querySelector("tr[data-payment-id].current")?.dataset.paymentId;

        approveBtn?.addEventListener("click", async () => {
            const id = getCurrentId();
            if (!id) return;
            const ok = await paymentService.processPayment(id, "approve");
            if (ok) close();
            else alert("승인 처리 실패");
        });

        cancelBtn?.addEventListener("click", async () => {
            const id = getCurrentId();
            if (!id) return;
            const ok = await paymentService.processPayment(id, "cancel");
            if (ok) close();
            else alert("취소 처리 실패");
        });
    } else {
        const waitModal = setInterval(() => {
            const modalCheck = document.querySelector(".payment-modal");
            if (modalCheck) {
                clearInterval(waitModal);
                window.paymentInit();
            }
        }, 700);
    }
};

window.addEventListener("DOMContentLoaded", () => {
    if (
        document.getElementById("section-payment") &&
        typeof window.paymentInit === "function"
    ) {
        window.paymentInit();
    }
});
