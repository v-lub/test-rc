package rent_cars_app.controller.dto.filter;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "filters")
@EqualsAndHashCode(of="make")
public class FilterForFrontDto {
	@Id
	String make;
	/*@JsonIgnore
	Integer sum;*/
	Set<ModelForFrontDto> models;
}
