package RatingApp.com;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import RatingApp.com.entities.RatingStar;
import RatingApp.com.repository.ExcelRepository;
import RatingApp.com.repository.RatingAppRepository;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private RatingAppRepository appRepository;
	
	@Autowired
	private ExcelRepository excelRepository;

//	@Test
//	void contextLoads() {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
//		
//		// Day
//		List<String> weekDays = new ArrayList<>();
//        LocalDate today = LocalDate.now(); // Today
//        DayOfWeek firstDay = DayOfWeek.of(1); // Monday
//        LocalDate firstDayOfWeek = today.with(firstDay);
//        String day1 = today.format(formatter);
//        System.out.println(day1);
//        
//
//        // Iterate over each day of the week and add it to the list
//        for (int i = 0; i < 7; i++) {
//            LocalDate dayOfWeek = firstDayOfWeek.plusDays(i);
//            weekDays.add(dayOfWeek.format(formatter));
//            
//        }
//        Date currentTime = Calendar.getInstance().getTime();
//        SimpleDateFormat formattere = new SimpleDateFormat("dd/MM -------- HH:mm");
//        String formattedTime = formattere.format(currentTime);
//        System.out.println(formattedTime + "time");
//	}
//	
//	@Test
//	void testCurrentDay() {
//		String date = "07/05"; // Change this to the date you want to filter
//        List<Object[]> results = appRepository.countByDepartmentAndDateRateLike(date);
//        
//        for (Object[] result : results) {
//            String department = (String) result[0];
//            Long count = (Long) result[1];
//            System.out.println("Department: " + department + ", Count: " + count);
//        }
//	}
	
	public static String convertAddress(String address) {
        // Split the input into parts by spaces
        String[] parts = address.split(" ");

        // The first part is the number
        String number = parts[0];

        // Collect the initials of each of the remaining parts
        StringBuilder initials = new StringBuilder();
        for (int i = 1; i < parts.length; i++) {
            if (!parts[i].isEmpty()) {
                initials.append(parts[i].charAt(0));
            }
        }

        // Concatenate the number and the initials
        return number + initials.toString();
    }
	
	@Test
	void testGetDate() {
		  String input = "57 Trần Xuân Soạn";
	        String output = convertAddress(input);
	        System.out.println(output);
	}
	
	@Test
    public void testGetRatings() {
       
		List<Object[]> list = appRepository.countStarFoodByDepartmentAndDate("24/05", 3);
		for (Object[] objects : list) {
			System.out.println(objects[0] + " "+ objects[1]);
		}


    }
}
