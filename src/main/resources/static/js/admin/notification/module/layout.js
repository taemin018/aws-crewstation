const noticeLayout = (() => {
    const showList = (result = {}) => {
        const notices    = result.noticeListAdmin || [];
        const listEl     = document.getElementById('noticeList');
        const pageWrap   = document.querySelector('.notice-pagination');
        const criteria   = result.criteria || {};

        if (!listEl) return;

        // 목록
        if (Array.isArray(notices) && notices.length) {
            listEl.innerHTML = notices.map(n => {
                const id      = n.id ?? '';
                const date    = n.createdMd ?? '';
                const title   = n.title ?? '(제목 없음)';
                const writer  = n.writerName ?? 'CrewStation';
                return `
          <div class="notice-item" data-id="${id}">
            <div class="notice-date">${date}</div>
            <div class="notice-title">${title}</div>
            <div class="notice-writer">${writer}</div>
          </div>
        `;
            }).join('');
        } else {
            listEl.innerHTML = `<div class="empty">조회된 공지가 없습니다.</div>`;
        }

        // 페이지네이션
        if (pageWrap) {
            let p = '';
            const start = criteria.startPage ?? 1;
            const end   = criteria.endPage ?? 1;
            const cur   = criteria.page ?? 1;

            for (let i = start; i <= end; i++) {
                p += `<li data-page="${i}"
                  style="cursor:pointer;${i===cur?'font-weight:bold;':''}">${i}</li>`;
            }
            pageWrap.innerHTML = p;
        }
    };

    return { showList };
})();
