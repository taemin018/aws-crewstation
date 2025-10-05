let page = 1;
let postId= +document.getElementById("postId").dataset.post;
console.log(page);
console.log(postId);

replyService.getList(postId,replyLayout.showList,page);


const pagination = document.getElementById("replyPageWrap");
pagination.addEventListener("click",async (e)=>{
    console.log(e.target.closest("button.number-button"));
    if(e.target.closest("button.number-button")){
        page = e.target.closest("button.number-button").dataset.page;
        await replyService.getList(postId,replyLayout.showList,page);
    }

})