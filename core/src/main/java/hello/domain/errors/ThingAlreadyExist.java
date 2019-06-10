package hello.domain.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ThingAlreadyExist extends Error {

  public ThingAlreadyExist() {
    this.msg = "The thing is already in the DB";
  }
}
