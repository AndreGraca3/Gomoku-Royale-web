package pt.isel.gomoku.server.http.model

import com.fasterxml.jackson.annotation.JsonIgnore
import pt.isel.gomoku.server.http.response.siren.*

abstract class OutputModel {

    @JsonIgnore
    abstract fun getSirenClasses(): List<SirenClass>

    fun toSirenObject(
        entities: List<SubEntity>? = null,
        actions: List<SirenAction>? = null,
        links: List<SirenLink>,
    ) = Siren(
        clazz = getSirenClasses(),
        this,
        entities = entities,
        actions = actions,
        links = links
    )
}