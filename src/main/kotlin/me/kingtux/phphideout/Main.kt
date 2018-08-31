package me.kingtux.phphideout

import org.apache.commons.io.FileUtils
import org.simpleyaml.configuration.file.YamlFile
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileReader
import java.io.IOException


fun main(args: Array<String>) {
  Main().init()
}

class Main {
  val configFile = File("files" + File.separator + "config.yml")
  fun init() {
    val file = File("files");
    file.mkdir()
    saveFile("files" + File.separator + "config.yml", "/config.yml");
    Bot(getToken())
  }

  fun getToken(): String {
    val y = YamlFile(configFile)
    y.load()
    return y.getString("token")
  }

  @Throws(IOException::class)
  fun saveFile(saveDir: String, jarDir: String) {
    val file = File(saveDir)
    if (!file.exists()) {
      val url = this.javaClass.getResource(jarDir)
      if (url != null) {
        FileUtils.copyURLToFile(url, file)
      } else {
        throw NullPointerException("Jar File Not Found")
      }
    }
  }

}
