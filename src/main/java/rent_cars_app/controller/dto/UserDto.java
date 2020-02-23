package rent_cars_app.controller.dto;

import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
	String first_name;
	String second_name;
	LocalDate registration_date;
	Set<CommentDto> comments;
	Set<CarWithoutOwnerDto> own_cars;
	Set<BookedCarDto> booked_cars;
	Set<BookedCarDto> history;
}