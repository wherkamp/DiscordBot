package me.kingtux.phphideout

enum class SQLQueries {
    CREATE_USER_TABLE("CREATE TABLE IF NOT EXISTS users (" +
            "userid integer PRIMARY KEY AUTOINCREMENT," +
            "discord_id text," +
            "last_given_thx text," +
            "thx_points INTEGER" +
            ");" +
            ""),
    INSERT_USER("INSERT INTO users (discord_id, last_given_thx, thx_points) VALUES (?,?,?)"),
    ADD_THX("UPDATE users SET thx_points=? WHERE discord_id=?"),
    GET_USER("SELECT * FROM users WHERE discord_id=?"),
    UPDATE_LAST_THX("UPDATE users SET last_given_thx=? WHERE discord_id=?"),
    getPoints("SELECT * FROM users ORDER BY thx_points DESC");

    val sqliteQuery: String

    constructor(sqliteQuery: String) {
        this.sqliteQuery = sqliteQuery
    }

    override fun toString(): String {
        return sqliteQuery;
    }
}