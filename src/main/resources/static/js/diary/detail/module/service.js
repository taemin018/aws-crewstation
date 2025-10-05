const diaryDetailService = (() => {
    const like = async (like,isLike)=>{
        const method = isLike ? 'DELETE' : 'POST'
        const url = isLike ? `/api/likes/${like.postId}` : `/api/likes`;
        console.log(JSON.stringify(like))
        try {
            const response = await fetch(url,{
                method: method,
                body: JSON.stringify(like),
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            const result = await response.text();
            return {message:result,status : response.status}
        } catch (error) {
            console.log(error);
        }
    }
    return {like:like}
})();


