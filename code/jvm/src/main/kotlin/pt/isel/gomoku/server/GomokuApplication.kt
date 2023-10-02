package pt.isel.gomoku.server

import org.jdbi.v3.core.Jdbi
import org.postgresql.ds.PGSimpleDataSource
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class GomokuApplication {

	@Bean
	fun jdbi(): Jdbi {
		val jdbiDatabaseURL =
			 System.getenv("JDBI_DATABASE_URL")  /** for tests just change to JDBI_TEST_DATABASE_URL **/
		val dataSource = PGSimpleDataSource()
		dataSource.setURL(jdbiDatabaseURL)
		return Jdbi.create(dataSource)
	}
}

fun main(args: Array<String>) {
	runApplication<GomokuApplication>(*args)
}