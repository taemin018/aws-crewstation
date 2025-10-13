const noticeDetailService = (() => {
    const getNotice = async(callback,id) =>{
        try {
            const response = await fetch(`/api/admin/notices/${id}`);
            const result = await response.json();
            console.log(result);
            if (response.ok) {
                console.log("공지 상세보기 잘나옴")
                if (callback) {
                    setTimeout(() => {
                        callback(result);
                    }, 500)
                }
            } else {
                const errorText = await response.text();
                console.log(response.status);
                console.log(errorText || "Fetch Error");
            }
            return result;
        } catch (error) {
            console.log(error);
        }
    }
    return {getNotice:getNotice}
})();