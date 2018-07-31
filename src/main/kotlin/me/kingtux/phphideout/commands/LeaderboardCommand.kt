package me.kingtux.phphideout.commands

import de.btobastian.sdcf4j.Command
import de.btobastian.sdcf4j.CommandExecutor
import me.kingtux.phphideout.Bot
import sx.blah.discord.handle.obj.IChannel
import sx.blah.discord.handle.obj.IMessage

class LeaderboardCommand(val bot: Bot) : CommandExecutor {
    @Command(aliases = ["leaderboard()"], description = "Registers your account", usage = "&this->leaderboard")
    public fun leaderboardCommand(channel: IChannel, message: IMessage): String {
        bot.leaderboardManager.createLeaderboard(channel)
        return ""
    }
}