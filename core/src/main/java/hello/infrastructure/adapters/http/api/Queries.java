package hello.infrastructure.adapters.http.api;

import hello.domain.Thing;
import hello.infrastructure.adapters.http.api.responses.ThingNotFound;
import hello.repository.ThingsH2RepositoryImpl;
import hello.services.ThingServices;
import java.util.concurrent.CompletableFuture;
import org.springframework.web.bind.annotation.*;

@RestController
public class Queries {

  //    @Autowired
  //  ThingServices services;
  ThingServices services = new ThingServices(new ThingsH2RepositoryImpl());

  @RequestMapping("/things/{id}")
  public @ResponseBody CompletableFuture<Thing> giveAThing(@PathVariable int id) {
    return services
        .obtainThing(id)
        .thenApplyAsync(
            maybeThing ->
                maybeThing.fold(
                    () -> {
                      throw new ThingNotFound(String.format("Thing %d is not here", id));
                    },
                    x -> x));
  }

  @PostMapping(value = "/things", consumes = "application/json", produces = "application/json")
  public @ResponseBody CompletableFuture<Integer> saveAThing(@RequestBody Thing thing) {
    return services.save(thing);
  }
}
