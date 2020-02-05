package gr.parisk85.springbootjwt.controller.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.parisk85.springbootjwt.model.ErrorMessage;
import gr.parisk85.springbootjwt.model.ResponseWrapper;
import gr.parisk85.springbootjwt.service.ResponseWrapperService;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Map;

@ControllerAdvice
public class ResponseWrapperAdvice implements ResponseBodyAdvice {
    private static final String SUPPORTED_CLASS_SUFFIX = "controller";
    private final ResponseWrapperService responseWrapperService;
    private final ObjectMapper objectMapper;

    public ResponseWrapperAdvice(final ResponseWrapperService responseWrapperService, final ObjectMapper objectMapper) {
        this.responseWrapperService = responseWrapperService;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return methodParameter.getContainingClass().getSimpleName().toLowerCase().endsWith(SUPPORTED_CLASS_SUFFIX)
                && methodParameter.getContainingClass().getSimpleName().toLowerCase().endsWith(SUPPORTED_CLASS_SUFFIX);
    }

    @Override
    public ResponseWrapper beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (objectMapper.convertValue(body, Map.class).containsKey("error")) {
            final ErrorMessage errorMessage = new ErrorMessage();
            final int status = (int) objectMapper.convertValue(body, Map.class).get("status");
            errorMessage.setCode(HttpStatus.valueOf(status));
            return responseWrapperService.generateResponseWrapper(null, "error", errorMessage);
        }
        return responseWrapperService.generateResponseWrapper(body, "success", null);
    }
}
