package rent_cars_app.model.tools;


import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import rent_cars_app.controller.dto.*;
import rent_cars_app.model.entitys.*;

public class UserTools {
	
	public static boolean cheackPassword(String password) {
		// TODO Auto-generated method stub
		return true;
	}

	public static boolean cheackFirstName(String first_name) {
		// TODO Auto-generated method stub
		return true;
	}

	public static boolean cheackSecondName(String second_name) {
		// TODO Auto-generated method stub
		return true;
	}

	public static boolean cheackPhone(String phone) {
		// TODO Auto-generated method stub
		return true;
	}

	public static User addNewUserMapToUser(UserUpdateOrNewDto userDto, String email, String password) {
		return User.builder()
				.email(email)
				.password(password)
				.first_name(userDto.getFirst_name())
				.second_name(userDto.getSecond_name())
				.build();				
	}
	
	public static CommentDto mapToCommentDto(Comment comment) {
		return CommentDto.builder()
				.first_name(comment.getUser().getFirst_name())
				.second_name(comment.getUser().getSecond_name())
				.post_date(comment.getPost_date())
				.post(comment.getPost())
				.build();
	}

	public static UserDto mapToUserDtoNew(User newUser) {
		return UserDto.builder()
				.first_name(newUser.getFirst_name())
				.second_name(newUser.getSecond_name())
				.registration_date(newUser.getRegistration_date())
				.comments(new HashSet<>())
				.own_cars(new HashSet<>())
				.booked_cars(new HashSet<>())
				.history(new HashSet<>())
				.build();
	}

	public static UserDto mapToUserDto(User user) {
		Set<CommentDto> commentsDto = new HashSet<>();
		if (user.getComments() != null && !user.getComments().isEmpty()) {
			 commentsDto = user.getComments().stream()
											.map(UserTools::mapToCommentDto)
											.collect(Collectors.toSet());
		}
		Set<CarWithoutOwnerDto> own_carsDto = new HashSet<>();
		if (user.getOwn_cars() != null && !user.getOwn_cars().isEmpty()){
			own_carsDto = user.getOwn_cars().stream()
											.map(CarTools::mapToCarWithoutOwnerDto)
											.collect(Collectors.toSet());
		}
		Set<BookedCarDto> booked_carsDto = new HashSet<>();
		if (user.getBooked_cars() != null && !user.getBooked_cars().isEmpty()){
			booked_carsDto = user.getBooked_cars().stream()
													.map(CarTools::mapToBookedCarDto)
													.collect(Collectors.toSet());
		}
		Set<BookedCarDto> historyDto = new HashSet<>();
		if (user.getHistory_cars() != null && !user.getBooked_cars().isEmpty()){
			historyDto = user.getHistory_cars().stream()
												.map(CarTools::mapToBookedCarDto)
												.collect(Collectors.toSet());
		}
		return UserDto.builder()
				.first_name(user.getFirst_name())
				.second_name(user.getSecond_name())
				.registration_date(user.getRegistration_date())
				.comments(commentsDto)
				.own_cars(own_carsDto)
				.booked_cars(booked_carsDto)
				.history(historyDto)
				.build();
	}

	public static UnregisteredOrRegisteredUserDto mapToUnregisteredOrRegisteredUserDto(UnregisteredOrRegisteredUser person_who_reserved) {
		return UnregisteredOrRegisteredUserDto.builder()
				.email(person_who_reserved.getEmail())
				.first_name(person_who_reserved.getFirst_name())
				.second_name(person_who_reserved.getSecond_name())
				.phone(person_who_reserved.getPhone())
				.build();
	}
}
