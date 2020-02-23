package rent_cars_app.model;

import rent_cars_app.controller.dto.CommentDto;

public interface ICommentService {

	Iterable<CommentDto> latestComments();

	void postComment(String serial_number, String email, String post);

}
