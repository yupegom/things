package hello.services;

import hello.domain.Thing;
import hello.domain.errors.ThingAlreadyExist;
import hello.infrastructure.CompletableFutureUtilities;
import hello.repository.ThingsRepository;
import hello.repository.dtos.ThingDTO;
import io.vavr.control.Option;
import java.util.concurrent.CompletableFuture;

@org.springframework.stereotype.Service
public class ThingServices implements Service<Thing, Integer> {

  private final ThingsRepository repository;

  public ThingServices(ThingsRepository repository) {
    this.repository = repository;
  }

  @Override
  public CompletableFuture<Integer> save(Thing thing) {
    return repository
        .query(thing.getId())
        .thenCompose(
            mayBeThing -> {
              // Either<Error, Void> ignore = Eithe
              //      .cond(mayBeThing.isDefined(), () -> {throw new Exception("")}, () -> null);
              // mayBeThing.isDefined()
              //        ? CompletableFutureUtilities.failedFuture(new ThingAlreadyExist())
              //        : repository.insert(new ThingDTO(thing.getId(), thing.getName()))
              if (mayBeThing.isDefined())
                return CompletableFutureUtilities.failedFuture(new ThingAlreadyExist());
              else return repository.insert(new ThingDTO(thing.getId(), thing.getName()));
            });
  }

  @Override
  public CompletableFuture<Option<Thing>> obtainThing(Integer id) {
    return repository
        .query(id)
        .thenApply(
            mayBeThingDTO ->
                mayBeThingDTO.map(thingDTO -> new Thing(thingDTO.getId(), thingDTO.getName())));
  }
}
