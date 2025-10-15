
const section = document.getElementById('section-gift-report');
const giftReport = section ? section.querySelector('.table-reports tbody') : null;

const giftReportLayout = (() => {
    const showReportGiftList = (reportGift = []) => {
        if (!giftReport) return;
        reportGift.forEach((gift) => {
            const tr = document.createElement('tr');
            tr.dataset.reportId = gift.reportId;
            tr.dataset.status = gift.processStatus || 'PENDING';

            const status = (gift.processStatus || '').toUpperCase();
            const statusClass = status === 'RESOLVED' ? 'status-resolved' : 'status-pending';
            const statusText  = status === 'RESOLVED' ? '처리완료' : '대기중';

            tr.innerHTML = `
        <td class="td-post">
          <div class="post-title">${gift.postTitle}</div>
          <div class="post-meta">
            <span class="meta">by <b>${gift.writerEmail}</b></span>
            <span class="dot">·</span>
            <span class="meta">postId: ${gift.postId}</span>
          </div>
        </td>
        <td class="text-center">
          <span class="badge badge-label reason-badge text-danger">${gift.reportContent}</span>
        </td>
        <td>
          <div>reporter: <b>${gift.reporterEmail ?? gift.reporterSocialEmail ?? '익명'}</b></div>
          <div class="text-muted">${gift.reporterEmail ?? gift.reporterSocialEmail ?? '-'}</div>
        </td>
        <td class="text-center text-muted">${(gift.createdDatetime || '').substring(0, 16)}</td>
        <td class="td-actions text-right">
          <span class="approval-status status-badge ${statusClass}">${statusText}</span>
          <button class="btn btn-light-danger btn-sm action-btn view" title="상세보기">
            <i class="mdi mdi-chevron-right"></i>
          </button>
        </td>
      `;
            giftReport.appendChild(tr);
        });
    };

    return { showReportGiftList };
})();
