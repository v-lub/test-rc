package rent_cars_app.repository.map_api;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressComponents {
	String long_name;
	String short_name;
	Set<String> types;
}
