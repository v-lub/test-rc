package rent_cars_app.model.entitys;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = {"bookedId"})
public class ReservationPeriod {
	String bookedId;
	LocalDateTime start_date_time;
	LocalDateTime end_date_time;
	
	public ReservationPeriod(String bookedId, LocalDateTime start_date_time, LocalDateTime end_date_time) {
		this.bookedId = bookedId;
		this.start_date_time = start_date_time;
		this.end_date_time = end_date_time;
		
		if (bookedId == null) {
			this.bookedId = generateBookedId(start_date_time, end_date_time);
		}
	}

	private String generateBookedId(LocalDateTime start_date_time2, LocalDateTime end_date_time2) {
		return new String("B" + start_date_time.hashCode() + end_date_time.hashCode());
	}

}

