package rent_cars_app.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rent_cars_app.controller.dto.filter.FilterForFrontDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchByFiltersDto {
    Integer current_page;
    Integer items_on_page;
    Integer items_total;
    Iterable<CarDto> cars;
    Iterable<FilterForFrontDto> filters;
}
