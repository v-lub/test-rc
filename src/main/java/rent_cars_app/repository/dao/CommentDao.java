package rent_cars_app.repository.dao;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommentDao {
	String firstName;
	String secondName;
	LocalDate postDate;
	String post;
}
