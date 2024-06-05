package RatingApp.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import RatingApp.com.entities.RatingStar;

@Repository
public interface RatingAppRepository extends JpaRepository<RatingStar, Integer> {
	
	

}
