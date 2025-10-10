const mainService = (() => {

    const getMain = async (callback) => {
        try {
            const response = await fetch(`/api/admin/`)
            const result = await response.json();
            console.log(result);
            if (response.ok) {
                console.log("통계 잘나옴")
                if (callback) {
                    setTimeout(() => {
                        callback(result);
                    }, 1000)
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

    return {getMain: getMain}
})();