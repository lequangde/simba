package RatingApp.com.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import RatingApp.com.entities.ChartData;
import RatingApp.com.entities.UserDetail;
import RatingApp.com.repository.RatingAppRepository;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/data")
public class ChartApiController {

	@Autowired
	RatingAppRepository appRepository;

	@GetMapping("/chart")
    public ChartData getChartData(HttpSession httpSession) {
		
        // Format the date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
        List<String> weekDays = new ArrayList<>();
        LocalDate today = LocalDate.now();
        
        UserDetail user = (UserDetail) httpSession.getAttribute("session");
        
        // Get the first day of the week
        LocalDate firstDayOfWeek = today.with(DayOfWeek.MONDAY);

        // Iterate over each day of the week and add it to the list
        for (int i = 0; i < 7; i++) {
            LocalDate dayOfWeek = firstDayOfWeek.plusDays(i);
            weekDays.add(dayOfWeek.format(formatter));
        }

        // Fetch data for each day of the week
        int[] earnings = new int[7];
        for (int i = 0; i < 7; i++) {
            LocalDate day = firstDayOfWeek.plusDays(i);
            if(user.getUserName().equals("admin")) {
            	earnings[i] = appRepository.countAllByDateRateLike(day.format(formatter));
            }else {
            	String department = user.getDepartment();
            	earnings[i] = appRepository.countAllByDateRateLikeAndDepartment(day.format(formatter),department);
            }
        }
        
        String[] department = appRepository.findAllDepartmentAndDateRateLike(formatter.format(today));
        int[] breakUp = appRepository.getAllRatingWithDateRate(formatter.format(today));
        
        // Mock data for chart
        ChartData chartData = new ChartData();
        chartData.setEarnings(earnings);
        chartData.setExpenses(new int[] {});
        chartData.setBreakUp(breakUp);
        chartData.setDepartment(department);
        // You can add more data properties as needed

        return chartData;
    }
	
	

}
