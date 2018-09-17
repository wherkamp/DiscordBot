package org.phphideout.discordbot.leaderboard

import org.phphideout.discordbot.Bot
import org.phphideout.discordbot.Util
import sx.blah.discord.api.internal.json.objects.EmbedObject
import sx.blah.discord.handle.impl.obj.ReactionEmoji
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.util.EmbedBuilder


class Leaderboard(private val mapped: LinkedHashMap<Long, Int>) {
  private val lists: List<Map<Long, Int>> = Util.split(10, mapped)
  var currentPage = 0;

  fun next() {
        currentPage++
    }
    fun back() {
        currentPage--
    }

    fun toEmbedObject(bot: Bot): EmbedObject? {
        if (lists.size > (currentPage + 1)) {
            currentPage = 0;
        }
        val embedBuilder = EmbedBuilder();
        embedBuilder.withTitle("Top Users " + (currentPage + 1) + "/" + lists.size)
        var pos = 1;
      if(currentPage != 0){
      pos += (currentPage * 10)
      }

        for (entry: Map.Entry<Long, Int> in lists.get(currentPage).entries) {
            val user = bot.discordClient.getUserByID(entry.key);
            val level = bot.userManager.getLevel(user);
            embedBuilder.withColor(66, 134, 244)
            embedBuilder.appendField(pos.toString(), user.name + ":" + user.discriminator + "(${level})", false)
            pos++
        }
        return embedBuilder.build()
    }


}
