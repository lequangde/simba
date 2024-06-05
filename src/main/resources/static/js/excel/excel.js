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

  
document.addEventListener('DOMContentLoaded', (event) => {
        const rows = document.querySelectorAll('tbody tr');
        const ratingsData = [];

        rows.forEach(row => {
            const rateId = row.getAttribute('data-rate-id');
            const department = row.getAttribute('data-department');
            const valueFood = row.getAttribute('data-value-food');
            const valueService = row.getAttribute('data-value-service');
            const dateRate = row.getAttribute('data-date-rate');

            // Calculate the maximum number of stars based on the maximum rating value
            const maxStars = Math.max(parseInt(valueFood), parseInt(valueService));

            ratingsData.push({
                rateId: rateId,
                department: department,
                valueFood: parseInt(valueFood),
                valueService: parseInt(valueService),
                dateRate: dateRate,
                maxStars: maxStars // Store the maximum stars for reference
            });
        });

        console.log(ratingsData);
        // Now you can use the ratingsData array for further manipulation
    });
    

document.addEventListener('DOMContentLoaded', function() {
    // Add event listener for rating filter change
    document.getElementById('ratingFilter').addEventListener('change', function() {
        currentPage = 1; // Reset current page
        filterAndPaginateTable(); // Filter and paginate table
    });

    // Initially filter and paginate table with page size 5
    filterAndPaginateTable();
});

var currentPage = 1;
var pageSize = 5; // Change page size to 5 to match the maximum 5 records displayed

function filterAndPaginateTable() {
    var rating = document.getElementById('ratingFilter').value;
    var table = document.getElementById('ratingTableBody');
    var rows = table.getElementsByTagName('tr');
    var filteredRows = [];

    // Filter rows based on the selected rating
    for (var i = 0; i < rows.length; i++) {
        var valueFood = rows[i].getAttribute('data-value-food');
        var valueService = rows[i].getAttribute('data-value-service');
        var shouldDisplay = (rating === 'all') || (valueFood === rating) || (valueService === rating);

        if (shouldDisplay) {
            filteredRows.push(rows[i]);
        }
    }

    var totalPages = Math.ceil(filteredRows.length / pageSize);

    // Hide all rows initially
    for (var i = 0; i < rows.length; i++) {
        rows[i].style.display = 'none';
    }

    // Display only rows for the current page
    var start = (currentPage - 1) * pageSize;
    var end = start + pageSize;
    for (var i = start; i < end && i < filteredRows.length; i++) {
        filteredRows[i].style.display = '';
    }

    // Update pagination
    updatePagination(totalPages);
    var noDataMessage = document.getElementById('noDataMessage');
    if (filteredRows.length === 0) {
        noDataMessage.style.display = 'block';
    } else {
        noDataMessage.style.display = 'none';
    }

    // Hide or show next and previous page buttons if no data available
    var previousPageButton = document.getElementById('previousPage');
    var nextPageButton = document.getElementById('nextPage');
    
    
    if (filteredRows.length === 0) {
        previousPageButton.classList.add('hidden');
        nextPageButton.classList.add('hidden');
    } else {
        previousPageButton.classList.remove('hidden');
        nextPageButton.classList.remove('hidden');
    }
}


function updatePagination(totalPages) {
    var pagination = document.getElementById('pagination');
    var pageNumbers = document.getElementById('pageNumbers');
    pageNumbers.innerHTML = '';

    // Create page number buttons
    for (var i = 1; i <= totalPages; i++) {
        if (totalPages <= 7) {
            // Display all page numbers if totalPages <= 7
            var pageButton = document.createElement('button');
            pageButton.className = 'btn page-btn';
            pageButton.textContent = i;
            pageButton.addEventListener('click', function() {
                currentPage = parseInt(this.textContent);
                filterAndPaginateTable();

                // Reset color of all page number buttons to black
                var allButtons = document.querySelectorAll('.page-btn');
                allButtons.forEach(function(button) {
                    button.style.color = 'black';
                });

                // Set color of clicked button to red
                this.style.color = 'red';
            });
            pageNumbers.appendChild(pageButton);
        } else {
            if (i == 1 || i == totalPages || (i >= currentPage - 1 && i <= currentPage + 1)) {
                // Display pages 1, current page, and last page, as well as current page neighbors if totalPages > 7
                var pageButton = document.createElement('button');
                pageButton.className = 'btn page-btn';
                pageButton.textContent = i;
                pageButton.addEventListener('click', function() {
                    currentPage = parseInt(this.textContent);
                    filterAndPaginateTable();

                    // Reset color of all page number buttons to black
                    var allButtons = document.querySelectorAll('.page-btn');
                    allButtons.forEach(function(button) {
                        button.style.color = 'black';
                    });

                    // Set color of clicked button to red
                    this.style.color = 'red';
                });
                pageNumbers.appendChild(pageButton);
            } else if (i == 2 && currentPage > 4) {
                // Display ellipsis before page 3 if current page > 4
                var ellipsisStart = document.createElement('span');
                ellipsisStart.textContent = '...';
                pageNumbers.appendChild(ellipsisStart);
            } else if (i == totalPages - 1 && currentPage < totalPages - 3) {
                // Display ellipsis after page totalPages - 2 if current page < totalPages - 3
                var ellipsisEnd = document.createElement('span');
                ellipsisEnd.textContent = '...';
                pageNumbers.appendChild(ellipsisEnd);
            }
        }
    }

    // Disable previous button on the first page
    document.getElementById('previousPage').disabled = (currentPage === 1);
    // Disable next button on the last page
    document.getElementById('nextPage').disabled = (currentPage === totalPages);
}


function previousPage() {
    if (currentPage > 1) {
        currentPage--;
        filterAndPaginateTable();
    }
}

function nextPage() {
    var rating = document.getElementById('ratingFilter').value;
    var table = document.getElementById('ratingTableBody');
    var rows = table.getElementsByTagName('tr');
    var filteredRows = [];

    for (var i = 0; i < rows.length; i++) {
        var valueFood = rows[i].getAttribute('data-value-food');
        var valueService = rows[i].getAttribute('data-value-service');
        var shouldDisplay = (rating === 'all') || (valueFood === rating) || (valueService === rating);

        if (shouldDisplay) {
            filteredRows.push(rows[i]);
        }
    }

    var totalPages = Math.ceil(filteredRows.length / pageSize);

    if (currentPage < totalPages) {
        currentPage++;
        filterAndPaginateTable();
    }
}

function triggerFilterAndPagination() {
    currentPage = 1; // Reset current page
    filterAndPaginateTable(); // Filter and paginate table
}

// Trigger filter and pagination when page loads
triggerFilterAndPagination();
