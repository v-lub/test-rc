package rent_cars_app.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchDto {
    Integer current_page;
    Integer items_on_page;
    Integer items_total;
    Iterable<CarDto> cars;
}