package me.kingtux.phphideout

import sx.blah.discord.handle.obj.IUser

class UserManager(val bot: Bot) {

    public fun createUser(userID: Long) {
        val query = SQLQueries.INSERT_USER.sqliteQuery
        val prepare = bot.database.connection.prepareStatement(query);
        prepare.setString(1, userID.toString())
        prepare.setInt(2, 0)
        prepare.setString(3, (0).toString())
        prepare.execute()
        prepare.close()
    }

    public fun hasUser(userID: Long): Boolean {
        var returnThis = false;
        val query = SQLQueries.GET_USER.sqliteQuery
        val prepare = bot.database.connection.prepareStatement(query);
        prepare.setString(1, userID.toString())
        prepare.execute()
        while (prepare.resultSet.next()) {
            returnThis = true;
        }
        prepare.close()
        return returnThis;
    }

    public fun thxUser(thanker: IUser, reciever: IUser) {
        updateLastThx(thanker)
        val query = SQLQueries.ADD_THX.sqliteQuery
        val prepare = bot.database.connection.prepareStatement(query);
        prepare.setInt(1, (getThxPoints(reciever) + 1))
        prepare.setString(2, reciever.stringID)
        prepare.execute()
        prepare.close()
    }

    public fun canThx(sender: IUser): Boolean {
        var returnThis = true;
        val query = SQLQueries.GET_USER.sqliteQuery
        val prepare = bot.database.connection.prepareStatement(query);
        prepare.setString(1, sender.stringID)
        val result = prepare.executeQuery()
        while (result.next()) {
            returnThis = System.currentTimeMillis() >= result.getString("last_given_thx").toLong() + 60000;
        }
        prepare.close()
        return returnThis;
    }

    private fun getThxPoints(reciever: IUser): Int {
        var returnThis = 0;
        val query = SQLQueries.GET_USER.sqliteQuery
        val prepare = bot.database.connection.prepareStatement(query);
        prepare.setString(1, reciever.stringID)
        val result = prepare.executeQuery()
        while (result.next()) {
            returnThis = result.getInt("thx_points");
        }
        prepare.close()
        return returnThis;
    }

    public fun getUsersbyScores(): Map<Long, Int> {
        val returnThis = mutableMapOf<Long, Int>()
        val query = SQLQueries.GET_USER.sqliteQuery
        val prepare = bot.database.connection.prepareStatement(query);
        val result = prepare.executeQuery()
        while (result.next()) {
            returnThis.put(result.getString("discord_id").toLong(), result.getInt("thx_points"))
        }
        result.close()
        return returnThis
    }

    private fun updateLastThx(thanker: IUser) {
        val query = SQLQueries.UPDATE_LAST_THX.sqliteQuery
        val prepare = bot.database.connection.prepareStatement(query);
        prepare.setString(1, System.currentTimeMillis().toString())
        prepare.setString(2, thanker.stringID)
        prepare.execute()
        prepare.close()
    }
}