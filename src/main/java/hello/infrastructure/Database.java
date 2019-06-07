package hello.infrastructure;

import hello.infrastructure.adapters.daos.DAO;
import java.util.function.Function;
import javax.sql.DataSource;
import org.skife.jdbi.v2.DBI;

public abstract class Database {

  private final DataSource ds;
  private final DBI dbi;

  public Database(DataSource ds) {
    this.ds = ds;
    this.dbi = new DBI(ds);
  }

  public <T extends DAO, U> U handleStatement(Function<DBI, T> open, Function<T, U> handler) {
    DBI dbi = getDBI();
    T dao = open.apply(dbi);
    U r = handler.apply(dao);
    dao.close();
    return r;
  }

  private DBI getDBI() {
    return dbi;
  }
}
