package me.kingtux.phphideout.listeners

import me.kingtux.phphideout.Bot
import sx.blah.discord.api.events.IListener
import sx.blah.discord.handle.impl.events.guild.member.UserJoinEvent
import sx.blah.discord.handle.obj.IUser
import sx.blah.discord.util.RequestBuffer

class PlayerJoin(private val bot: Bot) : IListener<UserJoinEvent> {

  override fun handle(event: UserJoinEvent) {
    event.user.orCreatePMChannel.sendMessage(buildPM(event.user))
    bot.userManager.createUser(event.user.longID)
    RequestBuffer.request {
      bot.generalChannel.sendMessage(bot.utils.buildMessage("Welcome ${event.user.mention()} to PhPHideout! We have just private messaged you the rules!").build())
    }
  }

  private fun buildPM(user: IUser): String {
    return "So you want to join, PhP hideout? So you need to follow these rules: ```1. Be friendly and always show respect to everyone, no matter their role.\n" +
        "2. No advertising or spamming. Avoid writing whole sentences in caps.\n" +
        "3. Harassment, abuse, hate speech or any kind of discriminatory speech will not be tolerated.\n" +
        "4. No links may contain advertisement of any kind, pornography, spoilers, racism or disturbing/offensive content.\n" +
        "5. When chatting, please find the appropriate channel and keep to topic.\n" +
        "6. If you have access to manage the emojis, note that any abuse of this access will not be tolerated.\n" +
        "7. PHP is an awesome language treat it that way!\n" +
        "8. No spamming @${bot.announcementsRole.name} or @${bot.updatesRole.name}```"
  }

}