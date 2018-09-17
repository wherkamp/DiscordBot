package org.phphideout.discordbot.listeners

import org.phphideout.discordbot.Bot
import sx.blah.discord.api.events.IListener
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionEvent
import sx.blah.discord.handle.impl.obj.ReactionEmoji
import sx.blah.discord.util.RequestBuffer

class ReactionListener(private val bot: Bot) : IListener<ReactionEvent> {
    override fun handle(event: ReactionEvent) {
      println(event.user.name+ " Reacted with " + event.reaction.emoji.name)
      if(!event.user.isBot) {
        if (bot.leaderboardManager.currentLeaderboards.containsKey(event.message)) {
            if (event.reaction.emoji.name == ReactionEmoji.of("➡").name ) {
              val leaderboard = bot.leaderboardManager.currentLeaderboards[event.message] ?: return;
              leaderboard.currentPage = leaderboard.currentPage+1;
              bot.leaderboardManager.currentLeaderboards.remove(event.message)
              val channel = event.message.channel

                event.message.delete()
                val message = channel.sendMessage(leaderboard.toEmbedObject(bot))
                bot.leaderboardManager.react(message);
              val currentLeaderboards =  bot.leaderboardManager.currentLeaderboards
              currentLeaderboards.put(message, leaderboard)
              bot.leaderboardManager.currentLeaderboards = currentLeaderboards
              println(leaderboard.currentPage.toString() + " "+ bot.leaderboardManager.currentLeaderboards[message]!!.currentPage );
            } else if (event.reaction.emoji.name == ReactionEmoji.of("⬅").name) {
              val leaderboard = bot.leaderboardManager.currentLeaderboards[event.message] ?: return;
              leaderboard.currentPage = leaderboard.currentPage-1;
              bot.leaderboardManager.currentLeaderboards.remove(event.message)
              val channel = event.message.channel

                event.message.delete()
                val message = channel.sendMessage(leaderboard.toEmbedObject(bot))
                bot.leaderboardManager.react(message);
              val currentLeaderboards =  bot.leaderboardManager.currentLeaderboards
              currentLeaderboards.put(message, leaderboard)
              bot.leaderboardManager.currentLeaderboards = currentLeaderboards
              println(leaderboard.currentPage.toString() + " "+ bot.leaderboardManager.currentLeaderboards[message]!!.currentPage );

            }
        }
      }
    }


}