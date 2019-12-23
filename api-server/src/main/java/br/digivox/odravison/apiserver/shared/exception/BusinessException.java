package br.digivox.odravison.apiserver.shared.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BusinessException extends Exception {

    private HttpStatus httpStatus;

    public BusinessException(String message) {
        super(message);
    }

}
