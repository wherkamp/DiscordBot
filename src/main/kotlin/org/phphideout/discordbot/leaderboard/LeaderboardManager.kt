package org.phphideout.discordbot.leaderboard

import org.phphideout.discordbot.Bot
import sx.blah.discord.handle.impl.obj.ReactionEmoji
import sx.blah.discord.handle.obj.IChannel
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.util.RequestBuffer


class LeaderboardManager(val bot: Bot) {
   var currentLeaderboards = mutableMapOf<IMessage, Leaderboard>()

  fun createLeaderboard(iChannel: IChannel) {
    val leaderboard = buildBoard();
    val message = iChannel.sendMessage(leaderboard.toEmbedObject(bot))

    react(message)

    currentLeaderboards[message] = leaderboard

  }
  fun react(message: IMessage){
    RequestBuffer.request {
      message.addReaction(ReactionEmoji.of("⬅"))
    }
    RequestBuffer.request {
      message.addReaction(ReactionEmoji.of("➡"))
    }
  }
  private fun buildBoard(): Leaderboard {
    val scored = bot.userManager.getUsersbyScores();
    return Leaderboard(scored);
  }

}