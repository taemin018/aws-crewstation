// ===================== Reply Layout =====================
const replyLayout = (() => {
    // 개별 카드 반복
    const renderReplyCard = (diary) => {
        return `
        <div class="inquiry-wrap" data-id="${diary.postId}">
          <img src="${diary.mainImage || '/static/images/diary-list-ex2.jpg'}" alt="">
          <article class="inquiry">
            <h1 class="inquiry-content">
              <div class="inquiry-title-text">${diary.postTitle || "제목 없음"}</div>
            </h1>
            <div class="inquiry-content-text">
              ${diary.content || ""}
            </div>
            <div class="reply-content active">
              <div class="answer">
                <div class="reply-icon"></div>
                <div class="answer-container">${diary.replyContent ? `${diary.replyContent}` : "댓글 없음"}</div>
                <span class="writer">${diary.relativeDatetime || ""}</span>
              </div>
            </div>
          </article>
        </div>
        `;
    };

    // 전체 리스트 렌더링 (첫 페이지)
    const renderReplyList = (container, diaries) => {
        if (!diaries || diaries.length === 0) {
            container.innerHTML = `<p class="empty-message">댓글 단 다이어리가 없습니다.</p>`;
            return;
        }
        container.innerHTML = `
            <section class="inquiry-container">
                ${diaries.map(renderReplyCard).join("")}
            </section>
        `;
    };

    // 추가 리스트 렌더링 (무한스크롤용)
    const appendReplyList = (container, diaries) => {
        if (!diaries || diaries.length === 0) return;
        const feed = container.querySelector(".inquiry-container");
        feed.insertAdjacentHTML("beforeend", diaries.map(renderReplyCard).join(""));
    };

    return {
        renderReplyList : renderReplyList,
        appendReplyList : appendReplyList,
    };
})();
