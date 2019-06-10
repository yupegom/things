package hello.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import hello.domain.Thing;
import hello.domain.errors.ThingAlreadyExist;
import hello.infrastructure.Database;
import hello.infrastructure.adapters.daos.MembersDAO;
import hello.infrastructure.adapters.h2.H2Database;
import hello.repository.ThingRepositoryMock;
import hello.repository.ThingsH2RepositoryImpl;
import io.vavr.control.Option;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.After;
import org.junit.Test;

public class ThingServicesSpec {

  @After
  public void end() {
    Database database = new H2Database();
    database.handleStatement(
        dbi -> dbi.open(MembersDAO.class),
        d -> {
          d.truncate();
          return null;
        });
  }

  @Test
  public void getNoneFromH2() throws Exception {
    ThingServices service = new ThingServices(new ThingsH2RepositoryImpl());
    CompletableFuture<Option<Thing>> eventuallyThing =
        service
            .obtainThing(1)
            .exceptionally(
                ex -> {
                  System.out.println("Something went wrong" + ex);
                  return Option.none();
                });
    assertThat(eventuallyThing.get(), equalTo(Option.none()));
  }

  @Test
  public void getThingFromH2() throws Exception {
    ThingServices service = new ThingServices(new ThingsH2RepositoryImpl());
    Thing thing = new Thing(1, "test");
    CompletableFuture<Option<Thing>> eventuallyMayBething =
        service
            .save(thing)
            .thenCompose(i -> service.obtainThing(1))
            .exceptionally(
                ex -> {
                  System.out.println("Something went wrong" + ex);
                  return Option.none();
                });
    assertThat(eventuallyMayBething.get(), equalTo(Option.of(thing)));
  }

  @Test
  public void getThingFromMock() throws Exception {
    ThingServices service = new ThingServices(new ThingRepositoryMock());
    CompletableFuture<Option<Thing>> eventuallyThing =
        service
            .obtainThing(1)
            .exceptionally(
                ex -> {
                  System.out.println("Something went wrong" + ex);
                  return Option.none();
                });
    assertThat(eventuallyThing.get(), equalTo(Option.of(new Thing(1, "test"))));
  }

  @Test
  public void saveThing() throws Exception {
    ThingServices service = new ThingServices(new ThingsH2RepositoryImpl());
    CompletableFuture<Integer> eventuallyThing =
        service
            .save(new Thing(11, "test5"))
            .exceptionally(
                ex -> {
                  System.out.println("Something went wrong" + ex);
                  return 0;
                });
    assertThat(eventuallyThing.get(), equalTo(1));
  }

  @Test
  public void failSavingThingInH2() throws Exception {
    ThingServices service = new ThingServices(new ThingsH2RepositoryImpl());
    Thing thing = new Thing(1, "test");
    CompletableFuture<Integer> eventuallyMayBeThing =
        service.save(thing).thenCompose(i -> service.save(thing));
    try {
      eventuallyMayBeThing.get();
    } catch (ExecutionException ce) {
      assertThat(ce.getCause(), instanceOf(ThingAlreadyExist.class));
    }
  }

  @Test
  public void failSavingThingInMock() throws Exception {
    ThingServices service = new ThingServices(new ThingRepositoryMock());
    Thing thing = new Thing(1, "test");
    CompletableFuture<Integer> eventuallyMayBeThing = service.save(thing);
    try {
      eventuallyMayBeThing.get();
    } catch (ExecutionException ce) {
      assertThat(ce.getCause(), instanceOf(ThingAlreadyExist.class));
    }
  }
}
