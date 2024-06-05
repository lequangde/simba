package RatingApp.com.userConfig;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import RatingApp.com.entities.UserDetail;

public class UserDetailConfig implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UserDetail user;

	public UserDetailConfig() {
		
	}
	
	public UserDetailConfig(UserDetail user) {
		this.user = user;
	}



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		return user.getPassWord();
	}

	@Override
	public String getUsername() {
		return user.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
