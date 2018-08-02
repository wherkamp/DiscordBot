package me.kingtux.phphideout.listeners

import me.kingtux.phphideout.Bot
import sx.blah.discord.api.events.IListener
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionEvent

class ReactionListener(private val bot: Bot) : IListener<ReactionEvent> {
    override fun handle(event: ReactionEvent) {
    }


}