package com.github.ringoame196_s_mcPlugin.datas

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.minecart.CommandMinecart

data class CommandBlockMinecartDedicatedLog(
    val commandMinecart: CommandMinecart,
    val command: String
) : CommandBlockLog {
    override fun acquisitionType(): Material = Material.COMMAND_BLOCK_MINECART
    override fun acquisitionLocation(): Location = commandMinecart.location
    override fun acquisitionCommand(): String = command
}
