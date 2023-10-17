package pt.isel.gomoku.server.http.model.match

class MatchCreationInput(
    val isPrivate: Boolean,
    val size: Int?,
    val variant: String?
)