const API_URL = "http://localhost:8000";
const token = localStorage.getItem("token");

if (!token) {
    window.location.href = "index.html";
}

function logout() {
    localStorage.removeItem("token");
    window.location.href = "index.html";
}

function formatDate(date) {
    return new Date(date).toLocaleDateString("pt-BR");
}

function toggleSidebar() {
    document.getElementById("sidebar").classList.toggle("collapsed");
}

// ================== DESPESAS ==================

async function loadExpenses() {
    try {
        const response = await fetch(`${API_URL}/expenses`, {
            headers: { "Authorization": "Bearer " + token }
        });

        if (response.status === 401 || response.status === 403) {
            logout();
            return;
        }

        const expenses = await response.json();

        const table = document.getElementById("expensesTable");
        const categoryContainer = document.getElementById("categoryDashboard");

        table.innerHTML = "";
        categoryContainer.innerHTML = "";

        let total = 0;
        const categoryMap = {};

        expenses.forEach(e => {
            const amount = Number(e.amount) || 0;
            const categoryName = e.category ? e.category.name : "Sem categoria";

            total += amount;
            categoryMap[categoryName] =
                (categoryMap[categoryName] || 0) + amount;

            const tr = document.createElement("tr");
            tr.innerHTML = `
                <td>${e.description}</td>
                <td>R$ ${amount.toFixed(2)}</td>
                <td>${categoryName}</td>
                <td>${formatDate(e.date)}</td>
                <td>
                    <button onclick="deleteExpense(${e.id})">Excluir</button>
                </td>
            `;
            table.appendChild(tr);
        });

        document.getElementById("totalExpenses").innerText =
            `R$ ${total.toFixed(2)}`;
        document.getElementById("totalCount").innerText =
            expenses.length;

        Object.entries(categoryMap).forEach(([name, value]) => {
            const card = document.createElement("div");
            card.className = "category-card";
            card.innerHTML = `
                <h5>${name}</h5>
                <p>R$ ${value.toFixed(2)}</p>
            `;
            categoryContainer.appendChild(card);
        });

    } catch (error) {
        console.error(error);
        alert("Erro ao carregar despesas");
    }
}


document.getElementById("expenseForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const categoryId = Number(
        document.getElementById("categorySelect").value
    );


    const expense = {
    description: document.getElementById("description").value,
    amount: parseFloat(document.getElementById("amount").value),
    categoryId: categoryId, // agora é number
    date: document.getElementById("date").value,
    type: "EXPENSE"
};


    try {
        const response = await fetch(`${API_URL}/expenses`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            },
            body: JSON.stringify(expense)
        });

        if (!response.ok) {
            throw new Error("Erro ao salvar despesa");
        }

        document.getElementById("message").innerText =
            "Despesa salva com sucesso!";

        setTimeout(() => {
            document.getElementById("message").innerText = "";
        }, 3000);

        e.target.reset();
        loadExpenses();

    } catch (error) {
        console.error(error);
        alert("Erro ao salvar despesa");
    }
});


async function deleteExpense(id) {
    if (!confirm("Deseja excluir esta despesa?")) return;

    try {
        await fetch(`${API_URL}/expenses/${id}`, {
            method: "DELETE",
            headers: {
                "Authorization": "Bearer " + token
            }
        });

        loadExpenses();
    } catch (error) {
        console.error(error);
        alert("Erro ao excluir despesa");
    }
}

// ================== CATEGORIAS ==================

async function loadCategories() {
    try {
        const response = await fetch(`${API_URL}/categories`, {
            headers: { "Authorization": "Bearer " + token }
        });

        if (!response.ok) {
            throw new Error("Erro ao buscar categorias");
        }

        const categories = await response.json();

        const select = document.getElementById("categorySelect");
        select.innerHTML = `<option value="">Selecione uma categoria</option>`;

        categories.forEach(cat => {
            const option = document.createElement("option");
            option.value = cat.id;
            option.textContent = cat.name;
            select.appendChild(option);
        });

    } catch (e) {
        console.error("Erro ao buscar categorias", e);
    }
}

document.getElementById("categoryForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const name = document.getElementById("categoryName").value;

    const response = await fetch(`${API_URL}/categories`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify({ name })
    });

    if (!response.ok) {
        document.getElementById("categoryMessage").innerText =
            "Erro ao criar categoria";
        return;
    }

    document.getElementById("categoryMessage").innerText =
        "Categoria criada com sucesso!";

    document.getElementById("categoryForm").reset();
    loadCategories();
});

document.addEventListener("DOMContentLoaded", () => {
    loadCategories();
    loadExpenses();

});

async function loadBalance() {
    const response = await fetch("http://localhost:8000/expenses/balance", {
        headers: {
            "Authorization": "Bearer " + token
        }
    });

    const balance = await response.json();
    document.getElementById("balance").innerText =
        `Saldo: R$ ${balance.toFixed(2)}`;
}

function showPage(pageId, btn) {
    // Esconde todas as páginas
    document.querySelectorAll(".page").forEach(p =>
        p.classList.remove("active")
    );

    // Remove ativo dos botões
    document.querySelectorAll(".nav-btn").forEach(b =>
        b.classList.remove("active")
    );

    // Mostra a página selecionada
    document.getElementById(pageId).classList.add("active");

}
