package rent_cars_app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class Unauthorized extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Unauthorized(String reason) {
		super(reason);
	}
}
