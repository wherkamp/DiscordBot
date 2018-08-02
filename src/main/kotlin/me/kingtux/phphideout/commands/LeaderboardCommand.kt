package me.kingtux.phphideout.commands

import de.btobastian.sdcf4j.Command
import de.btobastian.sdcf4j.CommandExecutor
import me.kingtux.phphideout.Bot
import sx.blah.discord.handle.obj.IChannel
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IUser
import sx.blah.discord.util.EmbedBuilder

class LeaderboardCommand(val bot: Bot) : CommandExecutor {
    @Command(aliases = ["leaderboard()"], description = "Gets the leadboard", usage = "&this->leaderboard")
    public fun leaderboardCommand(channel: IChannel, message: IMessage): String {
        bot.leaderboardManager.createLeaderboard(channel)
        return ""
    }

    @Command(aliases = ["rank()"], description = "Gets some userinfo", usage = "&this->userinfo()")
    public fun userinfo(channel: IChannel, message: IMessage): String {
        var personToCheck: IUser;
        if (message.mentions.size >= 1) {
            personToCheck = message.mentions.get(0);

        } else {
            personToCheck = message.author;
        }
        val embedBuilder = EmbedBuilder();
        embedBuilder.withTitle(personToCheck.name + " Rank Info")
        val level = bot.userManager.getLevel(personToCheck).toString();

        embedBuilder.appendField("Rank", bot.userManager.getRank(personToCheck).toString(), true);
        embedBuilder.appendField("Level", level, true);
        embedBuilder.appendField("Points", bot.userManager.getPoints(personToCheck).toString() + "/" + bot.userManager.getLevelRequirements(level.toInt() + 1), false);
        channel.sendMessage(embedBuilder.build())
//
        return ""
    }
}