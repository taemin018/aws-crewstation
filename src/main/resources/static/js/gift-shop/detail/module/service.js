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
        let status =null;
        let message = null;
        let result = null;
        const response = await fetch("/api/post/report", {
            method: 'POST',
            body: JSON.stringify(report),
            headers: {
                'Content-Type': 'application/json'
            }
        });
        if(!response.ok){
            result = await response.json();
            message = result.message;
        }else{
            message = await  response.text();
        }
        return {message : message , status : response.status}
    }
    return {info: info,report:report}
})();