package me.kingtux.phphideout.leaderboard

import me.kingtux.phphideout.Bot
import me.kingtux.phphideout.Util
import sx.blah.discord.api.internal.json.objects.EmbedObject
import sx.blah.discord.handle.impl.obj.ReactionEmoji
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.util.EmbedBuilder

class Leaderboard {
    private val mapped: LinkedHashMap<Long, Int>;
    private val lists: List<Map<Long, Int>>
    var currentPage = 0;

    constructor(mapped: LinkedHashMap<Long, Int>) {
        this.mapped = mapped
        lists = Util.split(10, mapped)
        println(mapped.toString())
        println(lists.get(0).toString())
        println(mapped.size)
    }

    fun next() {
        currentPage++
    }

    fun toEmbedObject(bot: Bot): EmbedObject? {
        if (lists.size >= (currentPage + 1)) {
            currentPage = 0;
        }
        val embedBuilder = EmbedBuilder();
        embedBuilder.withTitle("Top Users " + (currentPage + 1) + "/" + lists.size)
        var pos = 1;
        for (entry: Map.Entry<Long, Int> in lists.get(currentPage).entries) {
            val user = bot.discordClient.getUserByID(entry.key);
            val level = bot.userManager.getLevel(user);
            embedBuilder.withColor(66, 134, 244)
            embedBuilder.appendField(pos.toString(), user.name + ":" + user.discriminator + "(${level})", false)
            pos++
        }
        return embedBuilder.build()
    }


    fun react(message: IMessage) {
        message.addReaction(ReactionEmoji.of(":arrow_left:"))
        message.addReaction(ReactionEmoji.of(":arrow_right:"))
    }
}