package hello.infrastructure.adapters.http.api;

import hello.domain.Thing;
import hello.repository.ThingsH2RepositoryImpl;
import hello.services.ThingServices;
import java.util.concurrent.CompletableFuture;
import org.springframework.web.bind.annotation.*;

@RestController
public class Commands {

  private final ThingServices services = new ThingServices(new ThingsH2RepositoryImpl());

  @PostMapping(value = "/things", consumes = "application/json", produces = "application/json")
  public @ResponseBody CompletableFuture<Integer> saveAThing(@RequestBody Thing thing) {
    return services.save(thing);
  }
}
