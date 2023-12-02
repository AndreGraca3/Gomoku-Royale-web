package pt.isel.gomoku.server.repository.dto

data class CollectionWrapper<T>(
    val results: List<T>,
    val collectionSize: Int,
)

class PaginationInputsWrapper(page: Int?, limit: Int?) {
    val page = page ?: 0
    val limit = limit ?: Int.MAX_VALUE
    val skip: Int
        get() = (page - 1) * limit
}