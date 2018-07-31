package me.kingtux.phphideout.leaderboard

import me.kingtux.phphideout.Bot
import sx.blah.discord.handle.obj.IChannel
import sx.blah.discord.handle.obj.IMessage

class LeaderboardManager(val bot: Bot) {
    private val currentLeaderboards = mutableMapOf<IMessage, Leaderboard>()

    fun createLeaderboard(iChannel: IChannel) {

    }

    private fun buildBoard(): Leaderboard? {
        return null
    }
}