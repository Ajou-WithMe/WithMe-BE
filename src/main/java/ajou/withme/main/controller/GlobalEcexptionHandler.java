package ajou.withme.main.controller;

import ajou.withme.main.util.ResFormat;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalEcexptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResFormat handleException(Exception e) {
        return new ResFormat(false, 500L, e.getMessage());
    }

}

