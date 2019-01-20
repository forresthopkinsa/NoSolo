package com.forresthopkinsa.nosolo

import net.md_5.bungee.api.config.ServerInfo

data class ConfigModel(
    val threshold: Int,
    val mainServer: ServerInfo,
    val lobbyServer: ServerInfo
)