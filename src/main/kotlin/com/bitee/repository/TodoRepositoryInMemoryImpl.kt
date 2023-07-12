package com.bitee.repository

import com.bitee.entities.Todo
import com.bitee.entities.TodoDraft

class TodoRepositoryInMemoryImpl : TodoRepository {
    private val  todos = mutableListOf<Todo>(
        Todo(1,"this is my first todo",false),
        Todo(2,"Make tea for consumption in the afternoon", true),
        Todo(3,"Create this todo kotlin app",false)
    )
    override fun getAllTodos(): List<Todo> {
        return todos
    }

    override fun singleTodo(id: Int): Todo?{
     return todos.firstOrNull{it.id ==id}
    }

    override fun createTodo(todo: TodoDraft): Todo {
        val newTodo = Todo(
            id =todos.size+1,
            title= todo.title,
            done= todo.done
        )
        todos.add(newTodo)
        return newTodo
    }

    override fun editTodo(todo: TodoDraft, id: Int): Boolean {
        val newTodo = todos.firstOrNull{it.id==id} ?: return false
        newTodo.title = todo.title
        newTodo.done = todo.done
        return true

    }

    override fun removeTodo(id: Int): Boolean {
       return todos.removeIf{it.id==id}
    }
}

