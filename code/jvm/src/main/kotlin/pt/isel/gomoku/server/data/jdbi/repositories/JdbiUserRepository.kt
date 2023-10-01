package pt.isel.gomoku.server.data.jdbi.repositories

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.gomoku.server.data.interfaces.IUserData
import pt.isel.gomoku.server.structs.dto.outbound.UserIn
import pt.isel.gomoku.server.structs.dto.outbound.UserOUT

/** Question:
 * It's necessary to have JdbiUserRepository class receiving Jbdi in constructor?
 * couldn't I just give it a Handle?
 * **/
@Component
class JdbiUserRepository(private val jdbi: Jdbi) : IUserData {
    override fun insertUser(userIn: UserIn): Int {
        var id = 0
        jdbi.useHandle<Exception> { handle ->
            id = handle.createUpdate(
                "insert into users (name, email, password, avatar) " +
                        "values (:name, :email, :password, :avatar) returning id"
            )
                .bindBean(userIn)
                .executeAndReturnGeneratedKeys("id")
                .mapTo(Int::class.java)
                .first()
        }
        return id
    }

    override fun getUserById(id: Int): UserOUT? {
        return jdbi.withHandle<UserOUT?, Exception> { handle ->
            handle.createQuery("select name, email, password, avatar, rank, numberOfGames from users where id = :id")
                .bind("id", id)
                .map { rs, _, _ ->
                    UserOUT(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("avatar"),
                        rs.getInt("rank"),
                        rs.getInt("numberOfGames")
                    )
                }
                /*.mapToBean(UserOUT::class.java)*/
                .singleOrNull()
        }
    }

    override fun changeName(id: Int, newName: String) {
        jdbi.useHandle<Exception> { handle ->
            handle.createUpdate("update users set name=:name where id=:id")
                .bind("name", newName)
                .bind("id", id)
                .execute()
        }
    }

    override fun getUserByEmail(email: String): UserOUT {
        return jdbi.withHandle<UserOUT?, Exception> { handle ->
            handle.createQuery("select name, email, password, avatar, rank, numberOfGames from users where email = :email")
                .bind("email", email)
                .map { rs, _, _ ->
                    UserOUT(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("avatar"),
                        rs.getInt("rank"),
                        rs.getInt("numberOfGames")
                    )
                }
                /*.mapToBean(UserOUT::class.java)*/
                .singleOrNull()
        } ?: throw Exception("User already exists!")
    }
}