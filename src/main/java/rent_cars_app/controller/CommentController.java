package rent_cars_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rent_cars_app.controller.dto.CommentDto;
import rent_cars_app.model.ICommentService;

@RestController
@CrossOrigin
public class CommentController {
	@Autowired
	ICommentService cs;

	@GetMapping("/comments")
	Iterable<CommentDto> latestComments() {
		return cs.latestComments();
	}
	@PostMapping("/comment")
	void postComment(@RequestParam("serial_number") String serial_number, @RequestBody CommentDto comment) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		cs.postComment(serial_number,auth.getName(),comment.getPost());
	}
}