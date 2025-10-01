const diaryListService = (() => {
    const getDiaries = async (callback, page = 1, keyword = "",orderType="최신순",category="",check=false) => {
        console.log(page)
        try {
            const response = await fetch(`/api/diaries?page=${page}&keyword=${keyword}&orderType=${orderType}&category=${category}`)
            const result = await response.json();
            console.log(result);
            if (response.ok) {
                console.log("다이어리 목록 잘 나옴")
                setTimeout(  () => {
                     callback(result,check);
                }, 500)
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
    return {getDiaries: getDiaries}
})();


