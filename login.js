document.getElementById('loginForm').addEventListener('submit', async (e) => {
e.preventDefault();


const email = document.getElementById('email').value;
const password = document.getElementById('password').value;


const response = await fetch('http://localhost:8000/auth/login', {
method: 'POST',
headers: { 'Content-Type': 'application/json' },
body: JSON.stringify({ email, password })
});


if (response.ok) {
const data = await response.json();
localStorage.setItem('token', data.token);
alert('Login realizado com sucesso');
} else {
document.getElementById('error').innerText = 'Usuário ou senha inválidos';
}

localStorage.setItem("token", data.token);
window.location.href = "expenses.html";

});