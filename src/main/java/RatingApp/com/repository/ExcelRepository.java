package RatingApp.com.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import RatingApp.com.entities.RatingStar;

@Repository
public interface ExcelRepository extends JpaRepository<RatingStar, Integer>{
	
	@Query(value = "SELECT * FROM rating_star WHERE department = :department AND SUBSTRING(date_rate, 1, 5) BETWEEN :startDate AND :endDate Order By rate_id Asc", nativeQuery = true)
	List<RatingStar> findByDepartmentAndDateRateBetweenOrderByRateIdAsc(
	        @Param("department") String department, 
	        @Param("startDate") String startDate, 
	        @Param("endDate") String endDate
	        );

	@Query(value = "SELECT * FROM rating_star WHERE department = :department AND SUBSTRING(date_rate, 1, 5) BETWEEN :startDate AND :endDate Order By rate_id Asc", nativeQuery = true)
	Page<RatingStar> findByDepartmentAndDateRateBetweenOrderByRateIdAscWithPageable(
			@Param("department") String selectedCountry,
			@Param("startDate") String startedDate, 
			@Param("endDate") String endedDate,
			Pageable pageable);
	
	@Query("SELECT r FROM RatingStar r WHERE SUBSTRING(r.dateRate, 1, 5) BETWEEN :startDate AND :endDate ORDER BY r.rateId ASC")
	List<RatingStar> findByDepartmentAndDateRateBetweenOrderByRateIdAsc(@Param("startDate") String startedDate,@Param("endDate") String endedDate);
	
	



}
