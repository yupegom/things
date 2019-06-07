package hello.domain;

public final class Thing {

  private final int id;
  private final String name;

  public Thing(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  @Override
  public boolean equals(Object o) {

    // If the object is compared with itself then return true
    if (o == this) {
      return true;
    }

    /* Check if o is an instance of Complex or not
    "null instanceof [type]" also returns false */
    if (!(o instanceof Thing)) {
      return false;
    }

    // typecast o to Complex so that we can compare data members
    Thing t = (Thing) o;

    // Compare the data members and return accordingly
    return name.equals(t.getName()) && id == t.getId();
  }
}
