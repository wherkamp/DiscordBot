package me.kingtux.phphideout

import java.io.File
import java.sql.Connection
import java.sql.DriverManager

class Database {
    val connection: Connection;
    private val sqlFile: File;

    constructor(path: String) {
        this.sqlFile = File(path);
        Class.forName("org.sqlite.JDBC");
        this.connection = DriverManager
                .getConnection("jdbc:sqlite:" + sqlFile.absolutePath);
        connection.createStatement().execute(SQLQueries.CREATE_USER_TABLE.sqliteQuery)
    }
}