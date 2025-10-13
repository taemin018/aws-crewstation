const noticeDetailBody = document.getElementById("noticeBody");
let prevPage = 0;
noticeDetailBody.addEventListener("click", async (e) => {
    console.log(e.target)
    if (e.target.closest(".detail-notice")) {
        e.preventDefault();
        const params = new URLSearchParams(window.location.search);
        prevPage = params.get('page'); // "12" (문자열)
        loading.style.display = "block";
        document.getElementById("notices").style.display = "none";
        await noticeDetailService.getNotice(noticeDetailLayout.showNotice, e.target.closest(".detail-notice").dataset.id);
        setTimeout(() => {
            loading.style.display = "none";
            document.getElementById("noticeDetail").style.display = "block"
        }, 500)

    }
})
document.getElementById("goNoticeList").addEventListener("click",async (e)=>{
    document.getElementById("noticeDetail").style.display = "none"
    loading.style.display = "block";
    await noticeService.getList(noticeLayout.showList,prevPage);
    setTimeout(() => {
        loading.style.display = "none";
        document.getElementById("notices").style.display="block";

    }, 500)


})

