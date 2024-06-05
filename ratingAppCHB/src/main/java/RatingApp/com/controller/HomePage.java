package RatingApp.com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePage {

	@RequestMapping("/")
	public String homePage() {
		return "index";
	}
	
	@RequestMapping("/dashboard")
	public String homePageDashboard() {
		return "dashboard";
	}
	
}
