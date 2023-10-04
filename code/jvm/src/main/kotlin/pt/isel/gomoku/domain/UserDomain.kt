package pt.isel.gomoku.domain

import org.springframework.stereotype.Component
import org.springframework.security.crypto.password.PasswordEncoder
import java.security.SecureRandom
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

class UserDomain(
    val tokenTtl: Duration,
    val maxTokensPerUser: Int,
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
        return token.createdAt <= now &&
                (Duration.between(now, token.createdAt)) <= tokenTtl
    }

    fun getTokenExpiration(token: Token): LocalDateTime {
        return token.createdAt + tokenTtl
    }

    fun isSafePassword(password: String) = password.length > 4 && password.contains(Regex("[0-9]"))

    val maxNumberOfTokensPerUser = maxTokensPerUser
}