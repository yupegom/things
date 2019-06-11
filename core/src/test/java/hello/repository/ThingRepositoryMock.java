package hello.repository;

import hello.repository.dtos.ThingDTO;
import io.vavr.control.Option;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ThingRepositoryMock implements ThingsRepository {
  @Override
  public CompletableFuture<ThingDTO> insert(ThingDTO thing) {
    return CompletableFuture.supplyAsync(
        () -> {
          state.put(thing.getId(), thing);
          return thing;
        });
  }

  private Map<Integer, ThingDTO> state = new HashMap<>();

  public ThingRepositoryMock() {
    state.put(1, new ThingDTO(1, "test"));
  }

  @Override
  public CompletableFuture<Option<ThingDTO>> query(Integer id) {
    return CompletableFuture.completedFuture(Option.of(state.get(id)));
  }
}
