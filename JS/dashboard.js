async function loadIncomes() {
    const response = await fetch(`${API_URL}/incomes`, {
        headers: { Authorization: "Bearer " + token }
    });

    if (!response.ok) return;

    const incomes = await response.json();
    const table = document.getElementById("incomeTable");
    table.innerHTML = "";

    let totalIncome = 0;

    incomes.forEach(i => {
        const value = Number(i.amount) || 0;
        totalIncome += value;

        table.innerHTML += `
            <tr>
                <td>${i.description}</td>
                <td>R$ ${value.toFixed(2)}</td>
                <td>${formatDate(i.date)}</td>
            </tr>
        `;
    });

    return totalIncome;
}
