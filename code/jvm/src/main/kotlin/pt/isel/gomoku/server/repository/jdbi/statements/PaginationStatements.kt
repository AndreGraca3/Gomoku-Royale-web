package pt.isel.gomoku.server.repository.jdbi.statements

object PaginationStatements {
    const val PAGINATION_PREFIX =
        """
            count(*) over() as count
        """

    const val PAGINATION_SUFFIX =
        """
            OFFSET :skip
            LIMIT :limit
        """
}