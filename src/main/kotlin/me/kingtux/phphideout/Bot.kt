package me.kingtux.phphideout

import de.btobastian.sdcf4j.handler.Discord4JHandler
import me.kingtux.phphideout.commands.HelpCommand
import me.kingtux.phphideout.commands.LeaderboardCommand
import me.kingtux.phphideout.commands.RegisterCommands
import me.kingtux.phphideout.commands.ThxCommand
import me.kingtux.phphideout.leaderboard.LeaderboardManager
import me.kingtux.phphideout.listeners.ChatListener
import me.kingtux.phphideout.listeners.LeaveEvent
import me.kingtux.phphideout.listeners.PlayerJoin
import me.kingtux.phphideout.listeners.ReactionListener
import org.yaml.snakeyaml.Yaml
import sx.blah.discord.api.ClientBuilder
import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.api.events.IListener
import sx.blah.discord.handle.impl.events.ReadyEvent
import sx.blah.discord.handle.obj.IChannel
import sx.blah.discord.handle.obj.IRole
import sx.blah.discord.util.DiscordException
import java.io.File
import java.io.FileReader


class Bot : IListener<ReadyEvent> {
  lateinit var discordClient: IDiscordClient;
  private val token: String;
  private val configFile = File("files" + File.separator + "config.yml")
  private val yaml = Yaml()
  private val configYAML = yaml.load<Map<String, Any>>(FileReader(configFile))

  val database: Database;
  val leaderboardManager: LeaderboardManager
  lateinit var userManager: UserManager;
  lateinit var registeredRole: IRole;
  lateinit var updatesRole: IRole;
  lateinit var announcementsRole: IRole;
  lateinit var botspamChannel: IChannel;
  lateinit var welcomeChannel: IChannel;
  lateinit var generalChannel: IChannel;

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
    val roles = configYAML["roles"] as Map<*, *>
    val channels = configYAML["channel"] as Map<*, *>
    registeredRole = discordClient.getRoleByID(roles["member"] as Long)
    updatesRole = discordClient.getRoleByID(roles["updates"] as Long)
    announcementsRole = discordClient.getRoleByID(roles["announcements"] as Long)
    botspamChannel = discordClient.getChannelByID(channels["botspam"] as Long)
    welcomeChannel = discordClient.getChannelByID(channels["welcome"] as Long)
    generalChannel = discordClient.getChannelByID(channels["general"] as Long)
    userManager = UserManager(this)
    println("RegisteredRole ${registeredRole.name}")
    println("UpdatesRole ${updatesRole.name}")
    println("AnnouncementsROle ${announcementsRole.name}")
    println("WelcomeChannel ${welcomeChannel.name}")
    println("BotSpam ${botspamChannel.name}")
  }

  private fun loadCommands() {
    var cmdHandler = Discord4JHandler(discordClient)
    cmdHandler.defaultPrefix = "\$this->"
    println(cmdHandler.defaultPrefix);
    cmdHandler.registerCommand(RegisterCommands(this))
    cmdHandler.registerCommand(ThxCommand(this))
    cmdHandler.registerCommand(LeaderboardCommand(this))
    cmdHandler.registerCommand(HelpCommand(cmdHandler, this))

  }

  private fun loadEvents() {
    discordClient.dispatcher.registerListener(this)
    discordClient.dispatcher.registerListener(ChatListener(this))
    discordClient.dispatcher.registerListener(PlayerJoin(this))
    discordClient.dispatcher.registerListener(LeaveEvent(this))
    discordClient.dispatcher.registerListener(ReactionListener(this))

  }
}