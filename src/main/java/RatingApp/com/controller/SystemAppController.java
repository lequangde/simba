package RatingApp.com.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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

import RatingApp.com.entities.CriteriaDetail;
import RatingApp.com.entities.RatingStar;
import RatingApp.com.entities.UserDetail;
import RatingApp.com.repository.CriteriaDetailRepository;
import RatingApp.com.repository.RatingAppRepository;
import RatingApp.com.repository.UserDetailRepository;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/app")
public class SystemAppController {

	@Autowired
	UserDetailRepository detailRepository;

	@Autowired
	RatingAppRepository appRepository;
	
	@Autowired
	CriteriaDetailRepository criteriaDetailRepository;

	// Department Info
	@GetMapping("/infoRate/department")
	public String dataListDepartment(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize, Model model, HttpSession session,
			@ModelAttribute("data") String dataFront) {

		String data = (String) session.getAttribute("data");

		Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
		Page<RatingStar> pageUserDetail2 = appRepository.findAllByDepartmentOrderByRateIdDesc(pageable, data);
		
		// Count all by department
		int countRating = (int) appRepository.countByDepartment(data);
		model.addAttribute("countRating", countRating);
		
		// Value food and department with role Employee
		int countRatingFood1 = (int) appRepository.countByValueFoodAndDepartment(1, data);
		model.addAttribute("countRatingFood1", countRatingFood1);
		int countRatingFood2 = (int) appRepository.countByValueFoodAndDepartment(2, data);
		model.addAttribute("countRatingFood2", countRatingFood2);
		int countRatingFood3 = (int) appRepository.countByValueFoodAndDepartment(3, data);
		model.addAttribute("countRatingFood3", countRatingFood3);
		int countRatingFood4 = (int) appRepository.countByValueFoodAndDepartment(4, data);
		model.addAttribute("countRatingFood4", countRatingFood4);
		int countRatingFood5 = (int) appRepository.countByValueFoodAndDepartment(5, data);
		model.addAttribute("countRatingFood5", countRatingFood5);

		// Value service and department with role Employee
		int countRatingService1 = (int) appRepository.countByValueServiceAndDepartment(1, data);
		model.addAttribute("countRatingService1", countRatingService1);
		int countRatingService2 = (int) appRepository.countByValueServiceAndDepartment(2, data);
		model.addAttribute("countRatingService2", countRatingService2);
		int countRatingService3 = (int) appRepository.countByValueServiceAndDepartment(3, data);
		model.addAttribute("countRatingService3", countRatingService3);
		int countRatingService4 = (int) appRepository.countByValueServiceAndDepartment(4, data);
		model.addAttribute("countRatingService4", countRatingService4);
		int countRatingService5 = (int) appRepository.countByValueServiceAndDepartment(5, data);
		model.addAttribute("countRatingService5", countRatingService5);

		model.addAttribute("pageDepartment", pageUserDetail2);
		model.addAttribute("totalPages", pageUserDetail2.getTotalPages());
		model.addAttribute("enable", "enable");

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("pageSize", pageSize);
		System.out.println("data " + data);
		return "system/department_info";
	}

	// Rate List
	@GetMapping("/infoRate")
	public String CustomerList(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize, Model model, HttpSession session,
			@ModelAttribute("data") String data) {
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize);

		// Authentication Handler
		UserDetail user = (UserDetail) session.getAttribute("session");
		if (user == null) {
			return "/auth/login";
		}

		// Admin and Employee Role
		if (user.getUserName().equals("admin")) {
			
			// Count all
			int countRating = (int) appRepository.count();
			model.addAttribute("countRating", countRating);

			// Value food
			int countRatingFood1 = (int) appRepository.countByValueFood(1);
			model.addAttribute("countRatingFood1", countRatingFood1);
			int countRatingFood2 = (int) appRepository.countByValueFood(2);
			model.addAttribute("countRatingFood2", countRatingFood2);
			int countRatingFood3 = (int) appRepository.countByValueFood(3);
			model.addAttribute("countRatingFood3", countRatingFood3);
			int countRatingFood4 = (int) appRepository.countByValueFood(4);
			model.addAttribute("countRatingFood4", countRatingFood4);
			int countRatingFood5 = (int) appRepository.countByValueFood(5);
			model.addAttribute("countRatingFood5", countRatingFood5);

			// Value service
			int countRatingService1 = (int) appRepository.countByValueService(1);
			model.addAttribute("countRatingService1", countRatingService1);
			int countRatingService2 = (int) appRepository.countByValueService(2);
			model.addAttribute("countRatingService2", countRatingService2);
			int countRatingService3 = (int) appRepository.countByValueService(3);
			model.addAttribute("countRatingService3", countRatingService3);
			int countRatingService4 = (int) appRepository.countByValueService(4);
			model.addAttribute("countRatingService4", countRatingService4);
			int countRatingService5 = (int) appRepository.countByValueService(5);
			model.addAttribute("countRatingService5", countRatingService5);

			if (!(data.equals(""))) {
				session.setAttribute("data", data);
				model.addAttribute("admin", "admin");
				return "redirect:/app/infoRate/department";
			}

			if (data.equals("")) {
				Page<RatingStar> pageUserDetail = appRepository.findAllByOrderByRateIdDesc(pageable);
				model.addAttribute("totalPages", pageUserDetail.getTotalPages());
				model.addAttribute("pageStar", pageUserDetail);
			}

		} else if (!(user.getUserName().equals("admin"))) {
			String department = user.getDepartment();
			Page<RatingStar> pageUserDetail = appRepository.findAllByDepartmentOrderByRateIdDesc(pageable, department);
			model.addAttribute("totalPages", pageUserDetail.getTotalPages());
			model.addAttribute("pageStar", pageUserDetail);
			
			// Count all by department
			int countRating = (int) appRepository.countByDepartment(department);
			model.addAttribute("countRating", countRating);

			// Value food and department with role Employee
			int countRatingFood1 = (int) appRepository.countByValueFoodAndDepartment(1, department);
			model.addAttribute("countRatingFood1", countRatingFood1);
			int countRatingFood2 = (int) appRepository.countByValueFoodAndDepartment(2, department);
			model.addAttribute("countRatingFood2", countRatingFood2);
			int countRatingFood3 = (int) appRepository.countByValueFoodAndDepartment(3, department);
			model.addAttribute("countRatingFood3", countRatingFood3);
			int countRatingFood4 = (int) appRepository.countByValueFoodAndDepartment(4, department);
			model.addAttribute("countRatingFood4", countRatingFood4);
			int countRatingFood5 = (int) appRepository.countByValueFoodAndDepartment(5, department);
			model.addAttribute("countRatingFood5", countRatingFood5);

			// Value service and department with role Employee
			int countRatingService1 = (int) appRepository.countByValueServiceAndDepartment(1, department);
			model.addAttribute("countRatingService1", countRatingService1);
			int countRatingService2 = (int) appRepository.countByValueServiceAndDepartment(2, department);
			model.addAttribute("countRatingService2", countRatingService2);
			int countRatingService3 = (int) appRepository.countByValueServiceAndDepartment(3, department);
			model.addAttribute("countRatingService3", countRatingService3);
			int countRatingService4 = (int) appRepository.countByValueServiceAndDepartment(4, department);
			model.addAttribute("countRatingService4", countRatingService4);
			int countRatingService5 = (int) appRepository.countByValueServiceAndDepartment(5, department);
			model.addAttribute("countRatingService5", countRatingService5);

		}

