$(function() {
	
	///----------------Time current ------------------------------------- 
	function generateWeeklyTimeData() {
    var currentDate = moment().startOf('isoWeek'); // Start from the beginning of ISO week
    var endDate = moment().endOf('isoWeek'); // End at the end of ISO week
    var result = [];

    while (currentDate <= endDate) {
        result.push(currentDate.format('DD/MM'));
        currentDate.add(1, 'days'); // Increment by one day
    }

    return result;
}
  
 var categories = generateWeeklyTimeData();
	


	//-------------- data from api ----------------------------------------
	$.ajax({
		url: "/api/data/chart", // Replace this with your actual endpoint URL
		method: "GET",
		success: function(data) {
			var breakUpData = data.breakUp;
			var departmentName = data.department;
			updateCharts(data, categories,breakUpData,departmentName);
		},
		error: function(xhr, status, error) {
			// Handle errors if any
			console.error("Error fetching data:", error);
		}
	})



	//update chart 
	function updateCharts(data, categories,breakUpData,departmentName) {
		var chart = {
			series: [
				{ 
					name: "Tổng đánh giá", 
				    data: data.earnings 
				  }
			],

			chart: {
				type: "bar",
				height: 345,
				offsetX: -15,
				toolbar: { show: true },
				foreColor: "#adb0bb",
				fontFamily: 'inherit',
				sparkline: { enabled: false },
			},

			colors: ["#5D87FF", "#49BEFF"],


			plotOptions: {
				bar: {
					horizontal: false,
					columnWidth: "30%",
					borderRadius: [6],
					borderRadiusApplication: 'end',
					borderRadiusWhenStacked: 'all'
				},
			},
			markers: { size: 0 },

			dataLabels: {
				enabled: false,
			},


			legend: {
				show: false,
			},


			grid: {
				borderColor: "rgba(0,0,0,0.1)",
				strokeDashArray: 3,
				xaxis: {
					lines: {
						show: false,
					},
				},
			},


			xaxis: {
				type: "category",
				categories: categories,
				labels: {
					style: { cssClass: "black--text lighten-2--text fill-color" },
				},
			},


			yaxis: {
				show: true,
				min: 0,
				max: 400,
				tickAmount: 4,
				labels: {
					style: {
						cssClass: "grey--text lighten-2--text fill-color",
					},
				},
			},
			stroke: {
				show: true,
				width: 3,
				lineCap: "butt",
				colors: ["transparent"],
			},


			tooltip: { theme: "light" },

			responsive: [
				{
					breakpoint: 600,
					options: {
						plotOptions: {
							bar: {
								borderRadius: 3,
							}
						},
					}
				}
			]


		};

		var chart = new ApexCharts(document.querySelector("#chart"), chart);
		chart.render();

		// =====================================
		// Breakup
		// =====================================
		var breakup = {
			color: "#adb5bd",
			series: breakUpData,
			labels: departmentName,
			chart: {
				width: 180,
				type: "donut",
				fontFamily: "Plus Jakarta Sans', sans-serif",
				foreColor: "#adb0bb",
			},
			plotOptions: {
				pie: {
					startAngle: 0,
					endAngle: 360,
					donut: {
						size: '1%',
					},
				},
			},
			stroke: {
				show: false,
			},

			dataLabels: {
				enabled: false,
			},

			legend: {
				show: false,
			},
			colors: ["#4D6AFF", "#13deb9", "#FF4DC0","#FFC04D","#4DFF6A","#DE2714"],

			responsive: [
				{
					breakpoint: 991,
					options: {
						chart: {
							width: 150,
						},
					},
				},
			],
			tooltip: {
				theme: "dark",
				fillSeriesColor: false,
			},
		};

		var chart = new ApexCharts(document.querySelector("#breakup"), breakup);
		chart.render();



		// =====================================
		// Earning
		// =====================================
		var earning = {
			chart: {
				id: "sparkline3",
				type: "area",
				height: 60,
				sparkline: {
					enabled: true,
				},
				group: "sparklines",
				fontFamily: "Plus Jakarta Sans', sans-serif",
				foreColor: "#adb0bb",
			},
			series: [
				{
					name: "Earnings",
					color: "#49BEFF",
					data: data.earnings,
				},
			],
			stroke: {
				curve: "smooth",
				width: 2,
			},
			fill: {
				colors: ["#f3feff"],
				type: "solid",
				opacity: 0.05,
			},

			markers: {
				size: 0,
			},
			tooltip: {
				theme: "dark",
				fixed: {
					enabled: true,
					position: "right",
				},
				x: {
					show: false,
				},
			},
		};
		new ApexCharts(document.querySelector("#earning"), earning).render();
	}

})



var isAdminValue = document.getElementById("role").textContent.trim();

    // Convert the string value to a boolean
    var isAdmin = isAdminValue === "true";
	console.log(isAdmin)
    // Show or hide content based on the isAdmin value
    if (isAdmin) {
        document.getElementById("content-select").style.display = "block";
    } else if(!(isAdmin)) {
        document.getElementById("content-select").classList.add("hidden");
    }
    
    document.addEventListener("DOMContentLoaded", function() {
        // Select the form element
        var form = document.getElementById("dateForm");
        
        // Submit the form automatically when the date input changes
        form.addEventListener("change", function() {
            form.submit(); // Trigger form submission
        });
    });

 function getColor(index) {
        var colors = ["#FF5733", "#33FF57", "#3366FF", "#FF33CC"]; // Add more colors as needed
        return colors[index % colors.length];
    }    
    
    // Array of colors
const colors = ["#4D6AFF", "#13deb9", "#FF4DC0", "#FFC04D", "#4DFF6A", "#DE2714"];

// Get the container element
const resultsContainer = document.getElementById('resultsContainer');

// Get all elements with the class 'round-8'
const roundElements = resultsContainer.getElementsByClassName('round-8');

// Loop through the round elements and apply the colors
for (let i = 0; i < roundElements.length; i++) {
    const colorIndex = i % colors.length; // Loop through colors if there are more elements than colors
    roundElements[i].style.backgroundColor = colors[colorIndex];
}
    