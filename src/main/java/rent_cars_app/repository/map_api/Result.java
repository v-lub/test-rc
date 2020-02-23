package rent_cars_app.repository.map_api;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Result {
Set<AddressComponents> address_components;
}
