// 정렬 버튼 드롭다운
const toggleBtn = document.getElementById("filterToggle");
const dropdown = document.getElementById("filterDropdown");

if (toggleBtn && dropdown) {
    // 버튼 클릭 시 드롭다운 열고 닫기
    toggleBtn.addEventListener("click", (e) => {
        e.stopPropagation();
        dropdown.style.display =
            dropdown.style.display === "block" ? "none" : "block";
    });

    // 메뉴 클릭 시 버튼 텍스트 변경
    dropdown.querySelectorAll("li").forEach((item) => {
        item.addEventListener("click", () => {
            if (!btnCheck) return;
            btnCheck = false;
            toggleBtn.childNodes[0].nodeValue = item.textContent + " ";
            dropdown.style.display = "none";
            setTimeout(() => {
                btnCheck = true
            }, 1500);
        });
    });

    // 외부 클릭 시 닫힘
    document.addEventListener("click", () => {
        dropdown.style.display = "none";
    });
}

// 좋아요 버튼 + 토스트 메시지
const likeBtns = document.querySelectorAll("button.card-item-action-btn.like");
const toast = document.querySelector("div.toast");
const toastText = document.querySelector("p.toast-text");

let likeClickable = true; // 광클 방지용 플래그

likeBtns.forEach((likeBtn) => {
    likeBtn.addEventListener("click", () => {
        if (!btnCheck) return;
        btnCheck = false;
        if (!likeClickable) return;
        likeClickable = false;
        console.log("클릭됨");
        setTimeout(() => {
            btnCheck = true
        }, 1500);
        toast.style.display = "block";
        toast.classList.remove("hide");
        toast.classList.add("show");

        const svg = likeBtn.querySelector("svg");
        if (svg.classList.contains("active")) {
            svg.classList.remove("active");
            toastText.textContent = "좋아요가 취소되었습니다.";
        } else {
            svg.classList.add("active");
            toastText.textContent = "좋아요가 추가되었습니다.";
        }

        // 토스트 애니메이션 처리
        setTimeout(() => {
            toast.classList.remove("show");
            toast.classList.add("hide");

            setTimeout(() => {
                toast.style.display = "none";
                likeClickable = true;
            }, 1000);
        }, 2000);
    });
});

let page = 1;
let keyword = "";
let orderType = "최신순"
let category = "total"
let checkMore = true;
let checkScroll = true;

const showList = async (page, keyword, orderType, category,check=false) => {
    diaries = await diaryListService.getDiaries(diariesListLayout.showDiaries, page, keyword, orderType, category,check);
    console.log(diaries);
    checkMore = diaries.criteria.hasMore;
    ({page, keyword, orderType, category} = diaries.search);
}
showList(page, keyword, orderType, category);


window.addEventListener("scroll", async (e) => {

    if (!checkMore) {
        console.log("더 이상 없어");
        return;
    }
    if (!checkScroll) {
        return;
    }
    // 현재 스크롤 위치
    const scrollTop = window.scrollY

    // 화면 높이
    const windowHeight = window.innerHeight;

    // 문서 전체 높이
    const documentHeight = document.documentElement.scrollHeight
    if (scrollTop + windowHeight >= documentHeight - 100) {
        //     바닥에 닿았을 때
        if (checkScroll) {
            checkScroll = false;
            console.log("몇 번 실행되니")
            diaries = await diaryListService.getDiaries(diariesListLayout.showDiaries, ++page, keyword, orderType, category);
            console.log(diaries);
            ({page, keyword, orderType, category} = diaries.search);
            checkMore = diaries.criteria.hasMore;
            console.log("시작해보자")
            setTimeout(() => {
                if (checkMore) {
                    checkScroll = true;
                }
                console.log("지금 실행")
            }, 500);
        }

    }
})




