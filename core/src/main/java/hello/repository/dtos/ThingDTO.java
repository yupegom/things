package hello.repository.dtos;

import hello.domain.Thing;

public final class ThingDTO {
  private final int id;
  private final String name;

  public ThingDTO(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public int getId() {
    return this.id;
  }

  public Thing toDomainThing() {
    return new Thing(this.id, this.name);
  }
}
