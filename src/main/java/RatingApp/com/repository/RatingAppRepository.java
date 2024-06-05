package RatingApp.com.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import RatingApp.com.entities.RatingStar;

@Repository
public interface RatingAppRepository extends JpaRepository<RatingStar, Integer> {

	Page<RatingStar> findAllByDepartmentOrderByRateIdDesc(Pageable pageable, String selectedCountry);

	int countByValueService(int i);

	int countByValueFood(int i);

	int countByValueFoodAndDepartment(int i, String department);

	int countByValueServiceAndDepartment(int i, String department);

	int countByDepartment(String data);

	Page<RatingStar> findAllByOrderByRateIdDesc(Pageable pageable);

	List<RatingStar> findAllByOrderByRateIdDesc();

	Page<RatingStar> findByDepartmentOrderByRateIdDesc(Pageable pageable, String department);

	// Display on chart by week
	@Query("select count(*) from RatingStar d where d.dateRate like %?1%")
	int countAllByDateRateLike(String weekDays);

	// Display on chart by week (employee)
	@Query("select count(*) from RatingStar d where d.dateRate like %?1% and d.department = ?2 ")
	int countAllByDateRateLikeAndDepartment(String format, String department);

	// Get all department to list select
	@Query("SELECT r.department FROM UserDetail r")
	List<String> findAllDepartments();

	// Display all department with total rate next to break up (admin)
	@Query(value = "SELECT department, COUNT(*) " + "FROM rating_star " + "WHERE date_rate LIKE %?1% "
			+ "GROUP BY department", nativeQuery = true)
	List<Object[]> countByDepartmentAndDateRateLike(String date);

	// Display total rate next to break up (employee)
	@Query("SELECT r.department, count(r) FROM RatingStar r WHERE r.dateRate LIKE %?1% AND r.department = ?2 GROUP BY r.department")
	List<Object[]> countByDepartmentAndDateRateLikeAndDepartment(String currentDay, String department);

	// Display total rate today (admin)
	@Query("select count(*) from RatingStar d where d.dateRate like %?1%")
	int countAllByToday(String today);

	// Display total food rate 1-5 from today (admin)
	@Query("select count(*) from RatingStar d where d.valueFood = ?1 and d.dateRate like %?2%")
	int countByValueFood(int i, String firstDay);

	// Display total service rate 1-5 from today (admin)
	@Query("select count(*) from RatingStar d where d.valueService = ?1 and d.dateRate like %?2%")
	int countByValueService(int i, String firstDay);

	// Display total rate today (employee)
	@Query("select count(*) from RatingStar d where d.department = ?1 and d.dateRate like %?2%")
	int countByDepartment(String department, String currentDay);

	// Display total food rate 1-5 today (employee)
	@Query("select count(*) from RatingStar d where d.valueFood = ?1 and d.dateRate like %?2% and d.department = ?3")
	int countByValueFood(int i, String currentDay, String department);

	// Display total service rate 1-5 today (employee)
	@Query("select count(*) from RatingStar d where d.valueService = ?1 and d.dateRate like %?2% and d.department = ?3")
	int countByValueService(int i, String currentDay, String department);

	// Display break up by date (employee)
	@Query("select count(*) from RatingStar d where d.dateRate like %?1% and d.department = ?2")
	int countAllByDateRateLike(String formattedDate, String department);

	// Display department on pie breakup (admin)
	@Query("select department from RatingStar r where r.dateRate LIKE %?1% group by department")
	String[] findAllDepartmentAndDateRateLike(String format);

	// Display rate data on pie breakup (admin)
	@Query("select count(*) from RatingStar r where r.dateRate LIKE %?1% group by department")
	int[] getAllRatingWithDateRate(String date);

	// Filter food by rate
	@Query("SELECT r.department,COUNT(r.valueFood) " + "FROM RatingStar r " + "WHERE r.dateRate LIKE %?1% "
			+ "GROUP BY r.department, r.valueFood " + "HAVING r.valueFood = ?2")
	List<Object[]> countStarFoodByDepartmentAndDate(String datePattern, int starFood);

	// Filter service by rate
	@Query("SELECT r.department,COUNT(r.valueService) " + "FROM RatingStar r " + "WHERE r.dateRate LIKE %?1% "
			+ "GROUP BY r.department, r.valueService " + "HAVING r.valueService = ?2")
	List<Object[]> countStarServiceByDepartmentAndDate(String datePattern, int starService);
	
	@Query("SELECT r.department, COUNT(r.valueFood) " +
	           "FROM RatingStar r " +
	           "WHERE r.dateRate LIKE %?1% " +
	           "AND r.valueFood = ?2 " +
	           "GROUP BY r.department")
	    List<Object[]> findDepartmentAndStarFoodCount(
	       String date,
	       int valueFood
	    );

}
