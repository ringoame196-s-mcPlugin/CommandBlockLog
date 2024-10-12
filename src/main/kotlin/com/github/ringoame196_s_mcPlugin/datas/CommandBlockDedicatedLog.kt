package com.github.ringoame196_s_mcPlugin.datas

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block

data class CommandBlockDedicatedLog(
    val commandBlock: Block,
    val command: String
) : CommandBlockLog {
    override fun acquisitionType(): Material = commandBlock.type
    override fun acquisitionLocation(): Location = commandBlock.location
    override fun acquisitionCommand(): String = command
}
