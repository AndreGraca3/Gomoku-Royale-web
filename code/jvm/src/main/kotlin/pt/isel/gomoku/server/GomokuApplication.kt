package pt.isel.gomoku.server

import org.jdbi.v3.core.Jdbi
import org.postgresql.ds.PGSimpleDataSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import pt.isel.gomoku.server.service.core.SecurityManager
import pt.isel.gomoku.server.pipeline.authorization.AuthenticatedUserArgumentResolver
import pt.isel.gomoku.server.pipeline.authorization.AuthenticationInterceptor
import pt.isel.gomoku.server.repository.jdbi.configureWithAppRequirements
import java.time.Duration


@SpringBootApplication
class GomokuApplication {

    @Bean
    fun userDomainConfig() =
        SecurityManager(
            Duration.ofDays(1),
            256 / 8,
            BCryptPasswordEncoder()
        )

    @Value("\${JDBI_DATABASE_URL}")
    private lateinit var defaultJdbiDatabaseURL: String

    @Value("\${JDBI_TEST_DATABASE_URL}")
    private lateinit var testJdbiDatabaseURL: String

    @Bean
    @Profile("!test")
    fun jdbi(): Jdbi {
        val dataSource = PGSimpleDataSource()
        dataSource.setURL(defaultJdbiDatabaseURL)
        return Jdbi.create(dataSource).configureWithAppRequirements()
    }

    @Bean
    @Profile("test")
    fun jdbiTest(): Jdbi {
        val dataSource = PGSimpleDataSource()
        dataSource.setURL(testJdbiDatabaseURL)
        return Jdbi.create(dataSource).configureWithAppRequirements()
    }

}

@Component
class PipelineConfigure(
    val authenticationInterceptor: AuthenticationInterceptor,
    val authenticatedUserArgumentResolver: AuthenticatedUserArgumentResolver,
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