package hello.infrastructure.adapters.http.api;

import hello.domain.Thing;
import hello.services.Service;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Commands {

  @Autowired private Service services;

  @PostMapping(value = "/things", consumes = "application/json", produces = "application/json")
  public @ResponseBody CompletableFuture<Integer> saveAThing(@RequestBody Thing thing) {
    return services.save(thing);
  }
}
