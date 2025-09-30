const likeLayout = (() => {

    // 개별 카드 반복
    const renderDiaryCard = (diary) => {
        return `
        <div class="card-feed-item-wrap">
            <article class="card-collection-item">
                <div class="card-writer">
                    <div class="card-item-writer-content">
                        <div class="card-item-writer-header">
                            <a class="card-writer-link">
                                <img src="${diary.memberProfileImage || '/images/crew-station-icon-profile.png'}" 
                                     alt="" class="card-writer-img">
                                <span class="writer-name">${diary.memberName}</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="card-collection-item-content">
                    <a class="card-content-link"></a>
                    <div class="card-item-description">
                        <div class="card-item-description-content">
                            <span class="card-item-detail-content">
                                ${diary.content}
                            </span>
                        </div>
                    </div>
                    ${diary.mainImage ? `
                    <div class="card-collection-item-img">
                        <div class="card-item-img">
                            <img src="${diary.mainImage}" alt="" class="image">
                        </div>
                    </div>` : ""}
                    <div class="card-item-action-list">
                        <button class="card-item-action-btn like-btn" data-id="${diary.postId}">
                            <svg class="icon icon--stroke" aria-label="좋아요" width="24" height="24" 
                                fill="currentColor" stroke="currentColor" stroke-width="2" 
                                viewBox="0 0 24 24">
                                <path d="M23.22 7.95c.4 4.94-2.92 9.71-10.92 
                                13.85a.47.47 0 0 1-.42 0C3.88 17.66.56 
                                12.9.96 7.93 1.54 2.48 8.28.3 12.1 
                                4.7c3.8-4.4 10.55-2.22 11.13 3.25z"></path>
                            </svg>
                            <span class="count">${diary.diaryLikeCount}</span>
                        </button>
                        <button class="card-item-action-btn comment-btn" data-id="${diary.postId}">
                            <svg class="icon icon-stroke" aria-label="댓글 달기" width="24" height="24" 
                                viewBox="0 0 24 24">
                                <path fill="currentColor" fill-rule="nonzero" 
                                d="M13.665 18.434l.53-.066C19.69 
                                17.679 23 14.348 23 10c0-4.942-4.235-8.5-11-8.5S1 
                                5.058 1 10c0 4.348 3.31 7.68 8.804 
                                8.368l.531.066L12 21.764l1.665-3.33z"></path>
                            </svg>
                            <span class="count">${diary.diaryReplyCount}</span>
                        </button>
                    </div>
                </div>
            </article>
        </div>
        `;
    };

    // 전체 리스트 렌더링 (첫 페이지)
    const renderDiaryList = (container, diaries) => {
        if (!diaries || diaries.length === 0) {
            container.innerHTML = `<p class="empty-message">좋아요한 일기가 없습니다.</p>`;
            return;
        }
        container.innerHTML = `
            <div class="diary-card-feed">
                ${diaries.map(renderDiaryCard).join("")}
            </div>
        `;
    };

    // 추가 리스트 렌더링 (무한스크롤용)
    const appendDiaryList = (container, diaries) => {
        if (!diaries || diaries.length === 0) return;
        const feed = container.querySelector(".diary-card-feed");
        feed.insertAdjacentHTML("beforeend", diaries.map(renderDiaryCard).join(""));
    };

    return {
        renderDiaryList : renderDiaryList,
        appendDiaryList : appendDiaryList
    };

})();
