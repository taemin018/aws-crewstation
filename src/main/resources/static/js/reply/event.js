let page = 1;
let postId= +document.getElementById("postId").dataset.post;
console.log(page);
console.log(postId);

replyService.getList(postId,replyLayout.showList,page);