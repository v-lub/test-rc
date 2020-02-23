package rent_cars_app.repository.dao;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookedPeriodDao {
	String orderId;
	LocalDateTime startDateTime;
	LocalDateTime endDateTime;
	Boolean paid;
	Double amount;
	LocalDateTime bookingDate;
}
