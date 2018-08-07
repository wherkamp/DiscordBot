package me.kingtux.phphideout.commands

import de.btobastian.sdcf4j.Command
import de.btobastian.sdcf4j.CommandExecutor
import me.kingtux.phphideout.Bot
import sx.blah.discord.handle.obj.IChannel
import sx.blah.discord.handle.obj.IUser
import sx.blah.discord.util.RequestBuffer

class RegisterCommands(var bot: Bot) : CommandExecutor {
  @Command(aliases = ["register()"], description = "Registers your account", usage = "\$this->register()")
  public fun registerCommand(args: Array<String>, iUser: IUser, channel: IChannel) {
    if (bot.userManager.hasUser(iUser.longID)) {
      RequestBuffer.request {
        channel.sendMessage("echo \"You are already registered\"")
      }
    } else {
      iUser.addRole(bot.registeredRole)
      RequestBuffer.request {
        channel.sendMessage("echo \"You have been registered\"")
      }
      bot.userManager.createUser(iUser.longID);
    }
    bot.generalChannel.sendMessage("Say hello to ${iUser.mention()} who just joined and registered!")
  }


  @Command(aliases = ["role()"], description = "Sets a role", usage = "\$this->role() <Announcements, Updates>")
  public fun roleCommand(args: Array<String>, iUser: IUser, channel: IChannel) {
    if (args.size == 0) {
      channel.sendMessage("Sorry, you need to provide a role. (Announcements, Updates)")
      return;
    }
    if (args[0].equals("announcements", true)) {
      if (iUser.hasRole(bot.announcementsRole)) {
        channel.sendMessage("You will no longer receive announcements")
        iUser.removeRole(bot.announcementsRole);
        return;
      }
      channel.sendMessage("You will now receive announcements!")
      iUser.addRole(bot.announcementsRole)
    } else if (args[0].equals("updates", true)) {
      if (iUser.hasRole(bot.updatesRole)) {
        channel.sendMessage("You will no longer receive server updates")
        iUser.removeRole(bot.updatesRole);
        return;
      }
      channel.sendMessage("You will now receive updates of the server!")
      iUser.addRole(bot.updatesRole)
    } else {
      channel.sendMessage("Sorry, you need to provide a correct role. Updates or Announcements")
    }
  }


}