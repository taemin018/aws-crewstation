// 다이어리 신고 - SPA 초기화 함수
window.diaryReportInit = async function () {
    // 이미 초기화했으면 재진입 시 스킵
    if (window.__diaryReportInited) return;
    window.__diaryReportInited = true;

    // ===== 무한 스크롤 상태 =====
    let diaryPage = 1;
    let diaryCheckScroll = true;
    let diaryHasMore = true;

    const loadReportDiaryList = async (page = 1) => {
        const reports = await diaryReportService.getReports(page);
        diaryReportLayout.showReportDiaryList(reports);
        diaryHasMore = Array.isArray(reports) && reports.length > 0;
    };

    // 최초 로드 (섹션 진입 시 1회)
    await loadReportDiaryList(diaryPage);

    // 스크롤 컨테이너 (메인 패널)
    const DiaryScrollContainer = document.querySelector("#bootpay-main");
    if (DiaryScrollContainer && !DiaryScrollContainer.__diaryScrollBound) {
        DiaryScrollContainer.__diaryScrollBound = true; // 중복 바인딩 방지

        DiaryScrollContainer.addEventListener("scroll", async () => {
            const scrollTop = DiaryScrollContainer.scrollTop;
            const clientHeight = DiaryScrollContainer.clientHeight;
            const scrollHeight = DiaryScrollContainer.scrollHeight;

            if (scrollTop + clientHeight >= scrollHeight - 100) {
                if (diaryCheckScroll && diaryHasMore) {
                    diaryCheckScroll = false;
                    // ✅ 버그 fix: ++page -> ++diaryPage
                    await loadReportDiaryList(++diaryPage);
                    setTimeout(() => {
                        if (diaryHasMore) diaryCheckScroll = true;
                    }, 800);
                }
            }
        });
    }

    // =============== 사이드바 ===============
    (() => {
        const side = document.querySelector("#bootpay-side");
        if (!side) return;

        const topButtons = side.querySelectorAll(".menu-item > .menu-btn");
        const subLists = side.querySelectorAll(".menu-item > .menu-sub-list");

        const closeAllMenus = () => {
            subLists.forEach((ul) => {
                ul.classList.remove("show");
                ul.style.display = "none";
            });
            topButtons.forEach((btn) => btn.classList.remove("active", "current"));
            side.querySelectorAll(".menu-list > li").forEach((li) =>
                li.classList.remove("open")
            );
        };

        const syncFromDOM = () => {
            subLists.forEach((ul) => {
                const hasActiveChild = !!ul.querySelector(".boot-link.active");
                const markedShow = ul.classList.contains("show");
                if (hasActiveChild || markedShow) {
                    ul.classList.add("show");
                    ul.style.display = "block";
                    const btn = ul.previousElementSibling;
                    const li = ul.closest("li");
                    btn && btn.classList.add("active", "current");
                    li && li.classList.add("open");
                }
            });

            side.querySelectorAll(".menu-item > .menu-btn.active").forEach((btn) => {
                const panel = btn.nextElementSibling;
                if (panel && panel.classList.contains("menu-sub-list")) {
                    panel.classList.add("show");
                    panel.style.display = "block";
                    btn.classList.add("current");
                    btn.closest("li")?.classList.add("open");
                }
            });
        };

        const hasExplicit = !!side.querySelector(
            ".menu-btn.active, .menu-btn.current, .menu-sub-list.show, .menu-sub-list .boot-link.active"
        );
        if (hasExplicit) syncFromDOM();
        else closeAllMenus();

        side.addEventListener("click", (e) => {
            const subLink = e.target.closest(".menu-sub-list .boot-link");
            if (subLink && side.contains(subLink)) {
                // SPA 라우팅은 전역 router에서 처리 → 여기선 메뉴 UI만 유지
                const ul = subLink.closest(".menu-sub-list");
                ul.querySelectorAll(".boot-link.active").forEach((a) =>
                    a.classList.remove("active")
                );
                subLink.classList.add("active");

                closeAllMenus();
                ul.classList.add("show");
                ul.style.display = "block";
                const btn = ul.previousElementSibling;
                const li = ul.closest("li");
                btn && btn.classList.add("active", "current");
                li && li.classList.add("open");
                return;
            }

            const btnTop = e.target.closest(".menu-item > .menu-btn");
            if (!btnTop || !side.contains(btnTop)) return;

            const panel = btnTop.nextElementSibling;
            const hasPane = panel && panel.classList.contains("menu-sub-list");
            const wasOpen = hasPane && panel.classList.contains("show");

            closeAllMenus();
            btnTop.classList.add("active");

            if (hasPane && !wasOpen) {
                panel.classList.add("show");
                panel.style.display = "block";
                btnTop.classList.add("current");
                btnTop.closest("li")?.classList.add("open");
            }
        });
    })();

    // =============== 우측 상단 유저 메뉴 ===============
    (() => {
        const btn = document.getElementById("usermenubtn");
        const menu = document.getElementById("usermenu");
        if (!btn || !menu) return;

        const hide = () => {
            menu.classList.remove("show");
            menu.style.display = "none";
        };

        const toggle = () => {
            const willShow = !menu.classList.contains("show");
            menu.classList.toggle("show", willShow);
            menu.style.display = willShow ? "block" : "none";
        };

        btn.addEventListener("click", () => toggle());
        document.addEventListener("click", (e) => {
            if (!btn.contains(e.target) && !menu.contains(e.target)) hide();
        });
        document.addEventListener("keydown", (e) => {
            if (e.key === "Escape") hide();
        });
    })();

    // =============== 신고 모달 (상세/처리) ===============
    (() => {
        const modal = document.querySelector(".report-modal");
        if (!modal) return;

        const closeModal = () => {
            modal.classList.remove("show");
            document.body.classList.remove("modal-open");
            setTimeout(() => (modal.style.display = "none"), 120);
        };

        const bindText = (key, val) => {
            const el = modal.querySelector(`[data-bind="${key}"]`);
            if (el) el.textContent = val ?? "";
        };

        let currentRow = null;

        const openModalFromRow = (row) => {
            currentRow = row;

            const title = row.querySelector(".post-title")?.textContent.trim() ?? "-";
            const author = row.querySelector(".post-meta b")?.textContent.trim() ?? "-";
            const postIdText =
                row.querySelector(".post-meta .meta:last-child")?.textContent.trim() ?? "postId: -";
            const postId = postIdText.replace(/^postId:\s*/i, "") || "-";
            const reason = row.querySelector(".reason-badge")?.textContent.trim() ?? "-";
            const reporterName = row.querySelector("td:nth-child(3) b")?.textContent.trim() ?? "-";
            const reporterEmail = row.querySelector("td:nth-child(3) .text-muted")?.textContent.trim() ?? "-";
            const reportedAt = row.querySelector("td:nth-child(4)")?.textContent.trim() ?? "-";
            const badgeInRow = row.querySelector(".approval-status");

            bindText("title", title);
            bindText("author", author);
            bindText("postId", postId);
            bindText("reason", reason);
            bindText("reporterName", reporterName);
            bindText("reporterEmail", reporterEmail);
            bindText("reportedAt", reportedAt);

            const badgeInModal = modal.querySelector('[data-bind="statusBadge"]');
            if (badgeInModal) {
                badgeInModal.className = "status-badge";
                if (badgeInRow?.classList.contains("status-resolved")) {
                    badgeInModal.classList.add("status-resolved");
                    badgeInModal.textContent = "처리완료";
                } else if (badgeInRow?.classList.contains("status-rejected")) {
                    badgeInModal.classList.add("status-rejected");
                    badgeInModal.textContent = "반려";
                } else {
                    badgeInModal.classList.add("status-pending");
                    badgeInModal.textContent = "대기중";
                }
            }

            modal.style.display = "block";
            requestAnimationFrame(() => {
                modal.classList.add("show");
                modal.style.background = "rgba(0,0,0,.5)";
                document.body.classList.add("modal-open");
            });
        };

        document.addEventListener("click", (e) => {
            const btn = e.target.closest(".action-btn.view");
            if (!btn) return;
            const row = btn.closest("tr");
            if (!row) return;
            openModalFromRow(row);
        });

        const closeBtns = modal.querySelectorAll(".btn-close, .close");
        closeBtns.forEach((b) => b.addEventListener("click", closeModal));
        modal.addEventListener("click", (e) => { if (e.target === modal) closeModal(); });

        const approveBtn = modal.querySelector(".btn-approve");
        approveBtn &&
        approveBtn.addEventListener("click", async () => {
            if (!currentRow) return;

            const reportId = currentRow.dataset.reportId;
            const postIdText = currentRow.querySelector(".post-meta .meta:last-child")?.textContent.trim() ?? "";
            const postId = postIdText.replace(/^postId:\s*/i, "");
            const hidePost = modal.querySelector(".cb-hide-post")?.checked || false;

            const ok = await diaryReportService.processReport(reportId, postId, hidePost);
            if (!ok) {
                alert("신고 처리에 실패했습니다.");
                return;
            }

            const badge = currentRow.querySelector(".approval-status");
            if (badge) {
                badge.textContent = "처리완료";
                badge.classList.remove("status-pending", "status-rejected");
                badge.classList.add("status-resolved");
            }

            const badgeInModal = modal.querySelector('[data-bind="statusBadge"]');
            if (badgeInModal) {
                badgeInModal.className = "status-badge status-resolved";
                badgeInModal.textContent = "처리완료";
            }

            closeModal();
        });
    })();
};
