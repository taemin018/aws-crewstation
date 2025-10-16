const paymentLayout = (() => {
    const n = (v) => Number(v ?? 0).toLocaleString('ko-KR');
    const safe = (v, def = '-') => (v === null || v === undefined || v === '') ? def : String(v);

    const getTbody = () => document.querySelector('#section-payment #payment-tbody');

    const clear = () => {
        const tb = getTbody();
        if (tb) tb.innerHTML = '';
    };

    const showEmpty = () => {
        const tb = getTbody();
        if (!tb) return;
        tb.innerHTML = `<tr><td colspan="7" class="text-center text-muted">조회된 결제가 없습니다.</td></tr>`;
    };

    const normalizeList = (raw) => {
        if (raw && Array.isArray(raw.content)) return raw.content;
        if (Array.isArray(raw)) return raw;
        return [];
    };

    const showPayments = (listRaw = []) => {
        const tb = getTbody();
        if (!tb) return;

        const list = normalizeList(listRaw);
        if (!list.length) {
            if (!tb.hasChildNodes()) showEmpty();
            return;
        }

        list.forEach((p) => {
            const tr = document.createElement('tr');
            tr.dataset.paymentId = p.id;

            const statusText = safe(p.statusText ?? p.paymentPhase);
            const timeText   = safe(p.paidAt ?? p.updatedDatetime);

            tr.innerHTML = `
        <td class="td-name"><div class="good-name">${safe(p.productName)}</div></td>
        <td class="td-amount text-right pr-4 font-weight-bold">
          ${n(p.amount)} <span class="amount-unit">원</span>
        </td>
        <td class="td-method">
          <div class="pq">토스페이</div>
        </td>
        <td class="td-status">
          <div class="label-form">
            <span class="badge-label text-nowrap text-dark approval-status">${statusText}</span>
          </div>
        </td>
        <td class="td-at text-center"><div class="date-at text-dark">${timeText}</div></td>
        <td class="td-buyer text-center text-dark">
          <div class="buyer-wrapper">
            <div class="user-name">${safe(p.buyerName)}</div>
          </div>
        </td>
        <td class="td-action text-center">
          <button type="button" class="action-btn view" 
          data-paymentid="${p.id}">
            <i class="mdi mdi-chevron-right"></i>
          </button>
        </td>
      `;
            tb.appendChild(tr);
        });

        const cnt = document.querySelector('#section-payment .receipt-count .count-amount');
        if (cnt) {
            const current = Number(cnt.textContent.replace(/\D/g, '') || 0);
            cnt.textContent = String(current + list.length);
        }
    };

    const showPaymentDetail = (detail = {}) => {
        const modal =
            document.querySelector('#section-payment .payment-modal') ||
            document.querySelector('#section-payment .member-modal');
        if (!modal) return;

        const set = (k, v) => {
            const el = modal.querySelector(`[data-bind="${k}"]`);
            if (el) el.textContent = safe(v);
        };

        set('productName', detail.productName);
        set('amount', detail.amount != null ? `${n(detail.amount)}원` : '-');
        set('buyerName', detail.buyerName);
        set('buyerPhone', detail.buyerPhone);
        set('buyerEmail', detail.buyerEmail);
        set('status', detail.statusText ?? detail.paymentPhase);
        set('paidAt', detail.paidAt ?? detail.createdDatetime ?? detail.updatedDatetime);

        set('sellerName', detail.sellerName);
        set('sellerPhone', detail.sellerPhone);
        set('sellerEmail', detail.sellerEmail);
        set('listedAt', detail.listedAtText ?? detail.listedAt);
        set('deliveryType', detail.deliveryTypeText ?? detail.deliveryType);
        set('address', detail.address);
    };

    return { clear, showEmpty, showPayments, showPaymentDetail };
})();
