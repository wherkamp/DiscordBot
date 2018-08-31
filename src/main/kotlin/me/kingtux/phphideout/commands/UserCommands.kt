package me.kingtux.phphideout.commands

import de.btobastian.sdcf4j.Command
import de.btobastian.sdcf4j.CommandExecutor
import me.kingtux.phphideout.Bot
import sx.blah.discord.handle.obj.IChannel
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IUser
import sx.blah.discord.util.EmbedBuilder
import sx.blah.discord.util.RequestBuffer

class UserCommands(var bot: Bot) : CommandExecutor {


  @Command(aliases = ["role()"], description = "Sets a role", usage = "\$this->role() <Announcements, Updates>")
  public fun roleCommand(args: Array<String>, iUser: IUser, channel: IChannel) {
    if (args.size == 0) {
      channel.sendMessage("Sorry, you need to provide a role. (Announcements, Updates)")
      return;
    }
    if (args[0].equals("announcements", true)) {
      if (iUser.hasRole(bot.announcementsRole)) {
        channel.sendMessage(bot.utils.buildMessage("You will no longer receive announcements").build())
        iUser.removeRole(bot.announcementsRole);
        return;
      }
      channel.sendMessage(bot.utils.buildMessage("You will now receive announcements!").build())
      iUser.addRole(bot.announcementsRole)
    } else if (args[0].equals("updates", true)) {
      if (iUser.hasRole(bot.updatesRole)) {
        channel.sendMessage(bot.utils.buildMessage("You will no longer receive server updates").build())
        iUser.removeRole(bot.updatesRole);
        return;
      }
      channel.sendMessage(bot.utils.buildMessage("You will now receive updates of the server!").build())
      iUser.addRole(bot.updatesRole)
    } else {
      channel.sendMessage(bot.utils.buildMessage("Sorry, you need to provide a correct role. Updates or Announcements").build())
    }
  }

  @Command(aliases = ["test()"], description = "Tests something", usage = "\$this->test()")
  public fun test(args: Array<String>, iUser: IUser, channel: IChannel, message: IMessage): String {
    RequestBuffer.request {
      channel.sendMessage(bot.userManager.generateLevelUpMesage(iUser, 1));
    }
    return "";
  }

  @Command(aliases = ["rank()"], description = "Gets some rank", usage = "\$this->rank()")
  public fun rankCommand(channel: IChannel, message: IMessage): String {
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
    RequestBuffer.request {
      channel.sendMessage(embedBuilder.build())
    }
    return ""
  }
}