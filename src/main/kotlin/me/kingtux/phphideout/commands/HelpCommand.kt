package me.kingtux.phphideout.commands

import de.btobastian.sdcf4j.CommandExecutor
import de.btobastian.sdcf4j.Command
import de.btobastian.sdcf4j.CommandHandler
import de.btobastian.sdcf4j.handler.Discord4JHandler
import me.kingtux.phphideout.Bot
import sx.blah.discord.handle.obj.IChannel
import sx.blah.discord.handle.obj.IUser
import sx.blah.discord.util.EmbedBuilder
import java.awt.Color


class HelpCommand(val commandHandler: CommandHandler, val bot: Bot) : CommandExecutor {
  @Command(aliases = arrayOf("help()", "commands()"), description = "Shows this page", usage = "\$this->help()")
  fun onHelpCommand(channel: IChannel, user: IUser) {

    val builder = EmbedBuilder()
    builder.withTitle("Help Menu")
    builder.withDesc("This is all the commands you can use!")
    builder.withColor(Color.BLUE)

    for (simpleCommand in commandHandler.commands) {
      if (simpleCommand.commandAnnotation.aliases.contains("register()")) {
        if (!bot.userManager.hasUser(user.longID)) {
          builder.appendField(simpleCommand.commandAnnotation.description, simpleCommand.commandAnnotation.usage, false)
        }
      } else {
        builder.appendField(simpleCommand.commandAnnotation.description, simpleCommand.commandAnnotation.usage, false)
      }
    }
    channel.sendMessage(builder.build())
  }
}