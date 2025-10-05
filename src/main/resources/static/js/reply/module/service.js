const replyService = (() => {


    const getList = async (postId, callback, page=1) => {
        const response = await fetch(`/api/replies/${postId}?page=${page}`)
        const repliesCriteria = await response.json();

        if(callback){
            callback(repliesCriteria);
        }

    }
    const report = async (report) => {
        let status = null;
        let message = null;
        let result = null;
        const response = await fetch("/api/post/report", {
            method: 'POST',
            body: JSON.stringify(report),
            headers: {
                'Content-Type': 'application/json'
            }
        });
        if (response.ok) {
            console.log("기프트 존재")
        } else if (response.status === 404) {
            console.log("기프트 없음")
        } else {
            const error = await response.text()
            console.log(error);
        }
        message = await response.text();
        return {message: message, status: response.status}
    }


    return {getList:getList,report: report}
})();