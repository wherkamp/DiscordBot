package me.kingtux.phphideout.listeners

import me.kingtux.phphideout.Bot
import sx.blah.discord.api.events.IListener
import sx.blah.discord.handle.impl.events.guild.member.UserJoinEvent
import sx.blah.discord.handle.obj.IUser

class PlayerJoin(private val bot: Bot) : IListener<UserJoinEvent> {

    override fun handle(event: UserJoinEvent) {
        bot.welcomeChannel.sendMessage(buildWelcomeMessage(event.user))
        event.user.orCreatePMChannel.sendMessage(buildPM(event.user))
    }

    private fun buildPM(user: IUser): String {
        return "So you want to join our Discord? Well here are some rules you need to follow." +
                "Rules.```1. Be Nice ```" +
                "Other Info```1. Can I ask help for other languages? No, we are php centered!```"
    }

    private fun buildWelcomeMessage(user: IUser): String {
        return "Welcome " + user.mention() + " Please register your account by doing `\$this->register()`. We have PMed you the rules and other info. Read before registering."

    }
}