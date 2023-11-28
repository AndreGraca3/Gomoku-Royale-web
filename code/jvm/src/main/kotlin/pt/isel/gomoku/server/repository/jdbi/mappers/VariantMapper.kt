package pt.isel.gomoku.server.repository.jdbi.mappers

import org.jdbi.v3.core.mapper.ColumnMapper
import org.jdbi.v3.core.statement.StatementContext
import pt.isel.gomoku.domain.Variant
import java.sql.ResultSet
import java.sql.SQLException

class VariantMapper : ColumnMapper<Variant> {
    @Throws(SQLException::class)
    override fun map(r: ResultSet, columnNumber: Int, ctx: StatementContext?): Variant {
        return Variant.from(r.getString(columnNumber))
    }
}