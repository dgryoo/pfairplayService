package project.pfairplay.api.common.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseEntity {

    private String errorCode;

    private String errorField;

    private String message;

}
