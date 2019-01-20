package com.forresthopkinsa.nosolo

import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.event.PlayerDisconnectEvent
import net.md_5.bungee.api.event.PostLoginEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

class Events(private val onCountChange: (Int, ProxiedPlayer?) -> Unit) : Listener {

    private val logger = ProxyServer.getInstance().logger

    private val count: Int
        get() = ProxyServer.getInstance().onlineCount

    @EventHandler
    fun onPostLogin(event: PostLoginEvent) {
        val player = event.player
        logger.info("Login event for player: ${player.displayName} at server: ${player.server?.info?.name}")
        onCountChange(count, null)
    }

    @EventHandler
    fun onPlayerDisconnect(event: PlayerDisconnectEvent) {
        logger.info("PlayerDisconnect event for player: ${event.player.displayName} from server: ${event.player.server}")
        onCountChange(count - 1, event.player)
    }

}