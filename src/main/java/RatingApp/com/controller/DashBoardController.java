package RatingApp.com.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import RatingApp.com.entities.RatingStar;
import RatingApp.com.entities.UserDetail;
import RatingApp.com.repository.RatingAppRepository;
import RatingApp.com.repository.UserDetailRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/home")
public class DashBoardController {

	@Autowired
	ChartSelectApiController boardController;

	@Autowired
	UserDetailRepository detailRepository;

	@Autowired
	RatingAppRepository appRepository;

	@GetMapping("/dashboard")
	public String homePageDashboardTemplate(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {
		UserDetail user = (UserDetail) session.getAttribute("session");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");

		Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
		LocalDate today = LocalDate.now();
		LocalDate firstDayOfWeek = today.with(DayOfWeek.MONDAY);
		LocalDate lastDayOfWeek = today.with(DayOfWeek.SUNDAY);

		String firstDay = firstDayOfWeek.format(formatter);
		String lastDay = lastDayOfWeek.format(formatter);

		String currentDay = today.format(formatter);

		// Authentication Handler
		if (user != null) {
			model.addAttribute("session", user);
			model.addAttribute("user", user.getDepartment());
			model.addAttribute("firstDay", firstDay);
			model.addAttribute("lastDay", lastDay);

			if (user.getUserName().equals("admin")) {
				model.addAttribute("isAdmin", true);

				// Get all rate of all department today
				List<Object[]> results = appRepository.countByDepartmentAndDateRateLike(currentDay);
				model.addAttribute("resultsRate", results);

				// Total rate of all department
				int totalRateByDay = appRepository.countAllByDateRateLike(currentDay);
				model.addAttribute("totalRatingByDay", totalRateByDay + " " + "Lượt");

				// Get recent rate from today
				Page<RatingStar> recentRate = appRepository.findAllByOrderByRateIdDesc(pageable);
				model.addAttribute("recentRate", recentRate);

				// Count all
				int countRating = (int) appRepository.countAllByToday(currentDay);
				model.addAttribute("countRating", countRating);

				// Value food
				int countRatingFood1 = (int) appRepository.countByValueFood(1, currentDay);
				model.addAttribute("countRatingFood1", countRatingFood1);
				int countRatingFood2 = (int) appRepository.countByValueFood(2, currentDay);
				model.addAttribute("countRatingFood2", countRatingFood2);
				int countRatingFood3 = (int) appRepository.countByValueFood(3, currentDay);
				model.addAttribute("countRatingFood3", countRatingFood3);
				int countRatingFood4 = (int) appRepository.countByValueFood(4, currentDay);
				model.addAttribute("countRatingFood4", countRatingFood4);
				int countRatingFood5 = (int) appRepository.countByValueFood(5, currentDay);
				model.addAttribute("countRatingFood5", countRatingFood5);

				// Value service
				int countRatingService1 = (int) appRepository.countByValueService(1, currentDay);
				model.addAttribute("countRatingService1", countRatingService1);
				int countRatingService2 = (int) appRepository.countByValueService(2, currentDay);
				model.addAttribute("countRatingService2", countRatingService2);
				int countRatingService3 = (int) appRepository.countByValueService(3, currentDay);
				model.addAttribute("countRatingService3", countRatingService3);
				int countRatingService4 = (int) appRepository.countByValueService(4, currentDay);
				model.addAttribute("countRatingService4", countRatingService4);
				int countRatingService5 = (int) appRepository.countByValueService(5, currentDay);
				model.addAttribute("countRatingService5", countRatingService5);
				
				// ....
				
				
				
				List<Object[]> star2 = appRepository.findDepartmentAndStarFoodCount(currentDay, 4);
				model.addAttribute("star2", star2);

			} else {
				model.addAttribute("isAdmin", false);
				String department = user.getDepartment();

				List<Object[]> results = appRepository.countByDepartmentAndDateRateLikeAndDepartment(currentDay,
						department);
				model.addAttribute("resultsRate", results);

				Page<RatingStar> recentRate = appRepository.findByDepartmentOrderByRateIdDesc(pageable, department);
				model.addAttribute("recentRate", recentRate);

				// Count all by department
				int countRating = (int) appRepository.countByDepartment(department, currentDay);
				model.addAttribute("countRating", countRating);

				// Value food
				int countRatingFood1 = (int) appRepository.countByValueFood(1, currentDay, department);
				model.addAttribute("countRatingFood1", countRatingFood1);
				int countRatingFood2 = (int) appRepository.countByValueFood(2, currentDay, department);
				model.addAttribute("countRatingFood2", countRatingFood2);
				int countRatingFood3 = (int) appRepository.countByValueFood(3, currentDay, department);
				model.addAttribute("countRatingFood3", countRatingFood3);
				int countRatingFood4 = (int) appRepository.countByValueFood(4, currentDay, department);
				model.addAttribute("countRatingFood4", countRatingFood4);
				int countRatingFood5 = (int) appRepository.countByValueFood(5, currentDay, department);
				model.addAttribute("countRatingFood5", countRatingFood5);

				// Value service
				int countRatingService1 = (int) appRepository.countByValueService(1, currentDay, department);
				model.addAttribute("countRatingService1", countRatingService1);
				int countRatingService2 = (int) appRepository.countByValueService(2, currentDay, department);
				model.addAttribute("countRatingService2", countRatingService2);
				int countRatingService3 = (int) appRepository.countByValueService(3, currentDay, department);
				model.addAttribute("countRatingService3", countRatingService3);
				int countRatingService4 = (int) appRepository.countByValueService(4, currentDay, department);
				model.addAttribute("countRatingService4", countRatingService4);
				int countRatingService5 = (int) appRepository.countByValueService(5, currentDay, department);
				model.addAttribute("countRatingService5", countRatingService5);

			}

		} else {
			return "/auth/login";
		}

		return "dashboard/dashboardTemplate";
	}

	// Handler login
	@PostMapping("/sign-in")
	public String login(@ModelAttribute("userInfo") UserDetail userDetail, Model model, HttpSession httpSession) {

		UserDetail userDB = detailRepository.findByUserName(userDetail.getUserName());
		if (userDB != null) {
			httpSession.setAttribute("session", userDB);
			// Authentication successful, redirect to dashboard
			return "redirect:/home/dashboard";
		} else {

			// Authentication failed, add error message to model and return login page
			model.addAttribute("error", "Invalid username or password");
			System.out.println("failed");
			return "/auth/login";
		}

	}

	// Get country selected
	@PostMapping("/selected-country")
	public String getChartData(HttpSession httpSession, @RequestBody MultiValueMap<String, String> formData,
			RedirectAttributes redirectAttributes) {
		String selectedCountry = formData.getFirst("selectedCountry");
		// Format the date
		System.out.println(selectedCountry);
		System.out.println(formData.size());
		httpSession.setAttribute("countryList", selectedCountry);

		return "/dashboard/dashboardTemplateWithChart";
	}

	public static String formatDate(String dateString) {
		// Parse the input date string
		LocalDate date = LocalDate.parse(dateString);

		// Create a formatter to format the date as "DD/MM"
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");

		// Format the date
		String formattedDate = date.format(formatter);

		// Return the formatted date
		return formattedDate;
	}

	// Get date selected
	@PostMapping("/date")
	public String getDate(@RequestParam("dateSelect") String date, Model model, HttpSession session) {
		String formattedDate = formatDate(date);

		List<Object[]> results = appRepository.countByDepartmentAndDateRateLike(formattedDate);
		model.addAttribute("resultsRateWithEachDepartment", results);
		model.addAttribute("selectedDate", formattedDate);

		UserDetail user = (UserDetail) session.getAttribute("session");
		String department = user.getDepartment();

		if (user.getUserName().equals("admin")) {
			int countByDate = appRepository.countAllByDateRateLike(formattedDate);
			model.addAttribute("totalRatingByDay", countByDate + " " + "Lượt");


			// Value food
			int countRatingFood1 = (int) appRepository.countByValueFood(1, formattedDate);
			model.addAttribute("countRatingFood1", countRatingFood1);
			int countRatingFood2 = (int) appRepository.countByValueFood(2, formattedDate);
			model.addAttribute("countRatingFood2", countRatingFood2);
			int countRatingFood3 = (int) appRepository.countByValueFood(3, formattedDate);
			model.addAttribute("countRatingFood3", countRatingFood3);
			int countRatingFood4 = (int) appRepository.countByValueFood(4, formattedDate);
			model.addAttribute("countRatingFood4", countRatingFood4);
			int countRatingFood5 = (int) appRepository.countByValueFood(5, formattedDate);
			model.addAttribute("countRatingFood5", countRatingFood5);

			// Value service
			int countRatingService1 = (int) appRepository.countByValueService(1, formattedDate);
			model.addAttribute("countRatingService1", countRatingService1);
			int countRatingService2 = (int) appRepository.countByValueService(2, formattedDate);
			model.addAttribute("countRatingService2", countRatingService2);
			int countRatingService3 = (int) appRepository.countByValueService(3, formattedDate);
			model.addAttribute("countRatingService3", countRatingService3);
			int countRatingService4 = (int) appRepository.countByValueService(4, formattedDate);
			model.addAttribute("countRatingService4", countRatingService4);
			int countRatingService5 = (int) appRepository.countByValueService(5, formattedDate);
			model.addAttribute("countRatingService5", countRatingService5);
			
			
			//Filter food by date and rate
			List<Object[]> filterFoodBy1Star = appRepository.countStarFoodByDepartmentAndDate(formattedDate, 1);
			model.addAttribute("filterFood1Star", filterFoodBy1Star);
			
			List<Object[]> filterFoodBy2Star = appRepository.countStarFoodByDepartmentAndDate(formattedDate, 2);
			model.addAttribute("filterFood2Star", filterFoodBy2Star);
			
			List<Object[]> filterFoodBy3Star = appRepository.countStarFoodByDepartmentAndDate(formattedDate, 3);
			model.addAttribute("filterFood3Star", filterFoodBy3Star);
			
			List<Object[]> filterFoodBy4Star = appRepository.countStarFoodByDepartmentAndDate(formattedDate, 4);
			model.addAttribute("filterFood4Star", filterFoodBy4Star);
			
			List<Object[]> filterFoodBy5Star = appRepository.countStarFoodByDepartmentAndDate(formattedDate, 5);
			model.addAttribute("filterFood5Star", filterFoodBy5Star);
			
			//Filter service by date and rate
			List<Object[]> filterServiceBy1Star = appRepository.countStarServiceByDepartmentAndDate(formattedDate, 1);
			model.addAttribute("filterService1Star", filterServiceBy1Star);
			
			List<Object[]> filterServiceBy2Star = appRepository.countStarServiceByDepartmentAndDate(formattedDate, 2);
			model.addAttribute("filterService2Star", filterServiceBy2Star);
			
			List<Object[]> filterServiceBy3Star = appRepository.countStarServiceByDepartmentAndDate(formattedDate, 3);
			model.addAttribute("filterService3Star", filterServiceBy3Star);
			
			List<Object[]> filterServiceBy4Star = appRepository.countStarServiceByDepartmentAndDate(formattedDate, 4);
			model.addAttribute("filterService4Star", filterServiceBy4Star);
			
			List<Object[]> filterServiceBy5Star = appRepository.countStarServiceByDepartmentAndDate(formattedDate, 5);
			model.addAttribute("filterService5Star", filterServiceBy5Star);
			
			

		} else {
			int countByDate = appRepository.countAllByDateRateLike(formattedDate, department);
			model.addAttribute("totalRatingByDay", countByDate + " " + "Lượt");

			List<Object[]> resultsByDate = appRepository.countByDepartmentAndDateRateLikeAndDepartment(formattedDate,
					department);
			model.addAttribute("resultsRate", resultsByDate);

			// Count all
			int countRating = (int) appRepository.countByDepartment(department, formattedDate);
			model.addAttribute("countRating", countRating);

			// Value food
			int countRatingFood1 = (int) appRepository.countByValueFood(1, formattedDate, department);
			model.addAttribute("countRatingFood1", countRatingFood1);
			int countRatingFood2 = (int) appRepository.countByValueFood(2, formattedDate, department);
			model.addAttribute("countRatingFood2", countRatingFood2);
			int countRatingFood3 = (int) appRepository.countByValueFood(3, formattedDate, department);
			model.addAttribute("countRatingFood3", countRatingFood3);
			int countRatingFood4 = (int) appRepository.countByValueFood(4, formattedDate, department);
			model.addAttribute("countRatingFood4", countRatingFood4);
			int countRatingFood5 = (int) appRepository.countByValueFood(5, formattedDate, department);
			model.addAttribute("countRatingFood5", countRatingFood5);

			// Value service
			int countRatingService1 = (int) appRepository.countByValueService(1, formattedDate, department);
			model.addAttribute("countRatingService1", countRatingService1);
			int countRatingService2 = (int) appRepository.countByValueService(2, formattedDate, department);
			model.addAttribute("countRatingService2", countRatingService2);
			int countRatingService3 = (int) appRepository.countByValueService(3, formattedDate, department);
			model.addAttribute("countRatingService3", countRatingService3);
			int countRatingService4 = (int) appRepository.countByValueService(4, formattedDate, department);
			model.addAttribute("countRatingService4", countRatingService4);
			int countRatingService5 = (int) appRepository.countByValueService(5, formattedDate, department);
			model.addAttribute("countRatingService5", countRatingService5);

		}

		return "/dashboard/dashboardTemplateWithChart";
	}

}
