package rent_cars_app.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnregisteredOrRegisteredUserDto {
	String email;
	String first_name;
	String second_name;
	String phone;
}
