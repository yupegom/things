package hello.infrastructure.adapters.http.api;

import hello.domain.Thing;
import hello.infrastructure.adapters.http.api.responses.ThingNotFound;
import hello.services.Service;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Queries {

  @Autowired private Service<Thing, Integer> services;

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
}
