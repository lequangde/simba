package RatingApp.com.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
public class SecurityConfig {

//	@Autowired
//	PasswordEncoder passwordEncoder;

//	@Autowired
//	public void configGlobal(AuthenticationManagerBuilder builder) throws Exception {
//		builder.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
//		
//		builder.userDetailsService(userDetailService);
//	}

	private final static String[] permitAllLink = { 
			"/js/**", 
			"/css/**", 
			"/images/**", 
			"/libs/**", 
			"/scss/**",
			"/auth/**", 
			"/export",
			"/api/**",

	};

	private final static String[] permitQRLink = {
	};

	private final static String[] permitEmployeeLink = { 
			"/home/**", "/app/**", "/user/**",
			"/excel/**","/qr/**","/criteria/**"
	};

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.authorizeHttpRequests(auth -> {
			auth
			.requestMatchers(permitAllLink).permitAll()
			.requestMatchers(permitEmployeeLink).permitAll()
				.anyRequest().authenticated();
				
			
		}).formLogin(form -> {
			form.loginPage("/auth/login")
			.usernameParameter("userName")
			.passwordParameter("passWord")
			.permitAll();
			
		}).logout().logoutUrl("/logout")
		.logoutSuccessUrl("/login?logout")
		.invalidateHttpSession(true)
		.deleteCookies("JSESSIONID")
		.disable();

		return httpSecurity.build();
	}

	private AuthenticationSuccessHandler successHandler() {
        SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
        handler.setDefaultTargetUrl("/auth/sign-in");
        return handler;
    }


}
