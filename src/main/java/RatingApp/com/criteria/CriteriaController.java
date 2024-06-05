package RatingApp.com.criteria;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import RatingApp.com.entities.CriteriaDetail;
import RatingApp.com.entities.RatingStar;
import RatingApp.com.repository.CriteriaDetailRepository;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/criteria")
public class CriteriaController {
	
	@Autowired
	CriteriaDetailRepository criteriaDetailRepository;
	
	@GetMapping("/home")
	public String homePage() {
		return "criteria/criteria";
	}

	@GetMapping("/home1")
	public String home(
			Model model,@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,HttpSession httpSession
			) {
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
		String selectedCountry = (String) httpSession.getAttribute("selectedCountryWithCriteria");
		String startDate = (String) httpSession.getAttribute("startDateWithCriteria");
		String endDate = (String) httpSession.getAttribute("endDateWithCriteria");
		if(selectedCountry.equals("")) {
			Page<CriteriaDetail> listCriteria =  criteriaDetailRepository.findAllByDateRateBetween(startDate, endDate,pageable);
			model.addAttribute("listCriteria", listCriteria);
			model.addAttribute("totalPages", listCriteria.getTotalPages());
		}else {
			Page<CriteriaDetail> listCriteria =  criteriaDetailRepository.findByDepartmentAndDate(selectedCountry,startDate, endDate,pageable);
			model.addAttribute("listCriteria", listCriteria);
			model.addAttribute("totalPages", listCriteria.getTotalPages());
		}
		
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("pageSize", pageSize);
		
		return "criteria/criteria2";
	}
	
	@PostMapping("/reported")
	public String report(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate,
    		@RequestParam String selectedCountry, HttpSession httpSession) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
    	
		String startedDate = startDate.format(formatter);
		String endedDate = endDate.format(formatter);
		
		httpSession.setAttribute("selectedCountryWithCriteria", selectedCountry);
    	httpSession.setAttribute("startDateWithCriteria", startedDate);
    	httpSession.setAttribute("endDateWithCriteria", endedDate);
		return "redirect:/criteria/home1";
	}
	
}
