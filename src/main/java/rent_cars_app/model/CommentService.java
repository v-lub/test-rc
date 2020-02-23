package rent_cars_app.model;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rent_cars_app.controller.dto.CommentDto;
import rent_cars_app.exceptions.BadRequest;
import rent_cars_app.exceptions.NotFound;
import rent_cars_app.exceptions.Unauthorized;
import rent_cars_app.model.entitys.*;
import rent_cars_app.model.tools.UserTools;
import rent_cars_app.repository.IRentCarsRepository;

@Service
public class CommentService implements ICommentService{
	@Autowired
	IRentCarsRepository rentCarRepository;
	
	@Override
	public Iterable<CommentDto> latestComments() {
		Set<Comment> comments = rentCarRepository.findCommentsLatest().orElseThrow(() -> new NotFound("Any comments not found"));
		return comments.stream()
				.map(UserTools::mapToCommentDto)
				.collect(Collectors.toSet());
	}

	@Override
	public void postComment(String serial_number, String email, String post) {
		User userWhoComment = rentCarRepository.findUserById(email).orElseThrow(() -> new Unauthorized("Bad user"));
		Car car = rentCarRepository.findCarById(serial_number).orElseThrow(() -> new BadRequest("Car not found"));
		if (car.getReservations() == null) {
			throw new Unauthorized("You don't have permission for this");
		}
		car.getReservations().stream()
							.filter(r -> r.getPerson_who_reserved().getEmail().equals(email))
							.findAny()
							.orElseThrow(() -> new Unauthorized("You don't have permission for this"));
		User user = rentCarRepository.findUserByCarId(serial_number).get();
		user.getComments().add(new Comment(Owner.builder()
												.first_name(userWhoComment.getFirst_name())
												.second_name(userWhoComment.getSecond_name())
												.registration_date(userWhoComment.getRegistration_date())
												.build(), LocalDate.now(), post));
		rentCarRepository.updateUser(user);
	}

}
