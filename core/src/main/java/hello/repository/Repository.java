package hello.repository;

import io.vavr.control.Option;
import java.util.concurrent.CompletableFuture;

interface Repository<A, Id> {

  CompletableFuture<A> insert(A thing);

  CompletableFuture<Option<A>> query(Id id);
}
