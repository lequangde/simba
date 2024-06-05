package RatingApp.com.controller;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import RatingApp.com.entities.UserDetail;
import RatingApp.com.repository.UserDetailRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UserDetailRepository detailRepository;

	@GetMapping("/login")
	public String loginPage() {
		return "auth/login";
	}

	@GetMapping("/register")
	public String registerPage() {
		return "auth/register";
	}

	@PostMapping("/sign-up")
	public String signIn(@ModelAttribute("userDetail") UserDetail userDetail) {

		detailRepository.save(userDetail);
		System.out.println(userDetail.getUserName());
		return "auth/login";
	}


	@GetMapping("/logout")
	public String logout(HttpSession httpSession) {
		httpSession.invalidate();
		return "redirect:/login";
	}

}
