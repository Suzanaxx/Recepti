document.getElementById('register-btn').addEventListener('click', () => {
    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    fetch('http://localhost:8081/api/users/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, email, password }),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Registration failed: ' + response.statusText);
            }
            return response.text();
        })
        .then(data => alert(data))
        .catch(error => alert(error.message));
});
