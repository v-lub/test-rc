package rent_cars_app.controller.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationNotForOwnerDto {
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	LocalDateTime start_date_time;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	LocalDateTime end_date_time;
}
