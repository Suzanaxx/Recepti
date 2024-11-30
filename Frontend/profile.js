document.addEventListener('DOMContentLoaded', () => {
    const userInfoContainer = document.getElementById('user-info');
    const logoutButton = document.getElementById('logout-btn');

    // Retrieve user data from local storage
    const user = JSON.parse(localStorage.getItem('loggedInUser'));

    if (!user) {
        // If no user is logged in, redirect to login
        
        window.location.href = 'login.html';
    } else {
        // Display user details
        userInfoContainer.innerHTML = `
            <p><strong>Username:</strong> ${user.username}</p>
            <p><strong>Email:</strong> ${user.email}</p>
        `;
    }

    // Logout functionality
    logoutButton.addEventListener('click', () => {
        localStorage.removeItem('loggedInUser'); // Remove user details
       
        window.location.href = 'login.html'; // Redirect to login page
    });
});
