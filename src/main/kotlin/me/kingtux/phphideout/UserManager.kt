package me.kingtux.phphideout

import sx.blah.discord.api.internal.json.objects.EmbedObject
import sx.blah.discord.handle.obj.IUser
import sx.blah.discord.util.EmbedBuilder

class UserManager(val bot: Bot) {

  public fun createUser(userID: Long) {
    val query = SQLQueries.INSERT_USER.sqliteQuery
    val prepare = bot.database.connection.prepareStatement(query);
    prepare.setString(1, userID.toString())
    prepare.setString(2, (0).toString())
    prepare.setInt(3, 0)
    prepare.execute()
    prepare.close()
  }

  public fun hasUser(userID: Long): Boolean {
    var returnThis = false;
    val query = SQLQueries.GET_USER.sqliteQuery
    val prepare = bot.database.connection.prepareStatement(query);
    prepare.setString(1, userID.toString())
    val resultSet = prepare.executeQuery()
    while (resultSet.next()) {
      returnThis = true;
    }
    prepare.close()
    return returnThis;
  }

  public fun thxUser(thanker: IUser, reciever: IUser) {
    updateLastThx(thanker)
    val query = SQLQueries.ADD_THX.sqliteQuery
    val prepare = bot.database.connection.prepareStatement(query);
    prepare.setInt(1, (getPoints(reciever) + 25))
    prepare.setString(2, reciever.stringID)
    prepare.execute()
    prepare.close()

  }

  public fun userChat(sender: IUser) {
    val query = SQLQueries.ADD_THX.sqliteQuery
    val prepare = bot.database.connection.prepareStatement(query);
    prepare.setInt(1, (getPoints(sender) + 1))
    prepare.setString(2, sender.stringID)
    prepare.execute()
    prepare.close()

  }


  public fun needsLevelUp(sender: IUser): Boolean {
    val points = getPoints(sender)
    val level = getLevel(sender)
    val requiredPointsToLevelUp = getLevelRequirements(level + 1);
    return points >= requiredPointsToLevelUp;
  }

  public fun generateLevelUpMesage(iuser: IUser, rank: Int): EmbedObject {
    val embed = EmbedBuilder()
    //        embed.withTitle("Level UP");
    embed.withColor(66, 134, 244)
    embed.withDescription("${iuser.mention()} has just ranked up to ${rank}")
    return embed.build()
    //return "${iuser.name} has just ranked up to ${rank}"
  }

  public fun levelUp(sender: IUser) {
    val query = SQLQueries.LEVELUP.sqliteQuery
    val prepare = bot.database.connection.prepareStatement(query);
    prepare.setInt(1, getLevel(sender) + 1)
    prepare.setString(2, sender.stringID)
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

  public fun getRank(who: IUser): Int {
    val users = getUsersbyScores();
    var rank = 1;
    for (e in users.entries) {
      if (e.key == who.longID) {
        return rank;
      }
      rank++;
    }
    return rank;
  }

  public fun getPoints(reciever: IUser): Int {
    var returnThis = 0;
    val query = SQLQueries.GET_USER.sqliteQuery
    val prepare = bot.database.connection.prepareStatement(query);
    prepare.setString(1, reciever.stringID)
    val result = prepare.executeQuery()
    while (result.next()) {
      returnThis = result.getInt("points");
    }
    prepare.close()
    return returnThis;
  }

  public fun getLevel(reciever: IUser): Int {
    var returnThis = 0;
    val query = SQLQueries.GET_USER.sqliteQuery
    val prepare = bot.database.connection.prepareStatement(query);
    prepare.setString(1, reciever.stringID)
    val result = prepare.executeQuery()
    while (result.next()) {
      returnThis = result.getInt("level");
    }
    prepare.close()
    return returnThis;
  }

  public fun getUsersbyScores(): LinkedHashMap<Long, Int> {
    val returnThis = LinkedHashMap<Long, Int>();
    val query = SQLQueries.getPoints.sqliteQuery
    val result = bot.database.connection.createStatement().executeQuery(query);
    while (result.next()) {
      println("Next called")
      println(result.getString("discord_id") + " " + result.getInt("points"))
      returnThis.put(result.getString("discord_id").toLong(), result.getInt("points"))
    }
    result.close()
    // val returnThisOne = Util.sortByValue(returnThis);
    return returnThis;
  }

  public fun getLevelRequirements(n: Int): Int {
    return ((6 * Math.pow(n.toDouble(), 3.toDouble()) + 119 * n + 100).toInt());
  }

  private fun updateLastThx(thanker: IUser) {
    val query = SQLQueries.UPDATE_LAST_THX.sqliteQuery
    val prepare = bot.database.connection.prepareStatement(query);
    prepare.setString(1, System.currentTimeMillis().toString())
    prepare.setString(2, thanker.stringID)
    prepare.execute()
    prepare.close()
  }

  fun unRegister(longID: Long) {
    val query = SQLQueries.REMOVE_USER.sqliteQuery
    val prepare = bot.database.connection.prepareStatement(query);
    prepare.setString(1, longID.toString())
    prepare.execute()
    prepare.close()

  }
}