document.addEventListener('DOMContentLoaded', function() {
  // Set a timeout to go back to the previous page after 2 seconds
  setTimeout(function() {
    // Go back to the previous page
    window.location.href = '/qr/home';
  }, 4000); // 1000 milliseconds = 1 seconds
});