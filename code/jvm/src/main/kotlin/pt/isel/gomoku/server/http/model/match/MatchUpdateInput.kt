package pt.isel.gomoku.server.http.model.match

data class MatchUpdateInput(
    val id : String,
    val winner: Int?
)


