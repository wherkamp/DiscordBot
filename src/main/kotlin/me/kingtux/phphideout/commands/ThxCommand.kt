package me.kingtux.phphideout.commands

import de.btobastian.sdcf4j.Command
import de.btobastian.sdcf4j.CommandExecutor
import me.kingtux.phphideout.Bot
import sx.blah.discord.handle.obj.IChannel
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IUser

class ThxCommand(val bot: Bot) : CommandExecutor {
    @Command(aliases = ["thx()"], description = "Registers your account", usage = "&this->thx")
    public fun registerCommand(args: Array<String>, iUser: IUser, channel: IChannel, message: IMessage): String {
        if (message.mentions.size == 1) {

            if (!bot.userManager.canThx(iUser)) {
                channel.sendMessage("You need to wait 30 minutes to thx a new user")
                return ""
            }
            bot.userManager.thxUser(iUser, message.mentions.get(0))
            channel.sendMessage("You have thanked the user")

        } else {
            channel.sendMessage("Sorry you must mention a user!")
        }

        return "";
    }
}