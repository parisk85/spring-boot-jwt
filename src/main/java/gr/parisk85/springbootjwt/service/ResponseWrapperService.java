package gr.parisk85.springbootjwt.service;

import gr.parisk85.springbootjwt.model.ErrorMessage;
import gr.parisk85.springbootjwt.model.ResponseWrapper;
import org.springframework.stereotype.Service;

@Service
public class ResponseWrapperService {

    public ResponseWrapper generateResponseWrapper(final Object body, final String status, final ErrorMessage error) {
        final ResponseWrapper wrapper = new ResponseWrapper();
        wrapper.setData(body);
        wrapper.setStatus("success");
        wrapper.setError(error);
        return wrapper;
    }
}
