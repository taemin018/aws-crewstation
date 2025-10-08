// DOM 렌더링만 담당
const memberLayout = (() => {
    const el = {
        tbody: () => document.getElementById("membersTbody"),
        memberCount: () =>
            document.getElementById("memberCount") ||
            document.querySelector(".count-amount"),
        pageWrap: () =>
            document.querySelector(".pagination.bootpay-pagination.member-pagination") ||
            document.querySelector(".pagination.bootpay-pagination"),
        keyword: () => document.getElementById("memberKeyword"),
        modal: () => document.querySelector(".member-modal"),
        // 상세 필드
        detailName: () => document.getElementById("memberDetailName"),
        detailPhone: () => document.getElementById("memberDetailPhone"),
        detailEmail: () => document.getElementById("memberDetailEmail"),
        detailStatus: () => document.getElementById("memberDetailStatus"),
        detailProvider: () => document.getElementById("memberDetailProvider"),
        detailCreated: () => document.getElementById("memberDetailCreatedDatetime"),
        detailId: () => document.getElementById("memberDetailId"),
        detailPost: () => document.getElementById("memberDetailPost"),
    };

    const openModal = () => {
        const modal = el.modal();
        if (!modal) return;
        modal.style.display = "block";
        requestAnimationFrame(() => {
            modal.classList.add("show");
            modal.style.background = "rgba(0,0,0,0.5)";
            document.body.classList.add("modal-open");
        });
    };

    const closeModal = () => {
        const modal = el.modal();
        if (!modal) return;
        modal.classList.remove("show");
        document.body.classList.remove("modal-open");
        setTimeout(() => (modal.style.display = "none"), 100);
    };

    const showMembers = async (result) => {
        const tbody = el.tbody();
        const memberCount = el.memberCount();
        const pageWrap = el.pageWrap();
        if (!tbody || !memberCount || !pageWrap) return;

        const { members = [], total = 0, criteria = {}, search = {} } = result;
        if (el.keyword()) el.keyword().value = search.keyword || "";
        memberCount.textContent = total;

        // rows
        let rows = "";
        if (!members.length) {
            rows = `
        <tr>
          <td class="text-center font-weight-bold" colspan="7">회원이 존재하지 않습니다</td>
        </tr>`;
        } else {
            members.forEach((m) => {
                const active = (m.memberStatus || "").toLowerCase() === "active";
                rows += `
          <tr>
            <td class="td-name" style="width:10%; text-align:center;">
              <div class="member-name">${m.id ?? ""}</div>
            </td>
            <td class="td-amount text-right pr-4 font-weight-bold" style="width:15%; text-align:center;">
              ${m.memberName ?? ""}<span class="amount-unit"> 님</span>
            </td>
            <td class="td-email" style="width:25%;">
              <span>${m.memberEmail ?? "없음"}</span><br/>
              <span>${m.memberSocialEmail ?? "없음"}</span>
            </td>
            <td class="td-phone" style="width:15%;">${m.memberPhone ?? ""}</td>
            <td class="td-start" style="width:15%;">${m.createdDatetime ?? ""}</td>
            <td class="td-recent" style="width:10%; color:${active ? "#507cf3" : "#fe657e"};">
              ${active ? "활동 중" : "탈퇴"}
            </td>
            <td class="td-action text-center" style="width:10%;">
              <button type="button" class="action-btn member-detail-btn" data-memberid="${m.id}">
                <i class="mdi mdi-chevron-right"></i>
              </button>
            </td>
          </tr>`;
            });
        }
        tbody.innerHTML = rows;

        // pagination
        const {
            startPage = 1,
            endPage = 1,
            page = 1,
            hasPreviousPage = false,
            hasNextPage = false,
        } = criteria;

        let pagers = "";
        if (hasPreviousPage) {
            pagers += `
        <li class="page-item page-num">
          <a class="paging" data-page="${startPage - 1}">이전</a>
        </li>`;
        }
        for (let p = startPage; p <= endPage; p++) {
            pagers += `
        <li class="page-item page-num ${p === page ? "active" : ""}">
          <a class="paging" data-page="${p}">${p}</a>
        </li>`;
        }
        if (hasNextPage) {
            pagers += `
        <li class="page-item page-num">
          <a class="paging" data-page="${endPage + 1}">다음</a>
        </li>`;
        }
        pageWrap.innerHTML = pagers;
    };

    const showMemberDetail = async (result) => {
        if (!result) return;

        const name = el.detailName();
        const phone = el.detailPhone();
        const email = el.detailEmail();
        const status = el.detailStatus();
        const provider = el.detailProvider();
        const created = el.detailCreated();
        const id = el.detailId();
        const postTbody = el.detailPost();

        name && (name.textContent = result.memberName ?? "");
        phone && (phone.textContent = result.memberPhone ?? "");
        email && (email.textContent = result.memberEmail ?? result.memberSocialEmail ?? "");
        if (status) {
            const active = (result.memberStatus || "").toLowerCase() === "active";
            status.textContent = active ? "활동 중" : "탈퇴";
            status.style.color = active ? "#507cf3" : "#fe657e";
        }
        provider && (provider.textContent = result.memberProvider ?? "");
        created && (created.textContent = result.createdDatetime ?? "");
        id && (id.textContent = result.id ?? "");

        if (postTbody) {
            const posts = result.consultationPosts || [];
            postTbody.innerHTML = posts.length
                ? posts
                    .map((p) => {
                        const inactive = (p.consultationPostStatus || "").toLowerCase() === "inactive";
                        return `
                <tr>
                  <td>${p.consultationPostTitle ?? ""}</td>
                  <td>${p.consultationPostAnswerCount ?? 0}</td>
                  <td style="color:${inactive ? "#507cf3" : "#fe657e"};">
                    ${inactive ? "Y" : "N"}
                  </td>
                  <td>${p.createdDate ?? ""}</td>
                </tr>`;
                    })
                    .join("")
                : `<tr><td class="text-light-grey text-center" colspan="4">작성한 상담글이 없습니다</td></tr>`;
        }
    };

    return { showMembers, showMemberDetail, openModal, closeModal };
})();
