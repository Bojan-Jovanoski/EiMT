package bojan.jovanoski.emt.lab1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "Category Not Found Exception")
public class CategoryNotFoundException extends RuntimeException{
}
