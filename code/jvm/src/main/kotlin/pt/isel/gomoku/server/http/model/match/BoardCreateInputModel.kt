package pt.isel.gomoku.server.http.model.match

data class BoardCreateInputModel(
    val variant: String,
    val size: Int,
    val turn: String,
){
    override fun toString(): String {
        return "${variant}\n${size}\n${turn}"
    }
}


