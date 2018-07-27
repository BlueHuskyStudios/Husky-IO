package org.bh.tools.io.databases.exposed

import jdk.javadoc.internal.doclets.formats.html.*
import org.bh.tools.io.databases.*
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*

typealias X = SqlExpressionBuilder.()->Op<Boolean>


/**
 * @author Ben
 * @since 2018-07-22
 */
class ExposedDB
    <Contents, ContentsId>
    (val basis: org.jetbrains.exposed.sql.Database)
    : org.bh.tools.io.databases.Database<X, Contents, ExposedDB<Contents, ContentsId>>
    where Contents: Entity<ContentsId>, ContentsId: Comparable<ContentsId> {

    override fun retrieve(using: X, result: (Contents) -> Unit) {

        transaction(basis) {
            StarWarsFilm.find {}
        }
    }
}


private object StarWarsFilms : IntIdTable() {
    val sequelId = integer("sequel_id").uniqueIndex()
    val name = varchar("name", 50)
    val director = varchar("director", 50)
}
private class StarWarsFilm(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<StarWarsFilm>(StarWarsFilms)

    var sequelId by StarWarsFilms.sequelId
    var name     by StarWarsFilms.name
    var director by StarWarsFilms.director
}
