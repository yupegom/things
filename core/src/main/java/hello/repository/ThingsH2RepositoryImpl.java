package hello.repository;

import hello.infrastructure.Database;
import hello.infrastructure.adapters.daos.MembersDAO;
import hello.infrastructure.adapters.h2.H2Database;
import hello.repository.dtos.ThingDTO;
import io.vavr.control.Option;
import java.util.concurrent.CompletableFuture;
import org.springframework.stereotype.Component;

@Component
public class ThingsH2RepositoryImpl implements ThingsRepository {

  @Override
  public CompletableFuture<Integer> insert(ThingDTO thing) {
    Database h2Database = new H2Database();
    h2Database.handleStatement(
        dbi -> dbi.open(MembersDAO.class),
        d -> {
          d.insert(thing.getId(), thing.getName());
          return null;
        });
    return CompletableFuture.completedFuture(1);
  }

  @Override
  public CompletableFuture<Option<ThingDTO>> query(Integer id) {
    Database h2Database = new H2Database();
    ThingDTO thing =
        h2Database.handleStatement(dbi -> dbi.open(MembersDAO.class), d -> d.findById(id));
    return CompletableFuture.completedFuture(Option.of(thing));
  }
}
