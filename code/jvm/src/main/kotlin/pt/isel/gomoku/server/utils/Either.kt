package pt.isel.gomoku.server.utils

sealed class Either<out L, out R> {
    data class Left<out L>(val value: L) : Either<L, Nothing>()
    data class Right<out R>(val value: R) : Either<Nothing, R>()
}

// Functions for when using Either to represent success or failure
fun <L> failure(error: L) = Either.Left(error)
fun <R> success(value: R) = Either.Right(value)

typealias Failure<F> = Either.Left<F>
typealias Success<S> = Either.Right<S>
