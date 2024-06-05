package RatingApp.com.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import RatingApp.com.entities.CriteriaDetail;
import RatingApp.com.entities.RatingStar;

@Repository
public interface CriteriaDetailRepository extends JpaRepository<CriteriaDetail, Integer>{
	
	@Query("SELECT c FROM CriteriaDetail c JOIN FETCH c.ratingStar r")
    Page<CriteriaDetail> findAllWithRatingStars(Pageable pageable);
	
	@Query("SELECT c FROM CriteriaDetail c JOIN c.ratingStar r WHERE r.department = :department AND SUBSTRING(r.dateRate, 1, 5) BETWEEN :startDate AND :endDate ORDER BY r.rateId ASC")
    Page<CriteriaDetail> findByDepartmentAndDate(@Param("department") String department,
    		@Param("startDate") String startDate,
    		@Param("endDate") String endDate,Pageable pageable);
	
	@Query("SELECT c FROM CriteriaDetail c JOIN c.ratingStar r WHERE SUBSTRING(r.dateRate, 1, 5) BETWEEN :startDate AND :endDate ORDER BY r.rateId ASC")
    Page<CriteriaDetail> findAllByDateRateBetween(@Param("startDate") String startDate,
    											@Param("endDate") String endDate,Pageable pageable);

}
