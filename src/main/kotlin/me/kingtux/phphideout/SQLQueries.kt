package me.kingtux.phphideout

enum class SQLQueries {
    CREATE_USER_TABLE("CREATE TABLE IF NOT EXISTS users (\n" +
            "\tid integer PRIMARY KEY AUTOINCREMENT,\n" +
            "\tdiscord_id text,\n" +
            "\tlast_given_thx text,\n" +
            "\tpoints integer,\n" +
            "\tlevel integer\n" +
            ");\n" +
            "\n"),
    INSERT_USER("INSERT INTO users (discord_id, last_given_thx, level) VALUES (?,?,?)"),
    ADD_THX("UPDATE users SET points=? WHERE discord_id=?"),
    GET_USER("SELECT * FROM users WHERE discord_id=?"),
    UPDATE_LAST_THX("UPDATE users SET last_given_thx=? WHERE discord_id=?"),
    getPoints("SELECT * FROM users ORDER BY points DESC"),
    ADDPoint("UPDATE users SET points=? WHERE discord_id=?"),
    LEVELUP("UPDATE users SET level=? WHERE discord_id=?") ;

    val sqliteQuery: String

    constructor(sqliteQuery: String) {
        this.sqliteQuery = sqliteQuery
    }

    override fun toString(): String {
        return sqliteQuery;
    }
}