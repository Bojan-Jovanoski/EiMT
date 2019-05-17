package bojan.jovanoski.emt.lab1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "Manufacturer Already Exists")
public class ManufacturerAlreadyExistsException extends RuntimeException {
}
