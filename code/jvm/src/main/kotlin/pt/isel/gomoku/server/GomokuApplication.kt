package pt.isel.gomoku.server

import org.jdbi.v3.core.Jdbi
import org.postgresql.ds.PGSimpleDataSource
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import pt.isel.gomoku.server.data.transactionManager.DataExecutor

@SpringBootApplication
class GomokuApplication {

	@Bean
	fun jdbi(): Jdbi {
		val jdbiDatabaseURL =
			 System.getenv("JDBI_DATABASE_URL") ?:  /** for tests just change the JDBC_DATABASE_URL **/
				"jdbc:postgresql://localhost:5432/daw?user=postgres&password=ISEL123"
		val dataSource = PGSimpleDataSource()
		dataSource.setURL(jdbiDatabaseURL)
		return Jdbi.create(dataSource)
	}

	@Bean
	fun dataExecutor(): DataExecutor {
		return DataExecutor(jdbi())
	}
}

fun main(args: Array<String>) {
	runApplication<GomokuApplication>(*args)
}