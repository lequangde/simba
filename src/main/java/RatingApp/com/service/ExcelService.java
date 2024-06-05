package RatingApp.com.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import RatingApp.com.entities.RatingStar;
import RatingApp.com.repository.RatingAppRepository;

@Service
public class ExcelService {
	
	@Autowired 
	private RatingAppRepository appRepository;
	
	 public List<RatingStar> getRatingsByMinStars(int minStars) {
	        // Fetch all ratings and filter them based on the minStars
	        List<RatingStar> allRatings = appRepository.findAll(); // Fetch from repository or data source
	        return allRatings.stream()
	                .filter(r -> r.getValueFood() >= minStars || r.getValueService() >= minStars)
	                .collect(Collectors.toList());
	    }

	
}
