package hello.services;

import io.vavr.control.Option;
import java.util.concurrent.CompletableFuture;

public interface Service<T, Id> {
  CompletableFuture<T> save(T thing);

  CompletableFuture<Option<T>> obtainThing(Id id);
}
