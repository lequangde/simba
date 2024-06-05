package RatingApp.com.entities;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
	private String dateRate;
	
	@Column(name = "department")
	private String department;
	
	@OneToOne(mappedBy = "ratingStar")
	private CriteriaDetail criteriaDetail;

	@Override
	public String toString() {
		return "RatingStar [rateId=" + rateId + ", valueService=" + valueService + ", valueFood=" + valueFood
				+ ", dateRate=" + dateRate + ", department=" + department + ", criteriaDetail=" + criteriaDetail + "]";
	}
	
	

}
