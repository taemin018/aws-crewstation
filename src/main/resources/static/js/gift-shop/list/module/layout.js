const purchaseLayout = (() => {
    const showPurchases = async (result) => {
        let text = ``;

        const purchases = result.purchaseDTOs;
        const tbody = document.querySelector("div.product-list-wrapper");
        console.log(tbody)
        purchases.forEach((purchase) => {
                text += `
            <article class="product-card-wrapper">
                <div class="product-image-container">
                    <div class="product-limit-time-wrapper" data-endtime="${purchase.limitDateTime}">
                    </div>
                    <div class="product-image-wrapper">
                        <div class="product-image-hover">
                            <img src="https://prs.ohousecdn.com/apne2/any/uploads/commerce/store/deal/v1-400472465334400.jpg?w=256&amp;h=256&amp;c=c&amp;q=50" srcset="https://prs.ohousecdn.com/apne2/any/uploads/commerce/store/deal/v1-400472465334400.jpg?w=384&amp;h=384&amp;c=c&amp;q=50 1.5x,https://prs.ohousecdn.com/apne2/any/uploads/commerce/store/deal/v1-400472465334400.jpg?w=512&amp;h=512&amp;c=c&amp;q=50 2x,https://prs.ohousecdn.com/apne2/any/uploads/commerce/store/deal/v1-400472465334400.jpg?w=768&amp;h=768&amp;c=c&amp;q=50 3x" alt="상품-썸네일-이미지" class="product-image">
                        </div>
                    </div>
                </div>
                <div class="product-content-wrapper">
                    <div class="product-content-header">
                      <div class="product-content-nickname">${purchase.memberName}</div>
                      <span class="manner-wrap">
                        <img class="product-detail-header-manner-img" src="/images/manner.png" width="14" height="14" alt="케미지수 아이콘">
                        <a class="product-detail-header-manner">${purchase.chemistryScore}케미지수</a>
                      </span>
                    </div>
                    <div class="product-content-name">${purchase.postTitle}</div>
                    <div class="product-content-stock">${purchase.purchaseProductCount}개 남음</div>
                    <div class="product-content-price">${purchase.purchaseProductPrice} 원</div>
                    <div class="writer-info">
                    <span class="badge-list-container">
                        ${purchase.purchaseCountry} </span>
                    <span class="badge-list-container delivery-method">
                        ${purchase.purchaseDeliveryMethod === "direct" ? "직접전달" : "택배거래"} </span>
                    </div>

                </div>
            </article>
            `
            })

        tbody.innerHTML += text;

    }

    return {showPurchases: showPurchases};
})();