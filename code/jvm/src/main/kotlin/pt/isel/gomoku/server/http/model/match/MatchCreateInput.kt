package pt.isel.gomoku.server.http.model.match

class MatchCreateInput(
    val isPrivate: Boolean,
    val size: Int?,
    val variant: String?
)