window.paymentInit = async function () {
    if (window.paymentInited) return;
    window.paymentInited = true;
    const section = document.getElementById('section-payment');
    if (!section) return;

    const modal =
        section.querySelector('.payment-modal') ||
        section.querySelector('.member-modal') ||
        document.querySelector('#section-payment .payment-modal') ||
        document.querySelector('#section-payment .member-modal') ||
        document.querySelector('.payment-modal') ||
        document.querySelector('.member-modal');

    if (!modal) {
        console.warn('[payment] modal element not found (모달 바인딩은 스킵)');
    }

    let page = 1;
    let hasMore = true;
    let isLoading = false;

    const loadList = async (p = 1) => {
        if (isLoading) return;
        isLoading = true;
        try {
            const list = await paymentService.getPayments(p); // /api/admin/payment?page=p
            console.log('[payment] list len:', Array.isArray(list) ? list.length : (list?.content?.length || 0));
            paymentLayout.showPayments(list);

            const arr = Array.isArray(list) ? list : (list?.content || []);
            hasMore = Array.isArray(arr) && arr.length > 0;
        } catch (e) {
            console.error('결제 목록 로드 실패:', e);
        } finally {
            isLoading = false;
        }
    };

    // ===== 결제상태 선택 드롭다운 =====
        const wrap = section.querySelector('#filter-status');
        if (!wrap) return;

        const trigger  = wrap.querySelector('#btn-filter-status');
        const pop      = wrap.querySelector('.bt-pop-menu');
        const context  = pop?.querySelector('.bt-pop-menu-context');
        const backdrop = pop?.querySelector('.bt-pop-menu-back');
        if (!trigger || !pop) return;

        if (!document.getElementById('payment-filter-style')) {
            const style = document.createElement('style');
            style.id = 'payment-filter-style';
            style.textContent = `
      .boot-check-box.checked i.mdi-check { display: inline-block !important; }
      .boot-check-box:not(.checked) i.mdi-check { display: none !important; }
    `;
            document.head.appendChild(style);
        }

        pop.style.position = 'absolute';
        pop.style.zIndex   = '2000';
        pop.style.display  = 'none';
        if (context)  context.style.display  = 'block';
        if (backdrop) backdrop.style.display = 'none';

        const show = () => {
            pop.style.display = 'block';
            pop.classList.add('show');
            context?.classList.add('show');
            if (backdrop) {
                backdrop.classList.add('show');
                backdrop.style.display = 'block';
            }

            trigger.setAttribute('aria-expanded', 'true');
        };
        const hide = () => {
            pop.classList.remove('show');
            context?.classList.remove('show');
            if (backdrop) {
                backdrop.classList.remove('show');
                backdrop.style.display = 'none';
            }

            pop.style.display = 'none';
            trigger.setAttribute('aria-expanded', 'false');
        };

        const toggle = () => (pop.style.display === 'none' ? show() : hide());

        trigger.addEventListener('click', (e) => {
            e.preventDefault();
            e.stopPropagation();
            toggle();
        });

        pop.addEventListener('click', (e) => { e.stopPropagation(); });
        document.addEventListener('click', (e) => { if (!wrap.contains(e.target)) hide(); }, true);
        document.addEventListener('keydown', (e) => { if (e.key === 'Escape') hide(); });

        const listRoot = context || pop;
        listRoot.querySelectorAll('.boot-check').forEach(item => {
            const box  = item.querySelector('.boot-check-box');
            if (!box) return;
            const isChecked = box.classList.contains('checked') || item.dataset.checked === '1';
            box.classList.toggle('checked', isChecked);
            item.dataset.checked = isChecked ? '1' : '0';
            item.setAttribute('aria-checked', isChecked ? 'true' : 'false');
        });

        listRoot.addEventListener('click', (e) => {
            const item = e.target.closest('.boot-check');
            if (!item || !listRoot.contains(item)) return;

            const box = item.querySelector('.boot-check-box');
            if (!box) return;

            const isChecked = !box.classList.contains('checked');
            box.classList.toggle('checked', isChecked);
            item.dataset.checked = isChecked ? '1' : '0';
            item.setAttribute('aria-checked', isChecked ? 'true' : 'false');
        });

        //  전체선택 / 선택취소
        const btnSelectAll   = wrap.querySelector('#btn-select-all');
        const btnDeselectAll = wrap.querySelector('#btn-deselect-all');

        const setAll = (checked) => {
            listRoot.querySelectorAll('.boot-check').forEach(item => {
                const box = item.querySelector('.boot-check-box');
                if (!box) return;
                box.classList.toggle('checked', checked);
                item.dataset.checked = checked ? '1' : '0';
                item.setAttribute('aria-checked', checked ? 'true' : 'false');
            });
        };

        if (btnSelectAll)   btnSelectAll.addEventListener('click', (e) => {
            e.preventDefault();
            setAll(true);
        });

        if (btnDeselectAll) btnDeselectAll.addEventListener('click', (e) => { e.preventDefault(); setAll(false); });


    paymentLayout.clear();
    const cnt = section.querySelector('.receipt-count .count-amount');
    if (cnt) cnt.textContent = '0';
    await loadList(page);

    // 무한 스크롤
    const container = document.querySelector('#bootpay-main');
    const onScroll = async (scroller) => {
        const scrollTop = scroller === window ? window.scrollY : scroller.scrollTop;
        const clientHeight = scroller === window ? window.innerHeight : scroller.clientHeight;
        const scrollHeight = scroller === window ? document.documentElement.scrollHeight : scroller.scrollHeight;

        if (scrollTop + clientHeight >= scrollHeight - 120) {
            if (!isLoading && hasMore) {
                await loadList(++page);
            }
        }
    };

    if (container && !container.paymentScrollBound) {
        container.paymentScrollBound = true;
        container.addEventListener('scroll', () => onScroll(container));
    } else if (!window.paymentScrollBound) {
        window.paymentScrollBound = true;
        window.addEventListener('scroll', () => onScroll(window));
    }

    // 모달
    if (modal) {
        let currentRow = null;

        const open = () => {
            modal.style.display = 'block';
            requestAnimationFrame(() => {
                modal.classList.add('show');
                modal.style.background = 'rgba(0,0,0,.5)';
                document.body.classList.add('modal-open');
            });
        };

        const close = () => {
            modal.classList.remove('show');
            document.body.classList.remove('modal-open');
            setTimeout(() => { modal.style.display = 'none'; }, 100);
            delete modal.dataset.paymentId;
            if (currentRow) {
                currentRow.classList.remove('current');
                currentRow = null;
            }
        };

        modal.querySelectorAll('.btn-close, .close').forEach(b => b.addEventListener('click', close));
        modal.addEventListener('click', (e) => { if (e.target === modal) close(); });
        document.addEventListener('keydown', (e) => { if (e.key === 'Escape' && modal.classList.contains('show')) close(); });

        const table = section.querySelector('.table-layout');
        if (table && !table.__paymentRowBound) {
            table.__paymentRowBound = true;

            table.addEventListener('click', async (e) => {
                const btn = e.target.closest('.action-btn');
                if (!btn) return;

                const row = btn.closest('tr');
                const id = btn.dataset.paymentid || row?.dataset.paymentId;
                if (!id) return;

                try {
                    const detail = await paymentService.getDetail(id);
                    paymentLayout.showPaymentDetail(detail);

                    modal.dataset.paymentId = id;

                    if (currentRow) currentRow.classList.remove('current');
                    if (row) {
                        row.classList.add('current');
                        currentRow = row;
                    }

                    open();
                } catch (err) {
                    console.error('결제 상세 조회 실패:', err);
                    alert('상세 조회에 실패했습니다.');
                }
            });
        }

        const approveBtn = modal.querySelector('.btn-approve');
        const cancelBtn  = modal.querySelector('.btn-cancel');
        const getCurrentId = () =>
            modal.dataset.paymentId ||
            section.querySelector('tr[data-payment-id].current')?.dataset.paymentId;

        if (approveBtn) {
            approveBtn.addEventListener('click', async () => {
                const id = getCurrentId();
                if (!id) return;
                const ok = await paymentService.processPayment(id, 'approve');
                if (ok) close(); else alert('승인 처리 실패');
            });
        }

        if (cancelBtn) {
            cancelBtn.addEventListener('click', async () => {
                const id = getCurrentId();
                if (!id) return;
                const ok = await paymentService.processPayment(id, 'cancel');
                if (ok) close(); else alert('취소 처리 실패');
            });
        }
    }
};

window.addEventListener('DOMContentLoaded', () => {
    if (document.getElementById('section-payment') && typeof window.paymentInit === 'function') {
        window.paymentInit();
    }
});
