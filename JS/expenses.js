const API_URL = "http://localhost:8000";
const token = localStorage.getItem("token");

if (!token) {
    window.location.href = "../HTML/index.html";
}

function logout() {
    localStorage.removeItem("token");
    window.location.href = "../HTML/index.html";
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
            headers: { Authorization: "Bearer " + token }
        });

        if (response.status === 401 || response.status === 403) {
            logout();
            return;
        }

        const expenses = await response.json();

        const table = document.getElementById("expensesTable");
        const categoryContainer = document.getElementById("categoryDashboard");

        if (!table || !categoryContainer) return;

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

        const totalCountEl = document.getElementById("totalCount");
        if (totalCountEl) {
            totalCountEl.innerText = expenses.length;
        }

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

const expenseForm = document.getElementById("expenseForm");
if (expenseForm) {
    expenseForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        const categorySelect = document.getElementById("categorySelect");
        if (!categorySelect) return;

        const categoryValue = categorySelect.value;

        if (!categoryValue) {
            alert("Selecione uma categoria");
            return;
        }

        const rawAmount = document.getElementById("amount").value.replace(",", ".");

        const amountValue = parseFloat(rawAmount);

        if (isNaN(amountValue) || amountValue <= 0) {
            alert("Informe um valor válido");
            return;
        }
        const dateValue = document.getElementById("date").value;

        if (!dateValue) {
            alert("Informe a data da despesa");
            return;
        }

        const expense = {
            description: document.getElementById("description").value,
            amount: amountValue,
            categoryId: Number(categoryValue),
            date: dateValue
        };

        const response = await fetch(`${API_URL}/expenses`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + token
            },
            body: JSON.stringify(expense)
        });

        if (!response.ok) {
            const errorText = await response.text();
            console.error("Erro backend:", errorText);
            alert("Erro ao salvar despesa: " + errorText);
            return;
        }


        const messageEl = document.getElementById("message");
        if (messageEl) {
            messageEl.innerText = "Despesa salva com sucesso!";
            setTimeout(() => {
                messageEl.innerText = "";
            }, 3000);
        }

        e.target.reset();
        loadExpenses();

    });
}


async function deleteExpense(id) {
    if (!confirm("Deseja excluir esta despesa?")) return;

    const response = await fetch(`${API_URL}/expenses/${id}`, {
        method: "DELETE",
        headers: { Authorization: "Bearer " + token }
    });

    if (!response.ok) {
        alert("Erro ao excluir despesa");
        return;
    }

    loadExpenses();
}


// ================== CATEGORIAS ==================

async function loadCategories() {
    try {
        const response = await fetch(`${API_URL}/categories`, {
            headers: { Authorization: "Bearer " + token }
        });

        if (!response.ok) return;

        const categories = await response.json();
        const select = document.getElementById("categorySelect");

        if (!select) return;

        select.innerHTML =
            `<option value="">Selecione uma categoria</option>`;

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

const categoryForm = document.getElementById("categoryForm");
if (categoryForm) {
    categoryForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        const name = document.getElementById("categoryName").value;

        const response = await fetch(`${API_URL}/categories`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + token
            },
            body: JSON.stringify({ name })
        });

        document.getElementById("categoryMessage").innerText =
            response.ok
                ? "Categoria criada com sucesso!"
                : "Erro ao criar categoria";

        if (response.ok) {
            categoryForm.reset();
            loadCategories();
        }
    });
}
// SIDEBAR NAVIGATION
function showPage(pageId, btn) {
    document.querySelectorAll(".page").forEach(page => {
        page.classList.remove("active");
    });

    document.querySelectorAll(".nav-btn").forEach(b => {
        b.classList.remove("active");
    });

    const page = document.getElementById(pageId);
    if (page) {
        page.classList.add("active");
    }

    if (btn) {
        btn.classList.add("active");
    }

    // Atualiza dados conforme a página
    if (pageId === "dashboard") {
        loadExpenses();
    }

    if (pageId === "expenses") {
        loadCategories();
        loadExpenses();
    }
}

// ================== INIT ==================

document.addEventListener("DOMContentLoaded", () => {
    loadCategories();
    loadExpenses();
});
