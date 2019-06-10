package hello.infrastructure.adapters.h2;

import hello.infrastructure.Database;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcConnectionPool;

public class H2Database extends Database {

  private static final DataSource ds = JdbcConnectionPool.create("jdbc:h2:~/test", "", "");

  public H2Database() {
    super(ds);
  }
}
