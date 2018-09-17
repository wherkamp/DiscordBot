package org.phphideout.discordbot.listeners

import org.phphideout.discordbot.Bot
import sx.blah.discord.api.events.IListener
import sx.blah.discord.handle.impl.events.guild.member.UserLeaveEvent

class LeaveEvent(val bot: Bot) : IListener<UserLeaveEvent> {

  override fun handle(event: UserLeaveEvent) {
    bot.userManager.unRegister(event.user.longID);
    bot.generalChannel.sendMessage(bot.utils.buildMessage(event.user.mention() + " has left the server!").build())
  }
}
