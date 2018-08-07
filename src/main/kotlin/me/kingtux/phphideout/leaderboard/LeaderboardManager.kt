package me.kingtux.phphideout.leaderboard

import me.kingtux.phphideout.Bot
import sx.blah.discord.handle.obj.IChannel
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.util.RequestBuffer


class LeaderboardManager(val bot: Bot) {
  private val currentLeaderboards = mutableMapOf<IMessage, Leaderboard>()

  fun createLeaderboard(iChannel: IChannel) {
    val leaderboard = buildBoard();

    var message: IMessage
    RequestBuffer.request {
      message = iChannel.sendMessage(leaderboard.toEmbedObject(bot))

    }

  }

  private fun buildBoard(): Leaderboard {
    val scored = bot.userManager.getUsersbyScores();
    return Leaderboard(scored);
  }

}