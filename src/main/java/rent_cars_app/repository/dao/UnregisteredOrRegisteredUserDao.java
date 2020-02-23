package rent_cars_app.repository.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnregisteredOrRegisteredUserDao {
	String email;
	String firstName;
	String secondName;
	String phone;
}
