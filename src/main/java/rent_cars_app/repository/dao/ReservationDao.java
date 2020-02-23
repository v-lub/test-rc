
package rent_cars_app.repository.dao;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of="orderId")
public class ReservationDao {
	String orderId;
	LocalDateTime startDateTime;
	LocalDateTime endDateTime;
	Boolean paid;
	Double amount;
	LocalDateTime bookingDate;
	UnregisteredOrRegisteredUserDao personWhoBooked;
}
