package com.bitee.plugins

import com.bitee.entities.TodoTable
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabase() {
    val password = environment.config.property("ktor.mysql.password").getString()
    val user = environment.config.property("ktor.mysql.user").getString()
    val jdbcUrl = environment.config.property("ktor.mysql.url").getString()
    val driverClassName = environment.config.property("ktor.mysql.driverClassName").getString()

    Database.connect(
        driver = driverClassName,
        url = jdbcUrl,
        user = user,
        password = password
    )

    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.createMissingTablesAndColumns(TodoTable)
    }
}