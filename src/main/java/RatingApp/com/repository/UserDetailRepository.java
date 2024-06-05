package RatingApp.com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import RatingApp.com.entities.RatingStar;
import RatingApp.com.entities.UserDetail;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Integer> {

	UserDetail findByUserName(String userName);
	
	@Query("SELECT r.department FROM RatingStar r GROUP BY r.department")
	List<String> findDistinctDepartments();
	

}
