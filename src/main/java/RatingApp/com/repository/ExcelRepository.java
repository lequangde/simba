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
	

	@Query("SELECT rs FROM RatingStar rs WHERE department = :department AND SUBSTRING(rs.dateRate, 1, 2) BETWEEN :startDate AND :endDate AND SUBSTRING(rs.dateRate, 4, 2) = :month")
	List<RatingStar> findByDepartmentAndDateRateBetweenOrderByRateIdAsc(
	        @Param("department") String department, 
	        @Param("startDate") String startDate, 
	        @Param("endDate") String endDate,
	        @Param("month") String month
	        );

	@Query(value = "SELECT * FROM rating_star WHERE department = :department AND SUBSTRING(date_rate, 1, 6) BETWEEN :startDate AND :endDate Order By rate_id Asc", nativeQuery = true)
	Page<RatingStar> findByDepartmentAndDateRateBetweenOrderByRateIdAscWithPageable(
			@Param("department") String selectedCountry,
			@Param("startDate") String startedDate, 
			@Param("endDate") String endedDate,
			Pageable pageable);
	
	@Query("SELECT r FROM RatingStar r WHERE SUBSTRING(r.dateRate, 1, 2) BETWEEN :startDate AND :endDate AND SUBSTRING(r.dateRate, 4, 2) = :month")
	List<RatingStar> findByDepartmentAndDateRateBetweenOrderByRateIdAsc(@Param("startDate") String startedDate,@Param("endDate") String endedDate,@Param("month") String month);
	
	
																


}
