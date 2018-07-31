package me.kingtux.phphideout.commands

import de.btobastian.sdcf4j.Command
import de.btobastian.sdcf4j.CommandExecutor
import me.kingtux.phphideout.Bot
import sx.blah.discord.handle.obj.IChannel
import sx.blah.discord.handle.obj.IUser

class RegisterCommands(var bot: Bot) : CommandExecutor {
    @Command(aliases = ["register()"], description = "Registers your account", usage = "\t&this->register")
    public fun registerCommand(args: Array<String>, iUser: IUser, channel: IChannel) {
        if (bot.userManager.hasUser(iUser.longID)) {
            channel.sendMessage("echo \"You are already registered\"")
        } else {
            iUser.addRole(bot.registeredRole)
            channel.sendMessage("echo \"You have been registered\"")
            bot.userManager.createUser(iUser.longID);
        }
    }
}