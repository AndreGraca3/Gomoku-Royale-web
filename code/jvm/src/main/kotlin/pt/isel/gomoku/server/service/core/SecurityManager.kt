package pt.isel.gomoku.server.service.core

import org.springframework.security.crypto.password.PasswordEncoder
import pt.isel.gomoku.domain.Token
import java.security.SecureRandom
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

class SecurityManager(
    val tokenTtl: Duration,
    private val tokenSizeInBytes: Int,
    private val passwordEncoder: PasswordEncoder
) {

    fun generateTokenValue(): String =
        ByteArray(tokenSizeInBytes).let { byteArray ->
            SecureRandom.getInstanceStrong().nextBytes(byteArray)
            Base64.getUrlEncoder().encodeToString(byteArray)
        }

    fun canBeToken(token: String): Boolean = try {
        Base64.getUrlDecoder()
            .decode(token).size == tokenSizeInBytes
    } catch (ex: IllegalArgumentException) {
        false
    }

    fun isTokenTimeValid(token: Token): Boolean {
        val now = LocalDateTime.now()
        return token.createdAt.isBefore(now) &&
                (Duration.between(token.createdAt, now)) <= tokenTtl
    }

    fun getTokenExpiration(token: Token): LocalDateTime {
        return token.createdAt + tokenTtl
    }

    fun isSafePassword(password: String) = password.length > 4 && password.contains(Regex("[0-9]"))
}