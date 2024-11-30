document.addEventListener('DOMContentLoaded', () => {
    const profileButton = document.getElementById('profile-button');

    // Handle profile button click
    profileButton.addEventListener('click', () => {
        const user = JSON.parse(localStorage.getItem('loggedInUser'));

        if (!user) {
            // Redirect to login if not logged in
           
            window.location.href = 'login.html';
        } else {
            // Redirect to profile page if logged in
            window.location.href = 'profile.html';
        }
    });
});
