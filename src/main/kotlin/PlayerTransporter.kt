package com.forresthopkinsa.nosolo

import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.connection.ProxiedPlayer
import java.util.concurrent.TimeUnit

class PlayerTransporter(private val proxy: ProxyServer, private val config: ConfigModel) {

    private val logger = proxy.logger

    fun onCountChange(newCount: Int, exceptedPlayer: ProxiedPlayer? = null) {
        proxy.scheduler.schedule(NoSoloPlugin(), {

            val targetServer = if (newCount >= config.threshold) config.mainServer else config.lobbyServer
            logger.info("$newCount players; Sending everyone to ${targetServer.name}")
            proxy.players
                .filterNot { it.displayName == exceptedPlayer?.displayName }
                .filterNot { it.server?.info == targetServer }
                .forEach { player ->
                    player.connect(targetServer) { result, err ->
                        if (result)
                            logger.info("Successfully transported player: ${player.displayName} to server: ${targetServer.name}")
                        else
                            logger.severe("Failed to transport player: ${player.displayName}; error: ${err?.message}")
                    }
                }

        }, 5, TimeUnit.SECONDS)
    }

}