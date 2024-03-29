package buld.week.u5w4bw.exceptions;


import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
public class BadRequestException extends RuntimeException {

    private List<ObjectError> errorList;


    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(List<ObjectError> errorsList) {
        this.errorList = errorsList;
    }
}
