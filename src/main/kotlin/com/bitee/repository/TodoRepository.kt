package com.bitee.repository

import com.bitee.entities.Todo
import com.bitee.entities.TodoDraft

interface TodoRepository {
    fun getAllTodos():List<Todo>
    fun singleTodo(id:Int):Todo?
    fun createTodo(todo:TodoDraft):Todo
    fun editTodo(todo:TodoDraft,id:Int):Boolean
    fun removeTodo(id:Int):Boolean
}