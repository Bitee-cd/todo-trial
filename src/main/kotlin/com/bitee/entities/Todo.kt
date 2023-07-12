package com.bitee.entities

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow

object TodoTable : IntIdTable("todos") {
    val title = varchar("title", 100)
    val done = bool("done").default(false)
}

fun ResultRow.toTodo() = Todo(
    id = this[TodoTable.id].value,
    title = this[TodoTable.title],
    done = this[TodoTable.done]
)

@Serializable
data class Todo (
    val   id: Int,
    var title: String,
    var done: Boolean,
)