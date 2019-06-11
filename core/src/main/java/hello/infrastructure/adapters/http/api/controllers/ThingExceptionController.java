package hello.infrastructure.adapters.http.api.controllers;

import hello.domain.errors.ThingAlreadyExist;
import hello.infrastructure.adapters.http.api.responses.ThingNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ThingExceptionController {
  @ExceptionHandler(value = ThingNotFound.class)
  public ResponseEntity<Object> exception(ThingNotFound exception) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = ThingAlreadyExist.class)
  public ResponseEntity<Object> exception(ThingAlreadyExist exception) {
    return new ResponseEntity<>("Thing already exits", HttpStatus.BAD_REQUEST);
  }
}
