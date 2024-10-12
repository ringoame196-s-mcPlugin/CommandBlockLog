package com.github.ringoame196_s_mcPlugin.managers

import com.github.ringoame196_s_mcPlugin.commands.CommandConst
import com.github.ringoame196_s_mcPlugin.datas.CommandBlockLog
import com.github.ringoame196_s_mcPlugin.datas.Data
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.ChatColor
import org.bukkit.Material

class LogManager {
    fun setBlockTypeFilter(args: Array<out String>): MutableList<Material> {
        val blockTypeFilter = mutableListOf<Material>()
        val commandMap = mapOf(
            CommandConst.SEARCH_IMPULSE to Material.COMMAND_BLOCK,
            CommandConst.SEARCH_CHAIN to Material.CHAIN_COMMAND_BLOCK,
            CommandConst.SEARCH_REPEAT to Material.REPEATING_COMMAND_BLOCK,
            CommandConst.SEARCH_COMMAND_MINECART to Material.COMMAND_BLOCK_MINECART
        )

        args.forEach { arg ->
            commandMap[arg]?.let { blockTypeFilter.add(it) }
        }
        return blockTypeFilter
    }

    fun setCommandFilter(args: Array<out String>): MutableList<String> {
        val commandFilter = mutableListOf<String>()

        for (arg in args) {
            if (arg.contains(CommandConst.SEARCH_COMMAND_PREFIX)) {
                val command = arg.replace(CommandConst.SEARCH_COMMAND_PREFIX, "")
                commandFilter.add(command)
            }
        }

        return commandFilter
    }

    fun convertingLog(blockTypeFilter: MutableList<Material>, commandFilter: MutableList<String>): MutableList<TextComponent> {
        // コマンドブロックの種類をわかりやすく
        val blockDisplayMap = mapOf(
            Material.COMMAND_BLOCK to "${ChatColor.GOLD}衝撃${ChatColor.WHITE}",
            Material.CHAIN_COMMAND_BLOCK to "${ChatColor.AQUA}チェーン${ChatColor.WHITE}",
            Material.REPEATING_COMMAND_BLOCK to "${ChatColor.BLUE}反復${ChatColor.WHITE}",
            Material.COMMAND_BLOCK_MINECART to "${ChatColor.GOLD}コマブロトロッコ${ChatColor.WHITE}"
        )

        val logTexts = mutableListOf<TextComponent>()

        for (commandBlockLog in Data.commandBlockLog) {
            val blockType = commandBlockLog.acquisitionType()
            val blockDisplay = blockDisplayMap[blockType]
            val command = commandBlockLog.acquisitionCommand()
            if (!checkBlockTypeFilter(blockTypeFilter, blockType) || !checkCommandFilter(commandFilter, command)) continue
            val logText = TextComponent("[$blockDisplay] $command")
            logText.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, ComponentBuilder(displayLocation(commandBlockLog)).create())
            logTexts.add(logText)
        }

        return logTexts
    }

    private fun checkBlockTypeFilter(blockTypeFilter: MutableList<Material>, blockType: Material): Boolean {
        return blockTypeFilter.isEmpty() || blockTypeFilter.contains(blockType)
    }

    private fun checkCommandFilter(commandFilter: MutableList<String>, command: String): Boolean {
        return if (commandFilter.isNotEmpty()) {
            commandFilter.any { command.contains(it) }
        } else {
            true
        }
    }

    private fun displayLocation(commandBlockLog: CommandBlockLog): String {
        val location = commandBlockLog.acquisitionLocation()
        val world = location.world?.name
        val x = location.x.toInt()
        val y = location.y.toInt()
        val z = location.z.toInt()
        return "座標:world名:$world, x:$x, y:$y, z:$z"
    }
}
