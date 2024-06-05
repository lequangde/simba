const stars = document.querySelectorAll('.stars i');

//Loop through stars 
stars.forEach((star, index1) => {
	//Add event listener
	star.addEventListener('click', () => {
		console.log('clicked');
		stars.forEach((star, index2) => {
			console.log(index2);
			index1 >= index2 ? star.classList.add('active') : star.classList.remove('active')
		})
	});
});

const stars2 = document.querySelectorAll('.stars2 i');

//Loop through stars 
stars2.forEach((star2, index3) => {
	//Add event listener
	star2.addEventListener('click', () => {
		console.log('clicked');
		stars2.forEach((star2, index4) => {
			console.log(index4);
			index3 >= index4 ? star2.classList.add('active') : star2.classList.remove('active')
		})
	});
});

document.querySelectorAll('.stars > i').forEach(function(star) {
	star.addEventListener('click', function() {
		document.getElementById('valueFood').value = this.getAttribute('data-value');
	});
});

document.querySelectorAll('.stars2 > i').forEach(function(star) {
	star.addEventListener('click', function() {
		document.getElementById('valueService').value = this.getAttribute('data-value');
	});
});

function refreshPage() {
	// Use the location.reload() method to reload the page
	location.reload(true);
}

function redirectToController() {
    // Replace '/controller/action' with the URL of your controller action
    window.location.href = '/app/rating';
}
