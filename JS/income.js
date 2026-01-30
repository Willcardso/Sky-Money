document.getElementById("incomeForm").addEventListener("submit", async e => {
    e.preventDefault();

    const income = {
        description: incomeDescription.value,
        amount: incomeAmount.value,
        date: incomeDate.value
    };

    await fetch(`${API_URL}/incomes`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + token
        },
        body: JSON.stringify(income)
    });

    e.target.reset();
    updateDashboard();
});
