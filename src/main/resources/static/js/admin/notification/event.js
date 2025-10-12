document.addEventListener('DOMContentLoaded', () => {
    const loadNotices = (page = 1) =>
        noticeService.getList(noticeLayout.showList, page);

    const showSection = (name) => {
        document.querySelectorAll('#page-container > *')
            .forEach(el => el.style.display = 'none');

        const target = document.getElementById(`section-${name}`);
        if (target) target.style.display = 'block';

        if (name === 'notice') loadNotices(1);
    };

    // 나팔 버튼 클릭
    document.addEventListener('click', (e) => {
        const a = e.target.closest('[data-section="notice"]');
        if (!a) return;
        e.preventDefault();
        showSection('notice');
    });

    // 페이지네이션
    document.addEventListener('click', (e) => {
        const li = e.target.closest('.notice-pagination li, .notice-pagination a.paging');
        if (!li) return;
        e.preventDefault();
        const page = parseInt(li.dataset.page || '1', 10);
        loadNotices(page);
    });

    if (location.hash === '#notice') showSection('notice');
    window.addEventListener('hashchange', () => {
        if (location.hash === '#notice') showSection('notice');
    });
});

const NoticeModal = (() => {
    const el = () => document.getElementById('notice-modal');
    const show = () => {
        const m = el(); if (!m) return;
        m.style.display = 'block';
        m.classList.add('show');
        m.setAttribute('aria-modal', 'true');
        m.removeAttribute('aria-hidden');

        if (!document.getElementById('notice-modal-backdrop')) {
            const bd = document.createElement('div');
            bd.id = 'notice-modal-backdrop';
            bd.className = 'modal-backdrop fade show';
            document.body.appendChild(bd);
        }
    };
    const hide = () => {
        const m = el(); if (!m) return;
        m.classList.remove('show');
        m.style.display = 'none';
        m.removeAttribute('aria-modal');
        m.setAttribute('aria-hidden', 'true');
        document.getElementById('notice-modal-backdrop')?.remove();
    };
    const reset = () => {
        const t = document.getElementById('notice-title');
        const c = document.getElementById('notice-content');
        if (t) t.value = '';
        if (c) c.value = '';
    };
    return { show, hide, reset };
})();

window.noticeService = window.noticeService || {};
if (!window.noticeService.save) {
    window.noticeService.save = async (payload) => {
        const res = await fetch('/api/admin/notices', {
            method: 'POST',
            headers: {'Content-Type':'application/json'},
            body: JSON.stringify(payload)
        });
        if (!res.ok) throw new Error((await res.text()) || '공지 저장 실패');
        return res.json();
    };
}

    // 작성 버튼
    document.addEventListener('click', (e) => {
        const btn = e.target.closest('#notice-save-btn');
        if (!btn) return;
        e.preventDefault();
        NoticeModal.reset();
        NoticeModal.show();
    });

    // 모달 닫기
    document.addEventListener('click', (e) => {
        if (e.target.closest('#notice-modal-close')) {
            e.preventDefault();
            NoticeModal.hide();
        }
        const modal = document.getElementById('notice-modal');
        if (modal?.classList.contains('show') && e.target === modal) {
            NoticeModal.hide();
        }
    });

    //  완료 버튼
    document.addEventListener('click', async (e) => {
        const submit = e.target.closest('#notice-modal-submit');
        if (!submit) return;

        const title = document.getElementById('notice-title')?.value.trim() || '';
        const content = document.getElementById('notice-content')?.value.trim() || '';
        if (!title || !content) {
            alert('제목과 내용을 입력해주세요.');
            return;
        }

        submit.disabled = true;
        const prevText = submit.textContent;
        submit.textContent = '저장 중...';

        try {
            await noticeService.save({ title, content });
            NoticeModal.hide();
            noticeService.getList(noticeLayout.showList, 1);
        } catch (err) {
            console.error(err);
            alert(err.message || '저장에 실패했습니다.');
        } finally {
            submit.disabled = false;
            submit.textContent = prevText;
        }
    });
