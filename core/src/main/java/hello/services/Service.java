package hello.services;

import io.vavr.control.Option;
import java.util.concurrent.CompletableFuture;

interface Service<T, Id> {
  CompletableFuture<Integer> save(T thing);

  CompletableFuture<Option<T>> obtainThing(Id id);
}
