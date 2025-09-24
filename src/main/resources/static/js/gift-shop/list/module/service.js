const purchaseService = (() => {
    const getPurchases = async (callback, page = 1, keyword = "") => {
        console.log(page)
        try {
            const response = await fetch(`/api/gifts`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({page: page, keyword: keyword})
            })
            const result = await response.json();
            console.log(result);
            if (response.ok) {
                console.log("기프트 목록 잘 나옴")
                setTimeout(() => {
                    callback(result);
                }, 1000)
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
    return {getPurchases: getPurchases}
})();