package org.phphideout.discordbot.commands

import de.btobastian.sdcf4j.Command
import de.btobastian.sdcf4j.CommandExecutor
import org.phphideout.discordbot.Bot
import sx.blah.discord.handle.obj.IChannel
import sx.blah.discord.handle.obj.IUser
import sx.blah.discord.util.EmbedBuilder
import sx.blah.discord.util.RequestBuffer
import java.awt.Color

class ServerCommands(val bot: Bot) : CommandExecutor {
  @Command(aliases = ["serverinfo()"], description = "Sets a role", usage = "\$this->serverinfo()")
  fun roleCommand(args: Array<String>, iUser: IUser, channel: IChannel) {
    val embedBuilder = EmbedBuilder()
    embedBuilder.withTitle(channel.guild.name)
    embedBuilder.withDesc("Server ID " + channel.guild.stringID)
    embedBuilder.withColor(Color.BLUE)
    embedBuilder.appendField("Members", channel.guild.users.size.toString(), true);
    embedBuilder.appendField("Users", bot.utils.getMembers(channel.guild.users).size.toString(), true);
    embedBuilder.appendField("Bots", bot.utils.getBots(channel.guild.users).size.toString(), true);
    embedBuilder.appendField("Owner", "`" + channel.guild.owner.name + ":" + channel.guild.owner.discriminator + "`", true);
    RequestBuffer.request {
      channel.sendMessage(embedBuilder.build())
    }
  }

}