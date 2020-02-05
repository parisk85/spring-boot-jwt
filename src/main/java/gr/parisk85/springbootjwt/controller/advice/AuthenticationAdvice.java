package gr.parisk85.springbootjwt.controller.advice;

import gr.parisk85.springbootjwt.model.ErrorMessage;
import gr.parisk85.springbootjwt.service.ResponseWrapperService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AuthenticationAdvice {

    private final ResponseWrapperService responseWrapperService;

    public AuthenticationAdvice(ResponseWrapperService responseWrapperService) {
        this.responseWrapperService = responseWrapperService;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    protected ErrorMessage handleException(Exception e) {
        final ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return errorMessage;
    }
}
