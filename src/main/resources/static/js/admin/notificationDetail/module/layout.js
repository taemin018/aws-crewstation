const noticeDetailLayout = (() => {
    const showNotice = async (result) => {
        const detailTitle = document.getElementById("noticeDetailTitle");
        const detailDate = document.getElementById("noticeDetailDate");
        const noticeDetailContent = document.getElementById("noticeDetailContent");
        const noticeDetailImgWrap = document.getElementById("noticeDetailImgWrap");
        let text = ``;
        detailTitle.textContent = "";
        detailDate.textContent = "";
        noticeDetailContent.textContent = "";
        detailTitle.textContent = result.noticesTitle;
        detailDate.textContent = result.createdDatetime;
        noticeDetailContent.textContent = result.noticesContent;
        result.files.forEach((file) => {
            text += `
            <img src="/api/files/display?filePath=${file.filePath}&fileName=${file.fileName}"/>
            `
        });
        noticeDetailImgWrap.innerHTML = text;
    }
    return {showNotice: showNotice}
})();