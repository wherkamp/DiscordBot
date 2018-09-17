package org.phphideout.discordbot

import sx.blah.discord.handle.obj.IUser
import sx.blah.discord.util.EmbedBuilder
import java.awt.Color

class DiscordUtils(private val bot: Bot) {
  fun getMembers(users: List<IUser>): List<IUser> {
    val mutable = mutableListOf<IUser>();
    for (user in users) {
      if (!user.isBot) {
        mutable.add(user);
      }
    }
    return mutable
  }

  fun getBots(users: List<IUser>): List<IUser> {
    val mutable = mutableListOf<IUser>();
    for (user in users) {
      if (user.isBot) {
        mutable.add(user);
      }
    }
    return mutable
  }
  fun buildMessage(string: String): EmbedBuilder{
    val embedBuilded = EmbedBuilder()
    embedBuilded.withColor(Color.BLUE)
    embedBuilded.withDesc(string)
    return embedBuilded
  }
}