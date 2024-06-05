package RatingApp.com.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CriteriaDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "criteria_id")
	private int criteriaId;
	
	@Column(name = "object_1")
	private int object1;
	
	@Column(name = "object_2")
	private int object2;
	
	@Column(name = "object_3")
	private int object3;

	@Column(name = "object_4")
	private int object4;
	
	@OneToOne
	@JoinColumn(name = "rate_id")
	private RatingStar ratingStar;

	@Override
	public String toString() {
		return "CriteriaDetail [criteriaId=" + criteriaId + ", object1=" + object1 + ", object2=" + object2
				+ ", object3=" + object3 + ", object4=" + object4 + ", ratingStar=" + ratingStar + "]";
	}
	
	

}
