// ===================== Order Layout =====================
const orderLayout = (() => {

    // 상세 정보 렌더링
    const renderOrderDetail = (container, order) => {
        container.innerHTML = `
            <div class="main-title">
                주문번호 <span class="title-section">I</span>
                <span class="order-number">${order.guestOrderNumber}</span>
            </div>
            <section class="header">
                <section class="header-wrapper">
                    <h1 class="header-title">
                        <span class="date">${order.createdDatetime || ""}</span>
                        <span>${order.paymentStatus}</span>
                    </h1>
                </section>
                <div class="line"><h3 class="status">${order.paymentStatus}</h3></div>

                <button class="status-btn cancel-btn active">주문 취소</button>
                <button class="status-btn receive-btn">수령 완료</button>
                <button class="status-btn review-btn">
                    <img class="manner-icon" src="/static/images/manner.png"> 별점 주기
                </button>
            </section>

            <hr class="bar">

            <div class="product">
                <section class="header-wrapper">
                    <h1 class="header-title"><span>결제 정보</span></h1>
                </section>
                <div class="product-wrapper">
                    <img src="${order.mainImage || '/static/images/gift-shop-post-img2.png'}" alt="">
                    <div class="purchase-info">
                        <span class="badge-list-container">${order.purchaseCountry}</span>
                        <p class="info-title">${order.postTitle}</p>
                        <span class="badge-list-container amount">수량</span>
                        <span class="info-amount">1개</span>
                        <p class="info-price">${order.purchaseProductPrice} 원</p>
                    </div>
                </div>

                <div class="info-container">
                    <h2 class="address-wrap">판매자 정보</h2>
                    <div class="seller-info-category"><span class="seller-info">배송방법</span>${order.purchaseDeliveryMethod}</div>
                    <div class="seller-info-category"><span class="seller-info">판매자명</span>${order.memberName}</div>
                    <div class="seller-info-category"><span class="seller-info">전화번호</span>${order.memberPhone}</div>
                </div>

                <hr class="divider">

                <div class="info-container">
                    <h2 class="address-wrap">구매자 정보</h2>
                    <div class="buyer-info-category"><span class="buyer-info">주소</span>${order.address} ${order.addressDetail}</div>
                    <div class="buyer-info-category"><span class="buyer-info">전화번호</span>${order.guestPhone}</div>
                </div>
            </div>
        `;
    };

    // 상태 업데이트
    const updateOrderStatus = (statusText) => {
        const statusEl = document.querySelector(".header .status");
        if (statusEl) statusEl.innerText = statusText;
    };

    // 토스트 메시지
    const showToast = (message) => {
        alert(message);
    };

    return {
        renderOrderDetail : renderOrderDetail,
        updateOrderStatus : updateOrderStatus,
        showToast : showToast
    };

})();
