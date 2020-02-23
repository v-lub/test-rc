package rent_cars_app.repository.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookedCarDao {
	String serialNumber;
    BookedPeriodDao bookedPeriod;
}
