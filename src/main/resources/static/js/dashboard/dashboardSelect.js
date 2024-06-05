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
		url: "/api/chart/department", // Replace this with your actual endpoint URL
		method: "GET",
		success: function(data) {
			// Once the data is successfully retrieved, you can use it to update your charts
			updateCharts(data, categories);
			
		},
		error: function(xhr, status, error) {
			// Handle errors if any
			console.error("Error fetching data:", error);
		}
	})



	//update chart 
	function updateCharts(data, categories) {
		var chart = {
			series: [
				{ name: "Earnings this month:", data: data.earnings },
				{ name: "Expense this month:", data: data.expenses },
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
					columnWidth: "35%",
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

		var chart = new ApexCharts(document.querySelector("#chartDashboard"), chart);
		chart.render();

		// =====================================
		// Breakup
		// =====================================
		var breakup = {
			color: "#adb5bd",
			series: [80, 40, 25],
			labels: ["2022", "2021", "2020"],
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
						size: '75%',
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
			colors: ["#5D87FF", "#ecf2ff", "#F9F9FD"],

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

document.addEventListener("DOMContentLoaded", function() {
        // Select the form element
        var form = document.getElementById("dateForm");
        
        // Submit the form automatically when the date input changes
        form.addEventListener("change", function() {
            form.submit(); // Trigger form submission
        });
    });