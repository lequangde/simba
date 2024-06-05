package RatingApp.com.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class RatingStar {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rate_id")
	private int rateId;
	
	@Column(name = "star_service")
	private int valueService;
	
	@Column(name = "star_food")
	private int valueFood;
	
	@Column(name = "date_rate")
	private LocalDate dateRate;

}
