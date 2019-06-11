package hello.services;

import hello.domain.Thing;
import hello.domain.errors.ThingAlreadyExist;
import hello.infrastructure.CompletableFutureUtilities;
import hello.repository.ThingsRepository;
import hello.repository.dtos.ThingDTO;
import io.vavr.control.Option;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class ThingServices implements Service<Thing, Integer> {

  private final ThingsRepository repository;

  @Autowired
  public ThingServices(ThingsRepository repository) {
    this.repository = repository;
  }

  @Override
  public CompletableFuture<Thing> save(Thing thing) {
    return repository
        .query(thing.getId())
        .thenCompose(
            mayBeThing -> {
              // Either<Error, Void> ignore = Eithe
              //      .cond(mayBeThing.isDefined(), () -> {throw new Exception("")}, () -> null);
              // mayBeThing.isDefined()
              //        ? CompletableFutureUtilities.failedFuture(new ThingAlreadyExist())
              //        : repository.insert(new ThingDTO(thing.getId(), thing.getName()))
              return mayBeThing.fold(
                  () ->
                      repository
                          .insert(new ThingDTO(thing.getId(), thing.getName()))
                          .thenApply(ThingDTO::toDomainThing),
                  x -> CompletableFutureUtilities.failedFuture(new ThingAlreadyExist()));
            });
  }

  @Override
  public CompletableFuture<Option<Thing>> obtainThing(Integer id) {
    return repository
        .query(id)
        .thenApply(mayBeThingDTO -> mayBeThingDTO.map(ThingDTO::toDomainThing));
  }
}
