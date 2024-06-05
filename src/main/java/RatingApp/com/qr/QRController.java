package RatingApp.com.qr;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import RatingApp.com.entities.RatingStar;
import RatingApp.com.repository.RatingAppRepository;

@Controller
@RequestMapping("/qr")
public class QRController {
	
	@Autowired
	RatingAppRepository appRepository;
	
	@GetMapping("/home")
	public String home1() {
		return "QR/qr";
	}
	
	@PostMapping("/home")
	public String saveQR(@ModelAttribute("userRate")RatingStar rating,Model model) {
		
		if(rating.getValueFood() <= 3 || rating.getValueService() <= 3) {
			String department = "QR";
			Calendar calendar = Calendar.getInstance();

			// Set the timezone offset to UTC (or any other desired timezone)
			calendar.setTimeZone(TimeZone.getTimeZone("GMT+7"));
			calendar.add(Calendar.HOUR_OF_DAY, 7);
			Date currentTime = calendar.getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM ----------------- h:mm a");
			rating.setDateRate(formatter.format(currentTime));
			rating.setDepartment(department);
			appRepository.save(rating);
			return "/QR/qr3";
		}
		
		String department = "QR";
		Calendar calendar = Calendar.getInstance();

		// Set the timezone offset to UTC (or any other desired timezone)
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		calendar.add(Calendar.HOUR_OF_DAY, 7);
		Date currentTime = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM ----------------- h:mm a");
		rating.setDateRate(formatter.format(currentTime));
		rating.setDepartment(department);
		appRepository.save(rating);
		
		System.out.println(rating.getValueFood());
		System.out.println(rating.getValueService());
		
		return "redirect:/qr/home1";
	}
	
	@GetMapping("/home1")
	public String home2() {
		return "QR/qr2";
	}
	
	@GetMapping("/home2")
	public String home3() {
		return "QR/qr3";
	}
	
	@GetMapping("/reportQR")
	public String reportQR() {
		return "QR/reportQR";
	}
	

}