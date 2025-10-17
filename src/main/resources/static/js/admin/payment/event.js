// event.js
window.paymentInit = async function () {
    if (window.paymentInited) return;
    window.paymentInited = true;

    const section = document.getElementById("section-payment");
    if (!section) return;

    const modal =
        document.querySelector(".payment-modal") ||
        section.querySelector(".payment-modal");

    // ==== 상태(다중) / 검색어 상태 ====
    const STATUS_MAP = {
        "pay-progress": "PAY_PROGRESS",
        "pay-success":  "PAY_SUCCESS",
        "pay-cancel":   "PAY_CANCEL",
    };

    const state = {
        page: 1,
        categories: [],   // 예: ["PAY_PROGRESS","PAY_CANCEL"]
        keyword: "",
    };

    // "결제완료" 아이콘에 누락된 data-target 보정
    (function ensureSuccessIconMeta() {
        const successItem = Array
            .from(section.querySelectorAll(".boot-check"))
            .find(el => el.querySelector(".boot-check-content")?.textContent.trim() === "결제완료");
        if (!successItem) return;
        const icon = successItem.querySelector("i.mdi");
        if (icon) {
            icon.classList.add("btn-status");
            if (!icon.dataset.target) icon.dataset.target = "pay-success";
        }
    })();

    // 유틸
    const getKeyword = () =>
        section.querySelector(".filter-search input.form-control")?.value?.trim() || "";

    const getSelectedTargets = () =>
        Array.from(section.querySelectorAll(".btn-status.is-checked"))
            .map(i => i.dataset.target)
            .filter(Boolean);

    const getSelectedCategories = () =>
        getSelectedTargets().map(t => STATUS_MAP[t]).filter(Boolean);

    // ==== 목록 로드 ====
    let isLoading = false;
    let hasMore = true;

    const loadList = async (p = 1) => {
        if (isLoading) return;
        isLoading = true;
        try {
            state.page = p;
            state.keyword = getKeyword();

            const list = await paymentService.getPayments(p, {
                categories: state.categories,
                keyword: state.keyword,
            });

            console.log(
                "[payment] list len:",
                Array.isArray(list) ? list.length : list?.content?.length || 0
            );

            paymentLayout.showPayments(list);

            const arr = Array.isArray(list) ? list : list?.content || [];
            hasMore = Array.isArray(arr) && arr.length > 0;
        } catch (e) {
            console.error("결제 목록 로드 실패:", e);
            const tbody = section.querySelector("#payment-tbody");
            if (tbody) {
                tbody.innerHTML =
                    `<tr><td colspan="7" class="text-center text-danger py-4">결제 목록을 불러오지 못했습니다.</td></tr>`;
            }
        } finally {
            isLoading = false;
        }
    };

    paymentLayout.clear();
    await loadList(1);

    // ==== 상태 팝업 ====
    (function bindStatusPopup() {
        const btnOpen  = section.querySelector("#btn-filter-status");
        const pop      = section.querySelector("#filter-status .bt-pop-menu");
        const back     = pop?.querySelector(".bt-pop-menu-back");
        const ctx      = pop?.querySelector(".bt-pop-menu-context"); // 컨텐츠 래퍼
        const btnAll   = section.querySelector("#btn-select-all");
        const btnNone  = section.querySelector("#btn-deselect-all");
        const btnApply = section.querySelector("#btn-apply-status");

        if (!btnOpen || !pop || !ctx || pop._bound) return;
        pop._bound = true;

        // ---- 체크박스 시각 토글 유틸 ----
        const setCheckedForIcon = (icon, on) => {
            if (!icon) return;
            icon.classList.toggle("is-checked", on);
            const box = icon.closest(".boot-check-box");
            if (box) box.classList.toggle("is-checked", on); // 파란 배경/테두리
        };

        const toggleCheckedForIcon = (icon) => {
            const now = !icon.classList.contains("is-checked");
            setCheckedForIcon(icon, now);
        };

        const openPop = () => {
            back?.classList.add("show");
            ctx.classList.add("show");

            // 버튼 바로 아래 고정
            const hostRect = btnOpen.getBoundingClientRect();
            ctx.style.position = "fixed";
            ctx.style.top = `${hostRect.bottom + 8}px`;
            ctx.style.left = `${hostRect.left}px`;
            ctx.style.zIndex = "3000";
        };

        const closePop = () => {
            ctx.classList.remove("show");
            back?.classList.remove("show");
        };

        btnOpen.addEventListener("click", (e) => {
            e.preventDefault();
            e.stopPropagation();
            ctx.classList.contains("show") ? closePop() : openPop();
        });

        // 내부 클릭 시 닫히지 않게
        ctx.addEventListener("click", (e) => e.stopPropagation());
        back?.addEventListener("click", (e) => { e.stopPropagation(); closePop(); });

        // 바깥 클릭 시 닫기
        document.addEventListener("click", () => {
            if (ctx.classList.contains("show")) closePop();
        });

        // ---- 상태 아이템(아이콘/박스/텍스트)을 모두 클릭 타깃으로 위임 처리 ----
        ctx.addEventListener("click", (e) => {
            const icon = e.target.closest(".btn-status"); // <i ... class="btn-status">
            const item = e.target.closest(".boot-check"); // 라인 전체
            if (!icon && !item) return;

            e.preventDefault();
            e.stopPropagation();

            const targetIcon =
                icon ||
                item.querySelector(".btn-status"); // 라인 클릭 시 내부 아이콘 찾기

            if (targetIcon) toggleCheckedForIcon(targetIcon);
        });

        // 키보드 접근성 (Space/Enter)
        ctx.addEventListener("keydown", (e) => {
            if (!(e.key === " " || e.key === "Enter")) return;
            const item = e.target.closest(".boot-check");
            if (!item) return;
            const icon = item.querySelector(".btn-status");
            if (!icon) return;

            e.preventDefault();
            toggleCheckedForIcon(icon);
        });

        // 전체선택
        btnAll?.addEventListener("click", (e) => {
            e.preventDefault();
            e.stopPropagation();
            section.querySelectorAll(".btn-status").forEach((i) => setCheckedForIcon(i, true));
        });

        // 선택취소
        btnNone?.addEventListener("click", (e) => {
            e.preventDefault();
            e.stopPropagation();
            section.querySelectorAll(".btn-status").forEach((i) => setCheckedForIcon(i, false));
        });

        // 확인
        btnApply?.addEventListener("click", async (e) => {
            e.preventDefault();
            e.stopPropagation();

            const cats = Array
                .from(section.querySelectorAll(".btn-status.is-checked"))
                .map(i => STATUS_MAP[i.dataset.target])
                .filter(Boolean);

            if (!cats.length) {
                alert("최소 1개 이상 선택하세요.");
                return;
            }

            state.categories = cats;
            closePop();
            await loadList(1);
        });
    })();

    // ==== 검색 버튼/엔터 ====
    (function bindSearch() {
        const input = section.querySelector(".filter-search input.form-control");
        const btn   = section.querySelector(".filter-search .btn-search");

        btn?.addEventListener("click", (e) => {
            e.preventDefault();
            loadList(1);
        });

        input?.addEventListener("keydown", (e) => {
            if (e.key === "Enter") loadList(1);
        });
    })();

    // ==== 상세 모달 열기/닫기 + 상세조회 ====
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
        modal.querySelectorAll(".btn-close, .close")
            .forEach((btn) => btn.addEventListener("click", close));

        modal.addEventListener("click", (e) => { if (e.target === modal) close(); });

        document.addEventListener("keydown", (e) => {
            if (e.key === "Escape" && modal.classList.contains("show")) close();
        });

        // 상세보기 버튼
        const table = section.querySelector("table");
        if (table && !table.paymentRowBound) {
            table.paymentRowBound = true;

            table.addEventListener("click", async (e) => {
                const btn = e.target.closest(".action-btn, .btn-detail");
                if (!btn) return;

                const row = btn.closest("tr");
                const id  = btn.dataset.paymentid || row?.dataset.paymentId;
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

        // (승인/취소 버튼 이벤트는 유지)
        const approveBtn   = modal.querySelector(".btn-approve");
        const cancelBtn    = modal.querySelector(".btn-cancel");

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
    if (document.getElementById("section-payment") && typeof window.paymentInit === "function") {
        window.paymentInit();
    }
});
