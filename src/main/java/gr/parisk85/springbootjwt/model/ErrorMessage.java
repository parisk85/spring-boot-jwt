package gr.parisk85.springbootjwt.model;

import org.springframework.http.HttpStatus;

public class ErrorMessage {
    private HttpStatus code;

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }
}
