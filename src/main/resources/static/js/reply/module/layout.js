const replyLayout = (() => {
    const showList = (repliesCriteria) => {
        const replyWrap = document.getElementById("replyWrap");
        const replyPageWrap = document.getElementById("replyPageWrap");
        let text = ``;
        let checkUser = ``;
        let src = ``;
        repliesCriteria.replies.forEach((reply) => {
            if(reply.filePath){
                src = reply.filePath;
            }else if(reply.socialImgUrl){
                src=reply.socialImgUrl;
            }else{
                src= "/images/lock.png"
            }
            if(reply.writer){
                checkUser =`
                <div class="report-container">
                                                <div class="point">・</div>
                                                <button class="modify-reply-button reply-button">수정</button>
                                            </div>
                                            <div class="report-container">
                                                <div class="point">・</div>
                                                <button class="remove-reply-button reply-button">삭제</button>
                                            </div>`
            }
            text += `
                <div class="reply-item">
                                    <div class="profile-image">
                                        <img src="${src}" class="reply-profile-img">
                                    </div>
                                    <div class="reply-content">
                                        <div class="reply-name">
                                            <div class="name-text">crew1</div>
                                        </div>
                                        <div style="display: flex;">
                                            <div class="reply-content-text">${reply.replyContent}</div>
                                        </div>
                                        <div class="reply-like">
                                            <div class="time">${reply.relativeDate}</div>
                                            <!-- 본인일때 x -->
                                            
                                            <div class="report-container">
                                                <div class="point">・</div>
                                                <button class="reply-report-button reply-button">신고하기</button>
                                            </div>
                                            <!-- 본인 댓글만 -->
                                            ${checkUser}
                                        </div>
                                    </div>
                                </div>
            `;
        });
        replyWrap.innerHTML = text;

        text = ``;
        let criteria = repliesCriteria.criteria;

        if(criteria.hasPreviousPage){
            text = `<a data-page="${criteria.startPage - 1}" class="paging">이전</a>`
        }

        for(let i = criteria.startPage; i <= criteria.endPage; i++){
            text += `
            <a data-page="${i}" class="paging">${i}</a>
        `;
        }

        if(criteria.hasNextPage){
            text += `<a data-page="${criteria.endPage + 1}" class="paging">다음</a>`
        }

        replyPageWrap.innerHTML = text;
    }

    return {showList: showList}
})();












