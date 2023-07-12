package com.bitee.repository

import com.bitee.entities.Todo
import com.bitee.entities.TodoDraft
import com.bitee.entities.TodoTable
import com.bitee.entities.toTodo
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object TodoRepositoryDatabaseImpl : TodoRepository {
    override fun getAllTodos(): List<Todo> {
        val todos = transaction {
            TodoTable
                .selectAll()
                .map { it.toTodo() }
        }

        return todos
    }

    override fun singleTodo(id: Int): Todo? {
        val maybeTodo = transaction {
            TodoTable
                .select { TodoTable.id eq id }
                .firstOrNull()
                ?.toTodo()
        }

        return maybeTodo
    }

    override fun createTodo(todo: TodoDraft): Todo {
        val maybeTodo = transaction {
            TodoTable.insert {
                it[title] = todo.title
                it[done] = todo.done
            }.resultedValues
                ?.firstOrNull()
                ?.toTodo()
        }

        return maybeTodo ?: error("An error occurred")
    }

    override fun editTodo(todo: TodoDraft, id: Int): Boolean {
        val updated = transaction {
            TodoTable.update({ TodoTable.id eq id }) {
                it[title] = todo.title
                it[done] = todo.done
            }
        } > 0

        return updated
    }

    override fun removeTodo(id: Int): Boolean {
        val deleted = transaction {
            TodoTable.deleteWhere { TodoTable.id eq id }
        } > 0
        return deleted;
    }

}