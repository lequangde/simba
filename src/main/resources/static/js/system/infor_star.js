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
	var xhr = new XMLHttpRequest();
    xhr.open("POST", "/selected-country", true);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    document.getElementById('countryForm').submit();

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


