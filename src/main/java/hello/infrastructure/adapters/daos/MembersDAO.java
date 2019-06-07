package hello.infrastructure.adapters.daos;

import hello.infrastructure.adapters.mappers.ThingDTOMapper;
import hello.repository.dtos.ThingDTO;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

public interface MembersDAO extends DAO {

  @SqlUpdate("insert into members (memberid, membername) values (:id, :name)")
  void insert(@Bind("id") int id, @Bind("name") String name);

  @SqlUpdate("truncate table members")
  void truncate();

  @SqlQuery("select memberid, membername from members where memberid = :id")
  @RegisterMapper(ThingDTOMapper.class)
  ThingDTO findById(@Bind("id") int id);

  void close();
}
