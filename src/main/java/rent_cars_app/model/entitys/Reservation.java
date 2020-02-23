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
@EqualsAndHashCode(of = {"order_id"})
@Builder
public class Reservation {
	String order_id;
	LocalDateTime start_date_time;
	LocalDateTime end_date_time;
	Boolean paid;
	Car car;
	Double amount;
	LocalDateTime booking_date;
	UnregisteredOrRegisteredUser person_who_reserved;
	
	public Reservation(String order_id, LocalDateTime start_date_time, LocalDateTime end_date_time,
			Boolean paid, Car car, Double amount, LocalDateTime booking_date, UnregisteredOrRegisteredUser person_who_reserved) {
		this.order_id = order_id;
		this.start_date_time = start_date_time;
		this.end_date_time = end_date_time;
		this.paid = paid;
		this.car = car;
		this.amount = amount;
		this.booking_date = booking_date;
		this.person_who_reserved = person_who_reserved;
		if (order_id == null) {
			this.order_id = generateReservationId(start_date_time, end_date_time, car.getSerial_number());
		}
		if (paid == null) {
			this.paid = false;
		}
		if (booking_date == null) {
			this.booking_date = LocalDateTime.now();
		}
	}

	private String generateReservationId(LocalDateTime start_date_time, LocalDateTime end_date_time,
			String serial_number) {
		return new String("R" + serial_number.hashCode() + start_date_time.hashCode() + end_date_time.hashCode());
	}
	
	
}
