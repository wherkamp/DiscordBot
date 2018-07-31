package me.kingtux.phphideout

import de.btobastian.sdcf4j.handler.Discord4JHandler
import me.kingtux.phphideout.commands.LeaderboardCommand
import me.kingtux.phphideout.commands.RegisterCommands
import me.kingtux.phphideout.commands.ThxCommand
import me.kingtux.phphideout.leaderboard.LeaderboardManager
import me.kingtux.phphideout.listeners.ChatListener
import me.kingtux.phphideout.listeners.LeaveEvent
import me.kingtux.phphideout.listeners.PlayerJoin
import sx.blah.discord.api.ClientBuilder
import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.api.events.IListener
import sx.blah.discord.handle.impl.events.ReadyEvent
import sx.blah.discord.handle.obj.IChannel
import sx.blah.discord.handle.obj.IRole
import sx.blah.discord.util.DiscordException
import java.io.File


class Bot : IListener<ReadyEvent> {
    lateinit var discordClient: IDiscordClient;
    private val token: String;
    val database: Database;
    val leaderboardManager: LeaderboardManager
    lateinit var userManager: UserManager;
    lateinit var registeredRole: IRole;
    lateinit var botspamChannel: IChannel;
    lateinit var welcomeChannel: IChannel;

    constructor(token: String) {
        this.token = token;
        loadBot()
        database = Database(currentPath() + File.separator + "files" + File.separator + "database.sql")
        leaderboardManager = LeaderboardManager(this)
    }

    private fun currentPath(): String {
        var file = File(".")
        return file.canonicalPath
    }


    private fun loadBot() {
        val clientBuilder = ClientBuilder() // Creates the ClientBuilder instance
        clientBuilder
                .withToken(token) // Adds the login info to the builder
        try {
            discordClient = clientBuilder.login() // Creates the client instance and logs the client in
        } catch (e: DiscordException) {
            e.printStackTrace()
        }
        loadEvents()
        loadCommands()
    }

    override fun handle(event: ReadyEvent) {
        System.out.println("Server Ready")

        registeredRole = discordClient.getRoleByID(473471113611968512)
        botspamChannel = discordClient.getChannelByID(473289440966737952)
        welcomeChannel = discordClient.getChannelByID(473473238421340170)
        userManager = UserManager(this)

    }

    private fun loadCommands() {
        var cmdHandler = Discord4JHandler(discordClient)
        cmdHandler.defaultPrefix = "\$this->"
        println(cmdHandler.defaultPrefix);
        cmdHandler.registerCommand(RegisterCommands(this))
        cmdHandler.registerCommand(ThxCommand(this))
        cmdHandler.registerCommand(LeaderboardCommand(this))

    }

    private fun loadEvents() {
        discordClient.dispatcher.registerListener(this)
        discordClient.dispatcher.registerListener(ChatListener())
        discordClient.dispatcher.registerListener(PlayerJoin(this))
        discordClient.dispatcher.registerListener(LeaveEvent(this))

    }
}