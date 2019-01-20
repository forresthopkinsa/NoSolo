package com.forresthopkinsa.nosolo

import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.config.ServerInfo
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration
import java.io.File
import java.nio.file.Files

class ConfigUtility(private val proxy: ProxyServer, private val dataFolder: File) {

    private val logger = proxy.logger

    fun getConfig(): ConfigModel {
        if (!dataFolder.exists()) {
            logger.warning("Plugin data folder not found; creating it.")
            dataFolder.mkdir()
        }

        val file = File(dataFolder, "config.yml")

        if (!file.exists()) {
            logger.warning("Plugin config file not found; creating it.")
            javaClass.classLoader.getResourceAsStream("defaultConfig.yml").use {
                Files.copy(it, file.toPath())
            }
        }

        val config = ConfigurationProvider.getProvider(YamlConfiguration::class.java).load(file)

        val playerThreshold = config["playerThreshold", 2]
        val mainServerName = config["mainServerName", "main"]
        val lobbyServerName = config["lobbyServerName", "lobby"]

        if (playerThreshold == null || mainServerName == null || lobbyServerName == null) {
            throw Error("Configuration file is missing values; try deleting it to get a clean one")
        }

        val mainServer: ServerInfo? = proxy.getServerInfo(mainServerName)
        val lobbyServer: ServerInfo? = proxy.getServerInfo(lobbyServerName)

        if (mainServer == null) {
            throw Error("Server '$mainServerName' does not exist")
        } else if (lobbyServer == null) {
            throw Error("Server '$lobbyServerName' does not exist")
        }

        return ConfigModel(
            threshold = playerThreshold,
            mainServer = mainServer,
            lobbyServer = lobbyServer
        )
    }

}