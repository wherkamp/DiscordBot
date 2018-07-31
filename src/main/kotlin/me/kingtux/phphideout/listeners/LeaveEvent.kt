package me.kingtux.phphideout.listeners

import me.kingtux.phphideout.Bot
import sx.blah.discord.api.events.IListener
import sx.blah.discord.handle.impl.events.guild.member.UserLeaveEvent

class LeaveEvent(val bot: Bot) : IListener<UserLeaveEvent> {

    override fun handle(event: UserLeaveEvent) {
        bot.welcomeChannel.sendMessage(event.user.mention() + " has left the server!")
    }
}
