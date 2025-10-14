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
    const report = async (report) => {
        let status = null;
        let message = null;
        let result = null;
        const response = await fetch("/api/report", {
            method: 'POST',
            body: JSON.stringify(report),
            headers: {
                'Content-Type': 'application/json'
            }
        });
        if (response.ok) {
            console.log("게시글 존재")
        } else if (response.status === 404) {
            console.log("게시글 없음")
        } else {
            const error = await response.text()
            console.log(error);
        }
        message = await response.text();
        return {message: message, status: response.status}
    }

    const changeSecret = async (change) =>{
        let status = null;
        let message = null;
        let result = null;
        const response = await fetch("/api/diaries/secret", {
            method: 'POST',
            body: JSON.stringify(change),
            headers: {
                'Content-Type': 'application/json'
            }
        });
        if (response.ok) {
            console.log("게시글 존재")
        } else if (response.status === 404) {
            console.log("게시글 없음")
        } else {
            const error = await response.text()
            console.log(error);
        }
        message = await response.text();
        return {message: message, status: response.status}
    }
    return {like:like,report:report,changeSecret:changeSecret}
})();


