// 페이지 로드 후 실행
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

    // 나팔 버튼 → 공지 섹션 열기
    document.addEventListener('click', (e) => {
        const a = e.target.closest('[data-section="notice"]');
        if (!a) return;
        e.preventDefault();
        showSection('notice');
    });

    //  페이지네이션
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


// 모달 제어
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


// 저장
window.noticeService = window.noticeService || {};
window.noticeService.save = async (title, content) => {
    try {
        const res = await fetch('/api/admin/notices', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json', 'Accept': 'application/json' },
            credentials: 'same-origin',
            body: JSON.stringify({ title, content })
        });
        if (!res.ok) {
            console.error('공지 저장 실패:', await res.text());
            return false;
        }
        return true;
    } catch (err) {
        console.error('공지 저장 오류:', err);
        return false;
    }
};


// ️ 작성 버튼 → 모달 열기
document.addEventListener('click', (e) => {
    const btn = e.target.closest('#notice-save-btn');
    if (!btn) return;
    e.preventDefault();
    NoticeModal.reset();
    NoticeModal.show();
});

//  모달 닫기
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

//  완료(저장) 버튼
document.addEventListener('click', async (e) => {
    const submit = e.target.closest('#notice-modal-submit');
    if (!submit) return;

    const title = document.getElementById('notice-title')?.value.trim();
    const content = document.getElementById('notice-content')?.value.trim();
    if (!title || !content) {
        alert('제목과 내용을 입력해주세요.');
        return;
    }

    if (submit.disabled) return;
    submit.disabled = true;
    const prev = submit.textContent;
    submit.textContent = '저장 중...';

    try {
        const ok = await noticeService.save(title, content);
        if (ok) {
            NoticeModal.hide();
            noticeService.getList(noticeLayout.showList, 1);
        } else {
            alert('저장 실패');
        }
    } catch (err) {
        console.error(err);
        alert('저장 중 오류가 발생했습니다.');
    } finally {
        submit.disabled = false;
        submit.textContent = prev;
    }
});
