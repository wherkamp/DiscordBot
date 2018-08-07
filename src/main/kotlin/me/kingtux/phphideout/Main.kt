package me.kingtux.phphideout

import org.apache.commons.io.FileUtils
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
    val yaml = Yaml()
    val obj = yaml.load<Object>(FileReader(configFile))
    val map = obj as Map<String, Object>;
    return map.get("token") as String
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
