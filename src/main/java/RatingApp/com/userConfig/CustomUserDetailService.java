package RatingApp.com.userConfig;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import RatingApp.com.entities.UserDetail;
import RatingApp.com.repository.UserDetailRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserDetailRepository userDetailRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		UserDetail usersDB = userDetailRepository.findByUserName(userName);
		
		return new UserDetailConfig(usersDB);
	}

}
