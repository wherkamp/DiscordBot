package me.kingtux.phphideout.leaderboard

import me.kingtux.phphideout.Bot
import sx.blah.discord.handle.obj.IChannel
import sx.blah.discord.handle.obj.IMessage


class LeaderboardManager(val bot: Bot) {
    private val currentLeaderboards = mutableMapOf<IMessage, Leaderboard>()

    fun createLeaderboard(iChannel: IChannel) {
        val leaderboard = buildBoard();
        val message = iChannel.sendMessage(leaderboard.toEmbedObject(bot))
        leaderboard.react(message)
        currentLeaderboards.put(message, leaderboard)
    }

    private fun buildBoard(): Leaderboard {
        val scored = bot.userManager.getUsersbyScores();
        return Leaderboard(scored);
    }

}