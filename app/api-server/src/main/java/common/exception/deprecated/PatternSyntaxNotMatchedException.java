package common.exception.deprecated;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PatternSyntaxNotMatchedException extends RuntimeException {

    public PatternSyntaxNotMatchedException(String message) {
        super(message);
    }
}
