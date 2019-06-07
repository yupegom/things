package hello.infrastructure.adapters.mappers;

import hello.repository.dtos.ThingDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class ThingDTOMapper implements ResultSetMapper<ThingDTO> {

  public ThingDTO map(int index, ResultSet r, StatementContext ctx) throws SQLException {
    return new ThingDTO(Integer.parseInt((r.getString("memberid"))), r.getString("membername"));
  }
}
