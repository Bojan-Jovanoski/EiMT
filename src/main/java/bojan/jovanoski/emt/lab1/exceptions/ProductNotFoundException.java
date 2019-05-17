package bojan.jovanoski.emt.lab1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "Product Not Found Exception")
public class ProductNotFoundException extends RuntimeException {
}
