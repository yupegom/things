package hello.repository;

import hello.repository.dtos.ThingDTO;
import io.vavr.control.Option;
import java.util.concurrent.CompletableFuture;

public interface ThingsRepository extends Repository<ThingDTO, Integer> {

  @Override
  CompletableFuture<Integer> insert(ThingDTO thing);

  @Override
  CompletableFuture<Option<ThingDTO>> query(Integer id);
}
