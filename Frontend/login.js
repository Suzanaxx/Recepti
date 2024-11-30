document.getElementById('login-btn').addEventListener('click', () => {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    fetch('http://localhost:8081/api/users/login', {
        method: 'POST', // Ensure POST method is used
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password }),
    })
        .then(async response => {
            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(errorText);
            }
            return response.json(); // Parse JSON response
        })
        .then(user => {
            // Save user details in local storage
            localStorage.setItem('loggedInUser', JSON.stringify(user));

            // Redirect to the profile page
            window.location.href = 'profile.html';
        })
        .catch(error => alert(error.message));
});
