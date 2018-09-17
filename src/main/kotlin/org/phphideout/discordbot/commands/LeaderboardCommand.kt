package org.phphideout.discordbot.commands

import de.btobastian.sdcf4j.Command
import de.btobastian.sdcf4j.CommandExecutor
import org.phphideout.discordbot.Bot
import sx.blah.discord.handle.obj.IChannel
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IUser
import sx.blah.discord.util.RequestBuffer

class LeaderboardCommand(val bot: Bot) : CommandExecutor {
  @Command(aliases = ["leaderboard()", "leaderboard();"], description = "Gets the leaderbaord", usage = "\$this->leaderboard()")
  public fun leaderboardCommand(channel: IChannel, message: IMessage): String {
    bot.leaderboardManager.createLeaderboard(channel)
    return ""
  }

  @Command(aliases = ["thx()","thx();Discord"], description = "Thxs a user", usage = "\$this->thx() <who>")
  public fun registerCommand(args: Array<String>, iUser: IUser, channel: IChannel, message: IMessage): String {
    if (message.mentions.size == 1) {
      if (message.mentions[0] == iUser) {
        RequestBuffer.request {
          channel.sendMessage(bot.utils.buildMessage("You cannot thank yourself.").build())
        }
        return "";
      }
      if (message.mentions[0].isBot) {
        RequestBuffer.request {
          channel.sendMessage(bot.utils.buildMessage("You may not thank a bot").build())
        }
        return ""
      }
      if (!bot.userManager.canThx(iUser)) {
        RequestBuffer.request {
          channel.sendMessage(bot.utils.buildMessage("Sorry you must wait ${bot.userManager.getTimeTilNewThx(iUser)}.").build())
        }
        return ""
      }
      bot.userManager.thxUser(iUser, message.mentions[0])
      RequestBuffer.request {
        channel.sendMessage(bot.utils.buildMessage("You have thanked ${message.mentions[0].mention()}").build())
      }
      if (bot.userManager.needsLevelUp(message.mentions[0])) {
        bot.userManager.levelUp(message.mentions[0])
        RequestBuffer.request {

          channel.sendMessage(bot.userManager.generateLevelUpMesage(message.mentions[0], bot.userManager.getRank(message.mentions.get(0))))
        }
      }
    } else {
      RequestBuffer.request {
        channel.sendMessage(bot.utils.buildMessage("Sorry you need to tag someone to thx.").build())
      }
    }

    return "";
  }
}