

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
        // 서브링크 .active 가 있는 패널들은 펼친다
        subLists.forEach((ul) => {
            const hasActiveChild = !!ul.querySelector(".boot-link.active");
            const markedShow = ul.classList.contains("show");
            if (hasActiveChild || markedShow) {
                ul.classList.add("show");
                ul.style.display = "block";
                const btn = ul.previousElementSibling; // 상위 메뉴 버튼
                const li = ul.closest("li");
                btn && btn.classList.add("active", "current");
                li && li.classList.add("open");
            }
        });

        //  최상위 버튼이 .active 라면 그 다음 패널도 열어준다
        side.querySelectorAll(".menu-item > .menu-btn.active").forEach(
            (btn) => {
                const panel = btn.nextElementSibling;
                if (panel && panel.classList.contains("menu-sub-list")) {
                    panel.classList.add("show");
                    panel.style.display = "block";
                    btn.classList.add("current");
                    btn.closest("li")?.classList.add("open");
                }
            }
        );
    };

    // 초기 처리: active/show 가 하나라도 있으면 그 상태를 살리고,
    // 없으면(아무 지정도 없으면) 전체 닫기
    const hasExplicit = !!side.querySelector(
        ".menu-btn.active, .menu-btn.current, .menu-sub-list.show, .menu-sub-list .boot-link.active"
    );

    if (hasExplicit) {
        syncFromDOM();
    } else {
        closeAllMenus();
    }

    // 이하 클릭 위임 로직은 그대로 유지
    side.addEventListener("click", (e) => {
        const subLink = e.target.closest(".menu-sub-list .boot-link");
        if (subLink && side.contains(subLink)) {
            e.preventDefault();
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
        e.preventDefault();

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
    const initUserMenu = () => {
        const btn = document.getElementById("userMenuBtn");
        const menu = document.getElementById("userMenu");
        if (!btn || !menu) return;

        const hide = () => {
            menu.classList.remove("show");
            menu.style.display = "none";
            btn.setAttribute("aria-expanded", "false");
        };

        const toggle = () => {
            const willShow = !menu.classList.contains("show");
            menu.classList.toggle("show", willShow);
            menu.style.display = willShow ? "block" : "none";
            btn.setAttribute("aria-expanded", String(willShow));
        };

        btn.addEventListener("click", (e) => {
            e.preventDefault();
            e.stopPropagation(); // 문서 클릭 핸들러보다 우선
            toggle();
        });

        document.addEventListener("click", (e) => {
            if (!btn.contains(e.target) && !menu.contains(e.target)) hide();
        });

        document.addEventListener("keydown", (e) => {
            if (e.key === "Escape") hide();
        });
    };

    // DOM 준비 후 실행
    if (document.readyState === "loading") {
        document.addEventListener("DOMContentLoaded", initUserMenu);
    } else {
        initUserMenu();
    }
})();



// ===== 모달 열기/닫기 =====
(() => {
    const modal = document.querySelector(".member-modal");
    if (!modal) return;

    const closeBtns = modal.querySelectorAll(".close, .btn-close");
    const table = document.querySelector(".table-layout");
    const dialog = modal.querySelector(".modal-dialog");
    if (dialog) dialog.style.margin = "1.75rem auto";

    const openModal = () => {
        modal.style.display = "block";
        requestAnimationFrame(() => {
            modal.classList.add("show");
            modal.style.background = "rgba(0,0,0,0.5)";
            document.body.classList.add("modal-open");
        });
    };

    const closeModal = () => {
        modal.classList.remove("show");
        document.body.classList.remove("modal-open");
        setTimeout(() => { modal.style.display = "none"; }, 100);
    };

    if (table) {
        table.addEventListener("click", async (e) => {
            const btn = e.target.closest(".member-detail-btn, .action-btn");
            if (!btn) return;

            const memberId = btn.dataset.memberid;  // data-memberid 필수
            if (!memberId) return;

            const loading = document.getElementById("loading");
            if (loading) loading.style.display = "block";
            try {
                await memberService.getDetailMember(memberLayout.showMemberDetail, memberId);
                openModal();
            } catch (err) {
                console.error("회원 상세 조회 실패:", err);
            } finally {
                if (loading) loading.style.display = "none";
            }
        });
    }

    closeBtns.forEach((b) => b.addEventListener("click", closeModal));
    modal.addEventListener("click", (e) => { if (e.target === modal) closeModal(); });
    document.addEventListener("keydown", (e) => {
        if (e.key === "Escape" && modal.classList.contains("show")) closeModal();
    });
})();


const memberMenuBtn = document.getElementById("memberMenu");
const paginationMember = document.querySelector(".pagination.bootpay-pagination.member-pagination");
const memberKeywordInput = document.getElementById("memberKeyword");
const memberKeywordBtn = document.getElementById("memberKeywordBtn");

const loading = document.getElementById("loading");

const showMembers = async (page = 1, keyword = "") => {
    if (loading) loading.style.display = "block";
    const res = await memberService.getMembers(memberLayout.showMembers, page, keyword);
    if (loading) loading.style.display = "none";
    return res;
};


if (memberKeywordInput) {
    memberKeywordInput.addEventListener("keydown", async (e) => {
        if (e.key === "Enter") {
            await showMembers(1, memberKeywordInput.value.trim());
        }
    });
}

if (memberKeywordBtn) {
    memberKeywordBtn.addEventListener("click", async () => {
        await showMembers(1, memberKeywordInput?.value.trim() || "");
    });
}

if (memberMenuBtn) {
    memberMenuBtn.addEventListener("click", async (e) => {
        e.preventDefault();
        await showMembers(1, "");
    });
}



// 페이징 클릭
document.addEventListener("click", async (e) => {
    const a = e.target.closest(".pagination.bootpay-pagination a.paging");
    if (!a) return;

    e.preventDefault();

    const page = Number(a.dataset.page || 1);
    const keyword = (memberKeywordInput?.value || "").trim();

    await showMembers(page, keyword);
});


//  구글 차트
google.charts.load('current', { packages: ['corechart', 'bar'] });

window.addEventListener('DOMContentLoaded', async () => {
    const staticsData = await mainService.getMain(mainLayout.showMain);

    // 구글 차트가 로드되면 실행
    google.charts.setOnLoadCallback(() => {
        drawJoinChart(staticsData);
        drawPie(staticsData);
    });
});

//  join 차트
function drawJoinChart(staticsData) {
    const el = document.getElementById('join_chart');
    if (!el) return;

    const dataArray = [['월', '가입자 수']];
    if (Array.isArray(staticsData?.monthlyJoins)) {
        staticsData.monthlyJoins.forEach(m => {
            dataArray.push([m.date, Number(m.count)]);
        });
    }

    const data = google.visualization.arrayToDataTable(dataArray);
    const options = {
        title: '최근 3개월 가입자 수',
        vAxis: { minValue: 0 },
        legend: { position: 'none' },
        colors: ['#3366cc'],
        chartArea: { width: '85%', height: '70%' },
    };

    const chart = new google.visualization.ColumnChart(el);
    chart.draw(data, options);
}

//  인기 여행지 파이차트
function drawPie(staticsData) {
    const el = document.getElementById('piechart');
    if (!el) return;

    const dataArray = [['지역', '비율']];
    if (Array.isArray(staticsData?.popularCountries)) {
        staticsData.popularCountries.forEach(c => {
            dataArray.push([c.country, Number(c.count)]);
        });
    }

    const data = google.visualization.arrayToDataTable(dataArray);
    const options = {
        title: '인기 여행지 TOP 5',
        pieHole: 0.4,
        legend: { position: 'bottom' },
        chartArea: { width: '90%', height: '80%' },
    };

    const chart = new google.visualization.PieChart(el);
    chart.draw(data, options);
}


document.addEventListener("DOMContentLoaded", () => {
    const menuButtons = document.querySelectorAll(".menu-btn[data-section]");
    const sections = document.querySelectorAll("#page-container > div");

    menuButtons.forEach((btn) => {
        btn.addEventListener("click", () => {
            const target = btn.getAttribute("data-section");

            menuButtons.forEach((b) => b.classList.remove("active"));
            btn.classList.add("active");

            // 해당 섹션만 보이기
            sections.forEach((sec) => {
                if (sec.id === `section-${target}`) {
                    sec.style.display = "block";
                } else {
                    sec.style.display = "none";
                }
            });
        });
    });

    sections.forEach((sec) => {
        sec.style.display = sec.id === "section-home" ? "block" : "none";
    });
});

// ===== 로그인 정보 표시 + 로그아웃 =====

async function fetchWithRefresh(url, opts = {}) {
    let res = await fetch(url, { credentials: 'include', ...opts });
    if (res.status === 401) {
        const r = await fetch('/api/admin/auth/refresh', { method: 'GET', credentials: 'include' });
        if (r.ok) res = await fetch(url, { credentials: 'include', ...opts });
    }
    return res;
}

(async () => {
    try {
        const res = await fetchWithRefresh('/api/admin/auth/info');
        if (!res.ok) throw new Error('unauthorized');

        const me = await res.json();

        const emailEl = document.querySelector('.user-menu-email');
        if (emailEl) emailEl.textContent = me.memberEmail || '관리자';

        const avatarEl = document.querySelector('.user-avatar');
        if (avatarEl) {
            const letter = (me.memberEmail || 'C').trim().charAt(0).toUpperCase();
            avatarEl.textContent = letter || 'C';
        }
    } catch (e) {
        // 토큰 없거나 만료면 로그인 페이지로
        window.location.href = '/admin/login';
    }
})();

// 2) 로그아웃 클릭 처리
(function attachLogout() {
    let logoutLink = document.getElementById('logout-link');

    logoutLink.addEventListener('click', async (e) => {
        e.preventDefault();
        try {
            await fetch('/api/admin/auth/logout', { method: 'POST', credentials: 'include' });
        } finally {
            window.location.href = '/admin/login';
        }
    });
})();


