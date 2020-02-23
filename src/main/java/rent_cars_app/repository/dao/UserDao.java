package rent_cars_app.repository.dao;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = { "email" })
//@Document(collection = "users")
@ToString
public class UserDao {
	// @Id
	String email;
	String password;
	String firstName;
	String secondName;
	LocalDate registrationDate;
	Set<CommentDao> comments;
	// Set<CarDao> ownCars;
	Set<BookedCarDao> bookedCars;
	Set<BookedCarDao> history;
}