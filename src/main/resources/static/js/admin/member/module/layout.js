const memberLayout = (() => {
    const el = {
        tbody: () => document.getElementById("membersTbody"),
        memberCount: () => document.getElementById("memberCount") || document.querySelector(".count-amount"),
        pageWrap: () => document.querySelector(".pagination.bootpay-pagination.member-pagination") || document.querySelector(".pagination.bootpay-pagination"),
        keyword: () => document.getElementById("memberKeyword"),
        modal: () => document.querySelector(".member-modal"),

        detailTitle:   () => document.getElementById("memberModalTitle"),
        detailName:    () => document.getElementById("memberDetailName"),
        detailPhone:   () => document.getElementById("memberDetailPhone"),
        detailEmail:   () => document.getElementById("memberDetailEmail"),
        detailStatus:  () => document.getElementById("memberDetailStatus"),
        detailProvider:() => document.getElementById("memberDetailProvider"),
        detailCreated: () => document.getElementById("memberDetailCreatedDatetime"),
        detailId:      () => document.getElementById("memberDetailId"),
        detailPost:    () => document.getElementById("memberPostTbody"),
        detailComment: () => document.getElementById("memberCommentTbody"),
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

    const showMembers = (result) => {
        const tbody = el.tbody();
        const memberCount = el.memberCount();
        const pageWrap = el.pageWrap();
        if (!tbody || !memberCount || !pageWrap) return;

        const { members = [], total = 0, criteria = {}, search = {} } = result;
        if (el.keyword()) el.keyword().value = search.keyword || "";
        memberCount.textContent = total;

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

        // --- 페이징 계산: total/amount 로 안전 계산 ---
        const pageSize   = Number(criteria.amount ?? criteria.pageSize ?? 10);
        const current    = Number(criteria.page ?? 1);
        const totalPages = Math.max(1, Math.ceil(Number(total) / pageSize));

// 총 1페이지면 페이징 감추기
        if (totalPages === 1) {
            pageWrap.innerHTML = "";
        } else {
            const blockSize = 5;
            const blockStart = Math.floor((current - 1) / blockSize) * blockSize + 1;
            const blockEnd   = Math.min(blockStart + blockSize - 1, totalPages);

            let pagers = "";
            if (blockStart > 1) {
                pagers += `
      <li class="page-item page-num">
        <a class="paging" data-page="${blockStart - 1}">이전</a>
      </li>`;
            }
            for (let p = blockStart; p <= blockEnd; p++) {
                pagers += `
      <li class="page-item page-num ${p === current ? "active" : ""}">
        <a class="paging" data-page="${p}">${p}</a>
      </li>`;
            }
            if (blockEnd < totalPages) {
                pagers += `
      <li class="page-item page-num">
        <a class="paging" data-page="${blockEnd + 1}">다음</a>
      </li>`;
            }
            pageWrap.innerHTML = pagers;
        }

    };

    const showMemberDetail = (result) => {
        if (!result) return;

        // 타이틀
        const titleEl = el.detailTitle();
        if (titleEl) {
            const loginId = result.memberLoginId || result.loginId || result.username || "";
            const name    = result.memberName    || result.name    || "";
            titleEl.textContent = loginId ? `(${loginId}) ${name}` : name;
        }


        // 기본정보
        const nameEl = el.detailName();
        if (nameEl) nameEl.textContent = result.memberName ?? "";

        const phoneEl = el.detailPhone();
        if (phoneEl) phoneEl.textContent = result.memberPhone ?? "";

        const emailEl = el.detailEmail();
        if (emailEl) emailEl.textContent = result.memberEmail ?? result.memberSocialEmail ?? "";

        const providerEl = el.detailProvider();
        if (providerEl) providerEl.textContent = result.memberProvider ?? result.provider ?? "";

        const createdEl = el.detailCreated();
        if (createdEl) createdEl.textContent = result.createdDatetime ?? result.createdAt ?? "";

        const idEl = el.detailId();
        if (idEl) idEl.textContent = (result.id ?? result.memberId ?? "") + "";

        const statusEl = el.detailStatus?.();
        if (statusEl) {
            const active = (result.memberStatus || result.status || "").toLowerCase() === "active";
            statusEl.textContent = active ? "활동 중" : "탈퇴";
            statusEl.style.color = active ? "#507cf3" : "#fe657e";
        }

        // 작성 게시글
        const postTbody = el.detailPost();
        if (postTbody) {
            const posts =
                result.consultationPosts ||
                result.posts ||
                result.memberPosts ||
                [];

            postTbody.innerHTML = posts.length
                ? posts.map(p => `
            <tr>
              <td>${p.consultationPostTitle ?? p.title ?? ""}</td>
              <td>${p.consultationPostContent ?? p.content ?? ""}</td>
              <td>${p.createdDate ?? p.createdAt ?? ""}</td>
            </tr>
          `).join("")
                : `<tr><td class="text-light-grey text-center" colspan="3">작성한 게시글이 없습니다</td></tr>`;
        }

        // 작성 댓글
        const commentTbody = el.detailComment();
        if (commentTbody) {
            const comments =
                result.consultationComments ||
                result.comments ||
                result.memberComments ||
                [];

            commentTbody.innerHTML = comments.length
                ? comments.map(c => `
            <tr>
              <td>${c.postTitle ?? c.consultationPostTitle ?? ""}</td>
              <td>${c.content ?? c.commentContent ?? ""}</td>
              <td>${c.createdDate ?? c.createdAt ?? ""}</td>
            </tr>
          `).join("")
                : `<tr><td class="text-light-grey text-center" colspan="3">작성한 댓글이 없습니다</td></tr>`;
        }
    };

    return { showMembers, showMemberDetail, openModal, closeModal };
})();
