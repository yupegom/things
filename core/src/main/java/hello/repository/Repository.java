package hello.repository;

import io.vavr.control.Option;
import java.util.concurrent.CompletableFuture;

interface Repository<A, Id> {

  CompletableFuture<Integer> insert(A thing);

  CompletableFuture<Option<A>> query(Id id);
}
