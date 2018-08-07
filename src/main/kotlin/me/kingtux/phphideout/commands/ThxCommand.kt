package me.kingtux.phphideout.commands

import de.btobastian.sdcf4j.Command
import de.btobastian.sdcf4j.CommandExecutor
import me.kingtux.phphideout.Bot
import sx.blah.discord.handle.obj.IChannel
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IUser
import sx.blah.discord.util.RequestBuffer

class ThxCommand(val bot: Bot) : CommandExecutor {
  @Command(aliases = ["thx()"], description = "Thxs a user", usage = "\$this->thx() <who>")
  public fun registerCommand(args: Array<String>, iUser: IUser, channel: IChannel, message: IMessage): String {
    if (message.mentions.size == 1) {
      if (message.mentions[0] == iUser) {
        channel.sendMessage("Sorry, you can't thx yourself.")
        return "";
      }
      if(message.mentions[0].isBot){
        channel.sendMessage("Sorry, you can't thx a bot.")
        return ""
      }
      if (!bot.userManager.canThx(iUser)) {
        RequestBuffer.request {
          channel.sendMessage("You need to wait 30 minutes to thx a new user")
        }
        return ""
      }
      bot.userManager.thxUser(iUser, message.mentions[0])
      RequestBuffer.request {
        channel.sendMessage("You have thanked the user")
      }
      if (bot.userManager.needsLevelUp(message.mentions[0])) {
        bot.userManager.levelUp(message.mentions[0])
        RequestBuffer.request {

          channel.sendMessage(bot.userManager.generateLevelUpMesage(message.mentions[0], bot.userManager.getRank(message.mentions.get(0))))
        }
      }
    } else {
      RequestBuffer.request {
        channel.sendMessage("Sorry you must mention a user!")
      }
    }

    return "";
  }

  @Command(aliases = ["test()"], description = "Tests something", usage = "\$this->test()")
  public fun test(args: Array<String>, iUser: IUser, channel: IChannel, message: IMessage): String {
    RequestBuffer.request {
      channel.sendMessage(bot.userManager.generateLevelUpMesage(iUser, 1));
    }
    return "";
  }
}