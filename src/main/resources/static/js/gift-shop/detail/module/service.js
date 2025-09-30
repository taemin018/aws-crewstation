const purchaseDetailService = (() => {
    const info = async (callback) => {
        const response = await fetch('/api/gifts/info');
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || "Fetch error");
        }

        const member = await response.json();

        if (callback) {
            callback(member);
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

    const requestToSell = async (request) => {
        let message = null;
        let result = null;
        let isGuest = false;
        const response = await fetch("/api/payment/", {
            method: 'POST',
            body: JSON.stringify(request),
            headers: {
                'Content-Type': 'application/json'
            }
        });
        if (response.ok) {
            result = await response.json();
            message = result.message;
            isGuest = result.guest;
            console.log("기프트 존재")
        } else if (response.status === 404) {
            message = await response.text();
            console.log("기프트 없음")
        } else {
            message = await response.text();
            console.log("에러 발생");
        }

        console.log(message)
        return {isGuest: isGuest,message: message, status: response.status}
    }
    return {info: info, report: report, requestToSell: requestToSell}
})();