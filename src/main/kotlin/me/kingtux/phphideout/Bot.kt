package me.kingtux.phphideout

import de.btobastian.sdcf4j.handler.Discord4JHandler
import me.kingtux.phphideout.commands.*
import me.kingtux.phphideout.leaderboard.LeaderboardManager
import me.kingtux.phphideout.listeners.ChatListener
import me.kingtux.phphideout.listeners.LeaveEvent
import me.kingtux.phphideout.listeners.PlayerJoin
import me.kingtux.phphideout.listeners.ReactionListener
import org.simpleyaml.configuration.ConfigurationSection
import org.simpleyaml.configuration.file.YamlFile
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
  private val configYAML = YamlFile(configFile)
  val utils = DiscordUtils(this);
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
    configYAML.load()
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
    val roles = configYAML["roles"] as ConfigurationSection
    val channels = configYAML["channel"] as ConfigurationSection
    registeredRole = discordClient.getRoleByID(roles.getLong("member"))
    updatesRole = discordClient.getRoleByID(roles.getLong("updates"))
    announcementsRole = discordClient.getRoleByID(roles.getLong("announcements"))
    botspamChannel = discordClient.getChannelByID(channels.getLong("botspam"))
    welcomeChannel = discordClient.getChannelByID(channels.getLong("welcome"))
    generalChannel = discordClient.getChannelByID(channels.getLong("general"))
    userManager = UserManager(this)
    println("RegisteredRole ${registeredRole.name}")
    println("UpdatesRole ${updatesRole.name}")
    println("AnnouncementsROle ${announcementsRole.name}")
    println("WelcomeChannel ${welcomeChannel.name}")
    println("BotSpam ${botspamChannel.name}")
    println("General Channel ${generalChannel.name}")
  }

  private fun loadCommands() {
    var cmdHandler = Discord4JHandler(discordClient)
    cmdHandler.defaultPrefix = "\$this->"
    println(cmdHandler.defaultPrefix);
    cmdHandler.registerCommand(ServerCommands(this))
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