package me.kingtux.phphideout.listeners

import me.kingtux.phphideout.Bot
import sx.blah.discord.api.events.IListener
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent

class ChatListener(private val bot: Bot) : IListener<MessageReceivedEvent> {
    override fun handle(event: MessageReceivedEvent) {
        if (event.message.content.startsWith("/$this->") || event.message.author.isBot) {
            return;
        }
        bot.userManager.userChat(event.author)

        if (bot.userManager.needsLevelUp(event.author)) {
            bot.userManager.levelUp(event.author)
            event.channel.sendMessage(bot.userManager.generateLevelUpMesage(event.author, bot.userManager.getRank(event.author)))
        }
    }
}