const mainLayout = (() => {
    const showMain = async (result) => {
        const today = new Date();

        document.getElementById("todayJoinCount").textContent = result.todayJoin;
        document.getElementById("monthlyJoinCount").textContent = result.monthlyJoins[2].count;
    }
    return {showMain: showMain}
})();


