const wrapper = document.querySelector(".wrapperr"),
	selectBtn = wrapper.querySelector(".select-btn"),
	searchInp = wrapper.querySelector("input"),
	options = wrapper.querySelector(".options");

function refreshPage() {
	location.reload();
}

function addCountry(selectedCountry) {
	options.innerHTML = "";
	countries.forEach(country => {
		let isSelected = country == selectedCountry ? "selected" : "";
		let li = `<li onclick="updateName(this)" class="${isSelected}">${country}</li>`;
		options.insertAdjacentHTML("beforeend", li);
	});
}

fetch('/api/countryDB')
	.then(response => {
		if (!response.ok) {
			throw new Error('Network response was not ok');
		}
		return response.json();
	})
	.then(data => {
		countries = data; // Assign the fetched data directly to 'countries'
		console.log(countries); // Now 'countries' array is populated with data from the backend
		addCountry(); // Call addCountry function after countries are populated
	})
	.catch(error => {
		console.error('There was a problem with the fetch operation:', error);
	});

function updateName(selectedLi) {
	var selectedCountry = selectedLi.innerText;
	console.log(selectedCountry); // Log the text of the clicked <li> element

	// Make an AJAX request to the backend controller
	document.getElementById('selectedCountryInput').value = selectedCountry;
	searchInp.value = "";
	addCountry(selectedCountry);
	wrapper.classList.remove("active");
	selectBtn.firstElementChild.innerText = selectedCountry;
}


searchInp.addEventListener("keyup", () => {
	let arr = [];
	let searchWord = searchInp.value.toLowerCase();
	arr = countries.filter(data => {
		return data.toLowerCase().startsWith(searchWord);
	}).map(data => {
		let isSelected = data == selectBtn.firstElementChild.innerText ? "selected" : "";
		return `<li onclick="updateName(this)" class="${isSelected}">${data}</li>`;
	}).join("");
	options.innerHTML = arr ? arr : `<p style="margin-top: 10px;">Không tìm thấy cơ sở</p>`;
});


selectBtn.addEventListener("click", function(event) {
	event.stopPropagation(); // Prevent the click event from propagating to the document
	wrapper.classList.toggle("active");
});

// Toggle 'disactive' class when any part of the document is clicked outside the wrapper
document.addEventListener("click", function(event) {
	if (!wrapper.contains(event.target) && wrapper.classList.contains("active")) {
		wrapper.classList.remove("active");
	}
});

function submitForm(event, action) {
	event.preventDefault(); // Prevent default form submission
	var form = document.getElementById("countryForm");
	var startDate = document.getElementById("startDate");
	var endDate = document.getElementById("endDate");
	var valueDate = startDate.value;
	var valueDate2 = endDate.value;
	// Set the action URL based on the button clicked
	if (action === "download") {
		if (!(valueDate)) {
			alert('Chọn giá trị ngày bắt đầu !!');
		} else if (!(valueDate2)) {
			alert('Chọn giá trị ngày kết thúc !!');
		} else {
			form.action = "/excel/exportExcel";
			form.submit(); // Manually submit the form
		}

	} else if (action === "view") {
		form.action = "/excel/report";
		form.submit(); // Manually submit the form
	}
	console.log(valueDate)

}

// Save the scroll position before navigating away
window.addEventListener('beforeunload', function() {
	localStorage.setItem('scrollPosition', window.scrollY);
});

// Restore the scroll position after the page loads
window.addEventListener('load', function() {
	const scrollPosition = localStorage.getItem('scrollPosition');
	if (scrollPosition) {
		window.scrollTo(0, scrollPosition);
		localStorage.removeItem('scrollPosition');  // Clean up
	}
});

document.getElementById('dateSelector').addEventListener('change', function() {
	var selectedValue = this.value;

	if (selectedValue === 'currentDate') {
		var currentDate = new Date().toISOString().split('T')[0];
		document.getElementById('startDate').value = currentDate;
		document.getElementById('endDate').value = currentDate;
	} else if (selectedValue === 'currentMonth') {
		var today = new Date();
		var firstDayOfMonth = new Date(today.getFullYear(), today.getMonth(), 1);
		var lastDayOfMonth = new Date(today.getFullYear(), today.getMonth() + 1, 0);

		var firstDayOfMonthString = formatDate(firstDayOfMonth);
		var lastDayOfMonthString = formatDate(lastDayOfMonth);

		document.getElementById('startDate').value = firstDayOfMonthString;
		document.getElementById('endDate').value = lastDayOfMonthString;
	} else if (selectedValue === 'currentWeek') {
		var today = new Date();
		var startOfWeek = new Date(today.getFullYear(), today.getMonth(), today.getDate() - today.getDay());
		var endOfWeek = new Date(today.getFullYear(), today.getMonth(), today.getDate() + (6 - today.getDay()));

		var startOfWeekString = formatDate(startOfWeek);
		var endOfWeekString = formatDate(endOfWeek);

		document.getElementById('startDate').value = startOfWeekString;
		document.getElementById('endDate').value = endOfWeekString;
	}
});

function formatDate(date) {
	var year = date.getFullYear();
	var month = (date.getMonth() + 1).toString().padStart(2, '0'); // Months are zero-based
	var day = date.getDate().toString().padStart(2, '0');
	return year + '-' + month + '-' + day;
}

  

