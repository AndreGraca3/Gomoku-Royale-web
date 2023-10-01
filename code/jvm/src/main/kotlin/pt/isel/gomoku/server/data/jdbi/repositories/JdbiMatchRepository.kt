package pt.isel.gomoku.server.data.jdbi.repositories

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.gomoku.domain.model.Board
import pt.isel.gomoku.server.data.interfaces.IMatchData
import pt.isel.gomoku.server.structs.dto.outbound.MatchIn
import pt.isel.gomoku.server.structs.dto.outbound.MatchOUT

@Component
class JdbiMatchRepository(private val jdbi: Jdbi): IMatchData {

    override fun insertMatch(matchIn: MatchIn): Int {
        var id = 0
        jdbi.useHandle<Exception> { handle ->
            id = handle.createUpdate(
                "insert into match (variants, board) " +
                        "values (:variants, :board) returning id"
            )
                .bindBean(matchIn)
                .executeAndReturnGeneratedKeys("id")
                .mapTo(Int::class.java)
                .first()
        }
        return id
    }

    override fun getMatchById(id: Int): MatchOUT? {
        return jdbi.withHandle<MatchOUT?, Exception> { handle ->
            handle.createQuery("select variants, board from match where id = :id")
                .bind("id", id)
                .map { rs, _, _ ->
                    MatchOUT(
                        rs.getString("variants"),
                        rs.getObject("board") as Board
                    )
                }
                /*.mapToBean(UserOUT::class.java)*/
                .singleOrNull()
        }
    }
}