		model.addAttribute("currentPage", pageNum);
		return "system/info_star_rate";
	}


	// Transition page 1
	@GetMapping("/rating")
	public String ratePage(HttpSession session, Model model) {

		// Authentication Handler
		UserDetail user = (UserDetail) session.getAttribute("session");
		if (user == null) {
			return "/auth/login";
		}

		return "app/index";
	}

	// Saving rating from customers
	@PostMapping("/rating")
	public String rate(@ModelAttribute("userRate") RatingStar rating, @ModelAttribute("userDetail") UserDetail detail,
			HttpSession httpSession, Model model,
			RedirectAttributes attributes 
			) {

		if (rating.getValueFood() == 0 || rating.getValueService() == 0) {
			return "app/index";
		}
		
		if (rating.getValueFood() <= 3 || rating.getValueService() <= 3) {
			UserDetail user = (UserDetail) httpSession.getAttribute("session");

			// Save Rating
			String department = user.getDepartment();
			Calendar calendar = Calendar.getInstance();

			// Set the timezone offset to UTC (or any other desired timezone)
			calendar.setTimeZone(TimeZone.getTimeZone("GMT+7"));
			calendar.add(Calendar.HOUR_OF_DAY, 7);
			Date currentTime = calendar.getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM ----------------- h:mm a");
			rating.setDateRate(formatter.format(currentTime));
			rating.setDepartment(department);
			httpSession.setAttribute("userRate", rating);
			return "app/index3";
		}
		
		UserDetail user = (UserDetail) httpSession.getAttribute("session");

		// Save Rating
		String department = user.getDepartment();
		Calendar calendar = Calendar.getInstance();

		// Set the timezone offset to UTC (or any other desired timezone)
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		calendar.add(Calendar.HOUR_OF_DAY, 7);
		Date currentTime = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM ----------------- h:mm a");
		rating.setDateRate(formatter.format(currentTime));
		rating.setDepartment(department);
		appRepository.save(rating);
		
		
		System.out.println("Received rating: " + rating.getValueFood() + " + " + rating.getValueService());
		return "redirect:/app/rate";
	}
	
	// Rate page criteria
	@GetMapping("/rate3")
	public String homePage3(HttpSession session, Model model
			) {
		// Authentication Handler
		UserDetail user = (UserDetail) session.getAttribute("session");
		if (user == null) {
			return "/auth/login";
		}
		

		return "app/index3";
	}
	
	@PostMapping("/rate3")
	public String homePage3(
			@ModelAttribute("criteriaDetail") CriteriaDetail criteriaDetail,
			HttpSession session, Model model
			) {
		// Authentication Handler
		UserDetail user = (UserDetail) session.getAttribute("session");
		if (user == null) {
			return "/auth/login";
		}
		RatingStar userRate = (RatingStar) session.getAttribute("userRate");
		criteriaDetail.setRatingStar(userRate);
		appRepository.save(userRate);
		criteriaDetailRepository.save(criteriaDetail);
		System.out.println(userRate.toString());
		System.out.println(criteriaDetail.toString());
		return "app/index2";
	}
	
	
	// Transition page 2
	@GetMapping("/rate")
	public String homePage(HttpSession session, Model model) {

		// Authentication Handler
		UserDetail user = (UserDetail) session.getAttribute("session");
		if (user == null) {
			return "/auth/login";
		}

		return "app/index2";
	}
	
	// Get country clicked
	@PostMapping("/selected-country")
	public String processSelectedCountry(@RequestBody MultiValueMap<String, String> formData,
			RedirectAttributes redirectAttributes) {
		String selectedCountry = formData.getFirst("selectedCountry");
		redirectAttributes.addFlashAttribute("data", selectedCountry);

		// Process the selected country here
		System.out.println("Received selected country: " + selectedCountry);
		return "redirect:/app/infoRate";
	}
	

}
