package RatingApp.com.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import RatingApp.com.entities.ChartData;
import RatingApp.com.repository.RatingAppRepository;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class ChartSelectApiController {
	
	@Autowired
	RatingAppRepository appRepository;
	
	
	@GetMapping("/chart/department")
    public ChartData getChartDataDeparment(HttpSession httpSession) {
        // Format the date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
        List<String> weekDays = new ArrayList<>();
        LocalDate today = LocalDate.now();
        // Get the first day of the week
        LocalDate firstDayOfWeek = today.with(DayOfWeek.MONDAY);

        // Iterate over each day of the week and add it to the list
        for (int i = 0; i < 7; i++) {
            LocalDate dayOfWeek = firstDayOfWeek.plusDays(i);
            weekDays.add(dayOfWeek.format(formatter));
        }
        
        String data = (String) httpSession.getAttribute("countryList");
        
        // Fetch data for each day of the week
        int[] earnings = new int[7];
        for (int i = 0; i < 7; i++) {
            LocalDate day = firstDayOfWeek.plusDays(i);
            earnings[i] = appRepository.countAllByDateRateLikeAndDepartment(day.format(formatter),data);
        }

        // Mock data for chart
        ChartData chartData = new ChartData();
        chartData.setEarnings(earnings);
        chartData.setExpenses(new int[] {});
        // You can add more data properties as needed
        System.out.println(chartData.toString());
        return chartData;
    }
	
	@GetMapping("/countryDB")
    public List<String> updateCountries() {
        // Retrieve updated data from your service
        List<String> newData = appRepository.findAllDepartments();
        
        // Return the updated data as a JSON response
        return newData;
    }

}
