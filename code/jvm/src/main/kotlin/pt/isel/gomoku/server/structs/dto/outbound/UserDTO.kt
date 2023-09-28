package pt.isel.gomoku.server.structs.dto.outbound

data class UserDTO(val id: String) {
}

data class UserOUT(val id: String, val name: String, val email: String, val password: String) {
}