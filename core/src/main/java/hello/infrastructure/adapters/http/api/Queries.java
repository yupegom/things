package hello.infrastructure.adapters.http.api;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import hello.domain.Thing;
import hello.infrastructure.adapters.http.api.responses.ThingNotFound;
import hello.services.Service;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

  @HystrixCommand(fallbackMethod = "iFailed")
  @GetMapping("/things/mayFail/{fail}")
  public String mayFail(@PathVariable String fail) throws Exception {
    if (fail.contentEquals("fail")) throw new Exception("Fail!");
    else return "OK";
  }

  public String iFailed(String fail) {
    return "I've failed :(";
  }
}
