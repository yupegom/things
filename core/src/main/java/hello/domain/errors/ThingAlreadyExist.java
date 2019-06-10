package hello.domain.errors;

public class ThingAlreadyExist extends Error {

  public ThingAlreadyExist() {
    this.msg = "The thing is already in the DB";
  }
}
