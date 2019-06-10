package hello.infrastructure;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureUtilities {

  public static <T> CompletableFuture<T> failedFuture(Throwable t) {
    final CompletableFuture<T> cf = new CompletableFuture<>();
    cf.completeExceptionally(t);
    return cf;
  }
}
