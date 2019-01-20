package com.forresthopkinsa.nosolo

import net.md_5.bungee.api.plugin.Plugin

class NoSoloPlugin : Plugin() {

    private val config: ConfigModel by lazy {
        ConfigUtility(proxy, dataFolder).getConfig()
    }

    private val transporter: PlayerTransporter by lazy {
        PlayerTransporter(proxy, config)
    }

    override fun onEnable() {
        try {
            proxy.pluginManager.registerListener(this, Events(transporter::onCountChange))
        } catch (e: Exception) {
            proxy.logger.severe(e.message)
        }
    }

}