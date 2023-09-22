package pt.isel.gomoku.data

import org.springframework.stereotype.Component
import pt.isel.gomoku.structs.dto.outbound.UserDTO

@Component
class UserData: IUserData {
    override fun addUser(): UserDTO {
        return UserDTO("Best id ever")
    }
}