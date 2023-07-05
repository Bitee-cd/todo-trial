package com.bitee.routing

import com.bitee.entities.Todo
import com.bitee.entities.TodoDraft
import com.bitee.repository.TodoRepository
import com.bitee.repository.TodoRepositoryImpl
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*

fun Application.configureRouting() {
   val repository : TodoRepository = TodoRepositoryImpl();
    routing {

        get("/") {
            call.respondText("Hello World!")
        }
        get("/todos"){
            call.respond(repository.getAllTodos())
        }
        get("/todos/{id}"){
            val id = call.parameters["id"]?.toIntOrNull();
            if (id==null){
                 call.respond(HttpStatusCode.BadRequest,"id has to be a number")
                return@get
            }
            val todo = repository.singleTodo(id)
            if(todo==null){
                call.respond(HttpStatusCode.NotFound,"todo with the particular id: $id is not found")
                return@get
            }
            call.respond(todo)
        }
        post("/todos"){
            val todo=call.receive<TodoDraft>()
            val newTodo = repository.createTodo(todo)
            call.respond(HttpStatusCode.Created,newTodo)
        }
        put("/todos/{id}"){
            val id = call.parameters["id"]?.toIntOrNull();
            val todo = call.receive<TodoDraft>()
            if(id==null){
                call.respond(HttpStatusCode.BadRequest,"Id must be a number")
                return@put
            }
            val editedTodo = repository.editTodo(todo,id)
            if(editedTodo){
                call.respond(HttpStatusCode.OK,"todo with id: $id updated successfully")
            }else{
                call.respond(HttpStatusCode.NotFound,"todo with id $id not found")
            }
        }
        delete("/todos/{id}"){
            val id = call.parameters["id"]?.toIntOrNull()
            if (id==null){
                call.respond(HttpStatusCode.BadRequest,"Id must be a number")
                return@delete
            }
            val deleted = repository.removeTodo(id)
            if(deleted){
                call.respond(HttpStatusCode.OK,"todo with $id removed successfully")
            }
            else{
                call.respond(HttpStatusCode.NotFound,"todo with $id not found")
            }
        }
    }
}
