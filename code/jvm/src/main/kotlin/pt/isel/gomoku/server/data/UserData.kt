package pt.isel.gomoku.server.data

import org.springframework.stereotype.Component
import pt.isel.gomoku.server.structs.dto.outbound.UserDTO
import pt.isel.gomoku.server.structs.dto.outbound.UserOUT

@Component
class UserData: IUserData {

    override fun addUser(): UserDTO {
        return UserDTO("Best id ever")
    }

    override fun getUser(id: String): UserOUT {
        return UserOUT("Best id ever", "Best name ever", "Best email ever", "Best password ever")
    }

}