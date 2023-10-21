package pt.isel.gomoku.server

import org.jdbi.v3.core.Jdbi
import org.postgresql.ds.PGSimpleDataSource
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import pt.isel.gomoku.domain.SystemDomain
import pt.isel.gomoku.domain.UserDomain
import pt.isel.gomoku.server.pipeline.authorization.AuthenticatedUserArgumentResolver
import pt.isel.gomoku.server.pipeline.authorization.AuthenticationInterceptor
import pt.isel.gomoku.server.repository.jdbi.configureWithAppRequirements
import java.time.Duration


@SpringBootApplication
class GomokuApplication {

    @Bean
    fun userDomainConfig() =
        UserDomain(
            Duration.ofDays(1),
            256 / 8,
            BCryptPasswordEncoder()
        )

    @Bean
    fun systemDomainConfig() =
        SystemDomain(0.1f)

    @Bean
    fun jdbi(): Jdbi {
        // val jdbiDatabaseURL = System.getenv("JDBI_DATABASE_URL")
        val jdbiDatabaseURL = System.getenv("JDBI_TEST_DATABASE_URL")
        val dataSource = PGSimpleDataSource()
        dataSource.setURL(jdbiDatabaseURL)
        return Jdbi.create(dataSource).configureWithAppRequirements()
    }
}

@Component
class PipelineConfigure(
    val authenticationInterceptor: AuthenticationInterceptor,
    val authenticatedUserArgumentResolver: AuthenticatedUserArgumentResolver
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authenticationInterceptor)
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(authenticatedUserArgumentResolver)
    }
}

fun main(args: Array<String>) {
    runApplication<GomokuApplication>(*args)
}